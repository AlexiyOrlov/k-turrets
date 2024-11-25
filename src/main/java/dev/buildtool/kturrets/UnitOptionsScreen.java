package dev.buildtool.kturrets;

import dev.buildtool.kturrets.packets.*;
import dev.buildtool.satako.IntegerColor;
import dev.buildtool.satako.UniqueList;
import dev.buildtool.satako.gui.*;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.*;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractButtonListScreen;
import dev.ftb.mods.ftblibrary.util.client.PositionedIngredient;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.glfw.GLFW;

import java.util.*;
import java.util.stream.Collectors;

public class UnitOptionsScreen extends ButtonListScreen {
    protected Turret turret;
    private final List<Label> suggestions=new ArrayList<>(14);
    private final UniqueList<EntityType<?>> targets;
    public CombinedScreen wrapper;
    private final ArrayList<BetterButton> hideableWidgets=new ArrayList<>();
    private dev.buildtool.satako.gui.TextField addEntity;

    public UnitOptionsScreen(Turret turret) {
        this.turret=turret;
        targets=new UniqueList<>(Turret.decodeTargets(turret.getTargets()));
        showBottomPanel(false);
        initGui();
        setFullscreen();
        setTitle(Component.translatable("k_turrets.targets"));
    }

    @Override
    protected void doCancel() {

    }

    @Override
    protected void doAccept() {

    }

    @Override
    public void addButtons(Panel var1) {
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
                    List<ResourceLocation> entityTypes;
                    entityTypes = new ArrayList<>(ForgeRegistries.ENTITY_TYPES.getKeys().stream().filter(resourceLocation -> resourceLocation.toString().contains(text)).toList());
                    int yOffset = 20;
                    entityTypes.removeAll(targets.stream().map(ForgeRegistries.ENTITY_TYPES::getKey).toList());
                    for (ResourceLocation entityType : entityTypes.subList(0, Math.min(entityTypes.size(), 14))) {
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
            EntityType<?> entityTypesValue = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(string));
            targets.add(entityTypesValue);
            KTurrets.channel.sendToServer(new SetTarget(true, string, turret.getId()));
            addEntity.setValue("");
            mainPanel.refreshWidgets();

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

        BetterButton resetTargets=new BetterButton(addEntity.getX(),clearTargets.getY()+clearTargets.getHeight(),Component.translatable("k_turrets.reset.list"),pButton -> {
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

        SwitchButton playerProtection=new SwitchButton(addEntity.getX(),mobility.getY()+mobility.getHeight(),Component.translatable("k_turrets.protect.from.players"),Component.translatable("k_turrets.not.protect.from.players"),turret.isProtectingFromPlayers(),pButton -> {
            SwitchButton switchButton1= (SwitchButton) pButton;
            switchButton1.state=!switchButton1.state;
            turret.setProtectionFromPlayers(switchButton1.state);
            KTurrets.channel.sendToServer(new TogglePlayerProtection(switchButton1.state,turret.getId()));
        });
        wrapper.addRenderableWidget(playerProtection);
        hideableWidgets.add(playerProtection);

        SwitchButton refillSwitch=new SwitchButton(addEntity.getX(),playerProtection.getY()+playerProtection.getHeight(),Component.translatable("k_turrets.refill.inventory"),Component.translatable("k_turrets.dont.refill.inventory"),turret.isRefillingInventory(),pButton -> {
            SwitchButton b= (SwitchButton) pButton;
            b.state=!b.state;
            turret.setRefillInventory(b.state);
            KTurrets.channel.sendToServer(new SetRefillInventory(b.state,turret.getId()));
        });
        wrapper.addRenderableWidget(refillSwitch);
        hideableWidgets.add(refillSwitch);

        turret.getOwner().ifPresentOrElse(uuid -> {
            if(turret instanceof Drone drone)
            {

            }
        },()->
        {
            BetterButton claim=new BetterButton(addEntity.getX(),refillSwitch.getY()+refillSwitch.getHeight(),Component.translatable("k_turrets.claim.drone"),pButton -> {
                turret.setOwner(wrapper.getMinecraft().player.getUUID());
                wrapper.closeGui();
                KTurrets.channel.sendToServer(new ClaimTurret(turret.getId(),wrapper.getMinecraft().player.getUUID()));
            });
            wrapper.addRenderableWidget(claim);
            hideableWidgets.add(claim);
        });
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
            List<EntityType<?>> targets=Turret.decodeTargets(turret.getTargets());
            if(state)
            {
                targets.add(ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(title.getString())));
                turret.setTargets(Turret.encodeTargets(targets));
            }
            KTurrets.channel.sendToServer(new SetTarget(state, title.getString(), UnitOptionsScreen.this.turret.getId()));
        }
    }
}
