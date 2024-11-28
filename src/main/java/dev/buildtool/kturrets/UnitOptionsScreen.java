package dev.buildtool.kturrets;

import dev.buildtool.kturrets.packets.*;
import dev.buildtool.satako.Constants;
import dev.buildtool.satako.IntegerColor;
import dev.buildtool.satako.UniqueList;
import dev.buildtool.satako.gui.*;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.*;
import dev.ftb.mods.ftblibrary.ui.TextField;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class UnitOptionsScreen extends ButtonListScreen {
    protected Turret turret;
    private final List<Label> suggestions=new ArrayList<>(14);
    private final UniqueList<EntityType<?>> targets;
    public CombinedScreen wrapper;
    private final ArrayList<BetterButton> hideableWidgets=new ArrayList<>();
    private dev.buildtool.satako.gui.TextField addEntity;
    private final List<String> exceptions=new ArrayList<>();
    private ArrayList<TextButton> exceptionButtons=new ArrayList<>();

    public UnitOptionsScreen(Turret turret) {
        this.turret=turret;
        targets=new UniqueList<>(Turret.decodeTargets(turret.getTargets()));
        showBottomPanel(false);
        initGui();
        setFullscreen();
        setTitle(Component.translatable("k_turrets.targets"));
        exceptions.addAll(turret.getExceptions());
    }

    @Override
    protected void doCancel() {

    }

    @Override
    protected void doAccept() {

    }

    @Override
    public void addButtons(Panel var1) {
        exceptions.forEach(s -> {
            TextButton textButton = new TextButton(var1, Component.literal(s), true);
            var1.add(textButton);
            exceptionButtons.add(textButton);
        });
        if(!exceptions.isEmpty()) {
            TextField separator = new TextField(var1);
            separator.setHeight(18);
            var1.add(separator);
        }
        targets.sort(Comparator.comparing(o -> ForgeRegistries.ENTITY_TYPES.getKey(o).toString()));
        targets.stream().map(entityType -> Component.literal(ForgeRegistries.ENTITY_TYPES.getKey(entityType).toString())).map(entityName -> new TextButton(var1, entityName, true)).forEach(var1::add);
    }

    @Override
    public void addWidgets() {
        super.addWidgets();
        Panel rightPanel = new Panel(this) {
            private BetterButton addButton;
            private dev.buildtool.satako.gui.TextField addEntity;

            @Override
            public void addWidgets() {

            }

            @Override
            public void alignWidgets() {

            }
        };
        rightPanel.setPosAndSize(mainPanel.width+90,10,width- mainPanel.width,height);
        add(rightPanel);
    }

    @Override
    public boolean onClosedByKey(Key key) {
        return addEntity.isFocused() ? key.esc() : key.escOrInventory();
    }

    public void initialize()
    {
        addEntity = new dev.buildtool.satako.gui.TextField(width/2, 10,Component.empty(),width/2){
            @Override
            public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
                suggestions.forEach(wrapper::removeWidget);
                suggestions.clear();
                String text = addEntity.getValue();
                if (!text.isEmpty()) {
                    List<ResourceLocation> entityTypes= new ArrayList<>(ForgeRegistries.ENTITY_TYPES.getKeys().stream().filter(resourceLocation -> resourceLocation.toString().contains(text)).toList());
                    int yOffset = 20;
                    entityTypes.removeAll(targets.stream().map(ForgeRegistries.ENTITY_TYPES::getKey).toList());
                    for (ResourceLocation entityType : entityTypes.subList(0, Math.min(entityTypes.size(), 20))) {
                        Label hint = new Label(addEntity.getX(), addEntity.getY() + yOffset, Component.literal(ChatFormatting.YELLOW + entityType.toString()), wrapper, p_93751_ -> {
                            addEntity.setValue(p_93751_.getMessage().getString().substring(2));
                            suggestions.forEach(wrapper::removeWidget);
                            suggestions.clear();
                            hideableWidgets.forEach(betterButton -> betterButton.setHidden(false));
                        },new IntegerColor(0xff000000));
                        wrapper.addRenderableWidget(hint);
                        suggestions.add(hint);
                        yOffset += 14;
                    }
                    if (!entityTypes.isEmpty()) {
                        hideableWidgets.forEach(betterButton -> betterButton.setHidden(true));
                    } else {
                        hideableWidgets.forEach(betterButton -> betterButton.setHidden(false));
                    }
                } else {
                    hideableWidgets.forEach(betterButton -> betterButton.setHidden(false));
                }
                return true;
            }


        };
        wrapper.addRenderableWidget(addEntity);
        BetterButton addButton = new BetterButton(addEntity.getX(), addEntity.getY() + addEntity.getHeight()+4, Component.translatable("k_turrets.add.entity.type"), button -> {
            String string = addEntity.getValue();
            if(string.startsWith("!") && string.length()>1)
            {
                String playerName=string.substring(1);
                if(exceptions.contains(playerName))
                {
                    wrapper.addPopup(Component.translatable("k_turrets.player.is.already.in.exceptions"));
                }
                else {
                    turret.addPlayerToExceptions(playerName);
                    exceptions.add(playerName);
                    KTurrets.channel.sendToServer(new AddPlayerException(turret.getId(),playerName));
                    addEntity.setValue("");
                    mainPanel.refreshWidgets();
                }
            }
            else {
                EntityType<?> entityTypesValue = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(string));
                targets.add(entityTypesValue);
                KTurrets.channel.sendToServer(new SetTarget(true, string, turret.getId()));
                addEntity.setValue("");
                mainPanel.refreshWidgets();
                wrapper.addPopup(Component.translatable("k_turrets.added"));
            }

        });
        wrapper.addRenderableWidget(addButton);
        hideableWidgets.add(addButton);
        BetterButton dismantle=new BetterButton(addEntity.getX(),addButton.getY()+addButton.getHeight(),Component.translatable("k_turrets.dismantle"),pButton -> {
            wrapper.closeGui();
            KTurrets.channel.sendToServer(new DismantleTurret(turret.getId()));
        });
        wrapper.addRenderableWidget(dismantle);
        hideableWidgets.add(dismantle);

        BetterButton clearTargets=new BetterButton(addEntity.getX(),dismantle.getY()+dismantle.getHeight(),Component.translatable("k_turrets.clear.list"),pButton -> {
            targets.clear();
            mainPanel.clearWidgets();
            KTurrets.channel.sendToServer(new TurretTargets(new CompoundTag(),turret.getId()));
        });
        wrapper.addRenderableWidget(clearTargets);
        hideableWidgets.add(clearTargets);

        BetterButton resetTargets=new BetterButton(clearTargets.getX()+clearTargets.getElementWidth(),clearTargets.getY(),Component.translatable("k_turrets.reset.list"),pButton -> {
            List<EntityType<?>> entityTypeList = ForgeRegistries.ENTITY_TYPES.getValues().stream().filter(entityType1 -> !entityType1.getCategory().isFriendly()).toList();
            targets.addAll(entityTypeList);
            refreshWidgets();
            List<EntityType<?>> entityTypes=Turret.decodeTargets(turret.getTargets());
            entityTypes.clear();
            entityTypes.addAll(entityTypeList);
            CompoundTag compoundNBT = Turret.encodeTargets(entityTypeList);
            turret.setTargets(compoundNBT);
            KTurrets.channel.sendToServer(new TurretTargets(compoundNBT,turret.getId()));
        });
        wrapper.addRenderableWidget(resetTargets);
        hideableWidgets.add(resetTargets);

        SwitchButton mobility=new SwitchButton(addEntity.getX(),resetTargets.getY()+resetTargets.getHeight(),Component.translatable("k_turrets.mobile"),Component.translatable("k_turrets.immobile"),turret.isMoveable(),pButton -> {
           SwitchButton switchButton1= (SwitchButton) pButton;
           switchButton1.state=!switchButton1.state;
           turret.setMoveable(switchButton1.state);
           KTurrets.channel.sendToServer(new ToggleMobility(switchButton1.state,turret.getId()));
        });
        wrapper.addRenderableWidget(mobility);
        hideableWidgets.add(mobility);
        mobility.setTooltip(Tooltip.create(Component.translatable("k_turrets.pushable")));

        SwitchButton playerProtection=new SwitchButton(addEntity.getX(),mobility.getY()+mobility.getHeight(),Component.translatable("k_turrets.protect.from.players"),Component.translatable("k_turrets.not.protect.from.players"),turret.isProtectingFromPlayers(),pButton -> {
            SwitchButton switchButton1= (SwitchButton) pButton;
            switchButton1.state=!switchButton1.state;
            turret.setProtectionFromPlayers(switchButton1.state);
            KTurrets.channel.sendToServer(new TogglePlayerProtection(switchButton1.state,turret.getId()));
        });
        wrapper.addRenderableWidget(playerProtection);
        hideableWidgets.add(playerProtection);
        playerProtection.setTooltip(Tooltip.create(Component.translatable("k_turrets.protection.from.players")));

        SwitchButton refillSwitch=new SwitchButton(addEntity.getX(),playerProtection.getY()+playerProtection.getHeight(),Component.translatable("k_turrets.refill.inventory"),Component.translatable("k_turrets.dont.refill.inventory"),turret.isRefillingInventory(),pButton -> {
            SwitchButton b= (SwitchButton) pButton;
            b.state=!b.state;
            turret.setRefillInventory(b.state);
            KTurrets.channel.sendToServer(new SetRefillInventory(b.state,turret.getId()));
        });
        wrapper.addRenderableWidget(refillSwitch);
        hideableWidgets.add(refillSwitch);
        refillSwitch.setTooltip(Tooltip.create(Component.translatable("k_turrets.refill.info")));

        SwitchButton protectSwitch=new SwitchButton(addEntity.getX(),refillSwitch.getY()+refillSwitch.getHeight(),Component.translatable("k_turrets.protect.player"),Component.translatable("k_turrets.do.not.protect.player"),turret.isProtectingOwner(),pButton -> {
           SwitchButton switchButton= (SwitchButton) pButton;
           switchButton.state=!switchButton.state;
           turret.setProtectOwner(switchButton.state);
           KTurrets.channel.sendToServer(new SetProtectPlayer(turret.getId(),switchButton.state));
        });
        wrapper.addRenderableWidget(protectSwitch);
        hideableWidgets.add(protectSwitch);
        protectSwitch.setTooltip(Tooltip.create(Component.translatable("k_turrets.player.protection")));

        turret.getOwner().ifPresentOrElse(uuid -> {
            if(turret instanceof Drone drone)
            {
                DropDownButton dropDownButton=new DropDownButton(addEntity.getX(),protectSwitch.getY()+protectSwitch.getHeight(),wrapper,Component.literal(""));
                LinkedHashMap<Component, Button.OnPress> linkedHashMap = new LinkedHashMap<>(3);
                RadioButton follow=new RadioButton(addEntity.getX(),dropDownButton.getY()+dropDownButton.getHeight(),Component.translatable("k_turrets.following.owner"));
                linkedHashMap.put(follow.getMessage(),pButton -> {
                    drone.setBehavior(Drone.Behavior.FOLLOW);
                    dropDownButton.onPress();
                    KTurrets.channel.sendToServer(new SetBehavior(drone.getId(), Drone.Behavior.FOLLOW));
                    dropDownButton.setMessage(pButton.getMessage());
                });
                RadioButton guard=new RadioButton(addEntity.getX(),follow.getY()+follow.getElementHeight(),Component.translatable("k_turrets.guard.area"));
                linkedHashMap.put(guard.getMessage(),pButton -> {
                    drone.setBehavior(Drone.Behavior.GUARD);
                    dropDownButton.onPress();
                    KTurrets.channel.sendToServer(new SetBehavior(drone.getId(), Drone.Behavior.GUARD));
                    dropDownButton.setMessage(pButton.getMessage());
                });
                RadioButton stay=new RadioButton(addEntity.getX(),guard.getY()+guard.getElementHeight(),Component.translatable("k_turrets.staying"));
                linkedHashMap.put(stay.getMessage(),pButton -> {
                    drone.setBehavior(Drone.Behavior.STAY);
                    dropDownButton.onPress();
                    KTurrets.channel.sendToServer(new SetBehavior(drone.getId(), Drone.Behavior.STAY));
                    dropDownButton.setMessage(pButton.getMessage());
                });
                dropDownButton.setChoices(linkedHashMap,drone.getBehavior().ordinal());
                wrapper.addRenderableWidget(dropDownButton);
                hideableWidgets.add(dropDownButton);
            }
        },()->
        {
            BetterButton claim=new BetterButton(addEntity.getX(),protectSwitch.getY()+protectSwitch.getHeight(),Component.translatable("k_turrets.claim.drone"),pButton -> {
                turret.setOwner(wrapper.getMinecraft().player.getUUID());
                wrapper.closeGui();
                KTurrets.channel.sendToServer(new ClaimTurret(turret.getId(),wrapper.getMinecraft().player.getUUID()));
            });
            wrapper.addRenderableWidget(claim);
            hideableWidgets.add(claim);
        });
        Label range = new Label(addEntity.getX(), protectSwitch.getY() + protectSwitch.getHeight() + 20, Component.translatable(KTurrets.ID + ".range").append(": ").append("" + turret.getRange()), Constants.BLACK);
        hideableWidgets.add(wrapper.addRenderableWidget(range));
        Label health = new Label(addEntity.getX(), range.getY() + range.getHeight(), Component.translatable(KTurrets.ID + ".integrity").append(": ").append(String.format("%.1f", turret.getHealth()) + "/" + turret.getMaxHealth()), Constants.BLACK);
        hideableWidgets.add(wrapper.addRenderableWidget(health));
        int primaryDamage=turret.getDamage();
        int secondaryDamage= turret.getSecondaryDamage();
        MutableComponent damageText = Component.translatable("k_turrets.damage", primaryDamage);
        if(secondaryDamage>0)
            damageText.append("/"+secondaryDamage);
        Label damage=new Label(addEntity.getX(),health.getY()+range.getHeight(), damageText,Constants.BLACK);
        hideableWidgets.add(damage);
        wrapper.addRenderableWidget(damage);
        Label armor=new Label(addEntity.getX(),damage.getY()+damage.getHeight(),Component.translatable("k_turrets.armor",String.format("%.1f",turret.getAttribute(Attributes.ARMOR).getValue())),Constants.BLACK);
        hideableWidgets.add(armor);
        wrapper.addRenderableWidget(armor);
    }

    private class TextButton extends SimpleTextButton {
        boolean state;

        public TextButton(Panel var1, MutableComponent entityName,boolean state) {
            super(var1, entityName, Icons.CHECK);
            this.state=state;
        }

        @Override
        public void onClicked(MouseButton mouseButton) {
            state=!state;
            setIcon(state ? Icons.CHECK : Icons.CLOSE);
            playClickSound();
            String string = title.getString();
            if(exceptionButtons.contains(this))
            {
                if(state)
                {
                    turret.addPlayerToExceptions(string);
                    KTurrets.channel.sendToServer(new AddPlayerException(turret.getId(),string));
                }
                else {
                    turret.removePlayerFromExceptions(string);
                    KTurrets.channel.sendToServer(new RemovePlayerException(turret.getId(),string));
                }
            }
            else {
                List<EntityType<?>> targets = Turret.decodeTargets(turret.getTargets());
                if (state) {
                    targets.add(ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(string)));
                    turret.setTargets(Turret.encodeTargets(targets));
                }
                KTurrets.channel.sendToServer(new SetTarget(state, string, UnitOptionsScreen.this.turret.getId()));
            }
        }
    }
}
