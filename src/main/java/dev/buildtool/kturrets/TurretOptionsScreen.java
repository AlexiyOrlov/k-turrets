package dev.buildtool.kturrets;

import dev.buildtool.kturrets.arrow.ArrowDrone;
import dev.buildtool.kturrets.arrow.ArrowTurret;
import dev.buildtool.kturrets.brick.BrickDrone;
import dev.buildtool.kturrets.brick.BrickTurret;
import dev.buildtool.kturrets.bullet.BulletDrone;
import dev.buildtool.kturrets.bullet.BulletTurret;
import dev.buildtool.kturrets.cobble.CobbleDrone;
import dev.buildtool.kturrets.cobble.CobbleTurret;
import dev.buildtool.kturrets.fireball.FireballDrone;
import dev.buildtool.kturrets.fireball.FireballTurret;
import dev.buildtool.kturrets.gauss.GaussDrone;
import dev.buildtool.kturrets.gauss.GaussTurret;
import dev.buildtool.kturrets.packets.*;
import dev.buildtool.satako.IntegerColor;
import dev.buildtool.satako.UniqueList;
import dev.buildtool.satako.gui.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TurretOptionsScreen extends Screen2 {
    protected Turret turret;
    protected HashMap<EntityType<?>, Boolean> tempStatusMap;
    protected List<EntityType<?>> targets;
    private static final Component CHOOSE_HINT = Component.translatable("k_turrets.choose.tooltip");
    private List<SwitchButton> targetButtons;
    private TextField addEntityField;
    private List<String> exceptions;
    private HashMap<String, Boolean> tempExceptionStatus;
    private List<Label> suggestions;
    private BetterButton addTarget;
    private BetterButton dismantle;
    private BetterButton clearTargets;
    private BetterButton resetList;
    private BetterButton mobilitySwitch;
    private BetterButton protectionFromPlayers;
    private BetterButton claimTurret;
    private BetterButton inventoryRefillSwitch;

    private DropDownButton dropDownButton;

    public TurretOptionsScreen(Turret turret) {
        super(Component.translatable("k_turrets.targets"));
        this.turret = turret;
        targets = new UniqueList<>(Turret.decodeTargets(turret.getTargets()));
    }

    @Override
    public void init() {
        super.init();
        tempStatusMap = new HashMap<>(40);
        targets.forEach(entityType -> tempStatusMap.put(entityType, true));
        exceptions = turret.getExceptions();
        tempExceptionStatus = new HashMap<>(1);
        exceptions.forEach(s -> tempExceptionStatus.put(s, true));
        suggestions = new ArrayList<>(12);
        addEntityField = addRenderableWidget(new TextField(centerX, 3, 180));
        addEntityField.setMaxLength(64);
        addRenderableWidget(addTarget = new BetterButton(centerX, 20, Component.translatable("k_turrets.add.entity.type"), p_onPress_1_ -> {
            String s = addEntityField.getValue();
            if (s.startsWith("!")) {
                if (s.length() > 1) {
                    String playerName = s.substring(1);
                    if (exceptions.contains(playerName))
                        minecraft.player.displayClientMessage(Component.translatable("k_turrets.player.is.already.in.exceptions", playerName), true);
                    else {
                        turret.addPlayerToExceptions(playerName);
                        tempExceptionStatus.put(playerName, true);
                        KTurrets.channel.sendToServer(new AddPlayerException(turret.getId(), playerName));
                        addEntityField.setValue("");
                        rebuildWidgets();
                    }
                }
            } else {
                EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(s));
                if (s.length() > 2) {
                    if (type != null) {
                        if (type == EntityType.PIG && !s.equals("minecraft:pig") && !s.equals("pig")) {
                            minecraft.player.displayClientMessage(Component.translatable("k_turrets.incorrect.entry"), true);
                        } else {
                            targets.add(type);
                            tempStatusMap.put(type, true);
                            if (s.contains(":"))
                                addEntityField.setValue(s.substring(0, s.indexOf(':')));
                            rebuildWidgets();
                        }
                    }
                }
            }
        }));

        addRenderableWidget(dismantle = new BetterButton(centerX, 40, Component.translatable("k_turrets.dismantle"), p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new DismantleTurret(turret.getId()));
            minecraft.player.closeContainer();
        }));
        clearTargets = new BetterButton(centerX, 60, Component.translatable("k_turrets.clear.list"), p_onPress_1_ -> {
            targets.clear();
            tempStatusMap.clear();
            targetButtons.forEach(renderables::remove);
        });
        addRenderableWidget(clearTargets);
        addRenderableWidget(resetList = new BetterButton(clearTargets.getX() + clearTargets.getWidth(), 60, Component.translatable("k_turrets.reset.list"), p_93751_ -> {
            targets = ForgeRegistries.ENTITY_TYPES.getValues().stream().filter(entityType1 -> !entityType1.getCategory().isFriendly()).collect(Collectors.toList());
            this.rebuildWidgets();

        }));
        addRenderableWidget(mobilitySwitch = new SwitchButton(centerX, 80, Component.translatable("k_turrets.mobile"), Component.translatable("k_turrets.immobile"), turret.isMoveable(), p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new ToggleMobility(!turret.isMoveable(), turret.getId()));
            turret.setMoveable(!turret.isMoveable());
            if (p_onPress_1_ instanceof SwitchButton) {
                ((SwitchButton) p_onPress_1_).state = !((SwitchButton) p_onPress_1_).state;
            }
        }));
        addRenderableWidget(protectionFromPlayers = new SwitchButton(centerX, 100, Component.translatable("k_turrets.protect.from.players"), Component.translatable("k_turrets.not.protect.from.players"), turret.isProtectingFromPlayers(), p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new TogglePlayerProtection(!turret.isProtectingFromPlayers(), turret.getId()));
            turret.setProtectionFromPlayers(!turret.isProtectingFromPlayers());
            if (p_onPress_1_ instanceof SwitchButton)
                ((SwitchButton) p_onPress_1_).state = !((SwitchButton) p_onPress_1_).state;
        }));
        addRenderableWidget(inventoryRefillSwitch = new SwitchButton(centerX, 120, Component.translatable("k_turrets.refill.inventory"), Component.translatable("k_turrets.dont.refill.inventory"), turret.isRefillingInventory(), button -> {
            KTurrets.channel.sendToServer(new SetRefillInventory(!turret.isRefillingInventory(), turret.getId()));
            turret.setRefillInventory(!turret.isRefillingInventory());
            if (button instanceof SwitchButton switchButton) {
                switchButton.state = !switchButton.state;
            }
        }));
        if (!turret.getOwner().isPresent())
            addRenderableWidget(claimTurret = new BetterButton(centerX, 140, Component.translatable(turret instanceof Drone ? "k_turrets.claim.drone" : "k_turrets.claim.turret"), p_onPress_1_ -> {
                KTurrets.channel.sendToServer(new ClaimTurret(turret.getId(), minecraft.player.getUUID()));
                turret.setOwner(minecraft.player.getUUID());
                minecraft.player.closeContainer();
            }));
        else if (turret instanceof Drone drone) {
            dropDownButton = new DropDownButton(centerX, 140, this, Component.literal(""));
            LinkedHashMap<Component, Button.OnPress> linkedHashMap = new LinkedHashMap<>(3);
            RadioButton follow = new RadioButton(centerX, 140, Component.translatable("k_turrets.following.owner"));
            linkedHashMap.put(follow.getMessage(), p_93751_ -> {
                KTurrets.channel.sendToServer(new ToggleDroneFollow(true, drone.getId()));
                drone.followOwner(true);
                drone.setGuardArea(false);
                KTurrets.channel.sendToServer(new ToggleGuardingArea(drone.getId(), false));
                dropDownButton.setMessage(p_93751_.getMessage());
                dropDownButton.onPress();
            });
            RadioButton stay = new RadioButton(centerX, 160, Component.translatable("k_turrets.staying"));
            linkedHashMap.put(stay.getMessage(), p_93751_ -> {
                KTurrets.channel.sendToServer(new ToggleDroneFollow(false, drone.getId()));
                KTurrets.channel.sendToServer(new ToggleGuardingArea(drone.getId(), false));
                drone.followOwner(false);
                drone.setGuardArea(false);
                dropDownButton.setMessage(p_93751_.getMessage());
                dropDownButton.onPress();
            });
            RadioButton guard = new RadioButton(centerX, 180, Component.translatable("k_turrets.guard.area"));
            linkedHashMap.put(guard.getMessage(), p_93751_ -> {
                drone.followOwner(false);
                KTurrets.channel.sendToServer(new ToggleDroneFollow(false, drone.getId()));
                KTurrets.channel.sendToServer(new ToggleGuardingArea(drone.getId(), true));
                drone.setGuardArea(true);
                dropDownButton.setMessage(p_93751_.getMessage());
                dropDownButton.onPress();
            });
            dropDownButton.setChoices(linkedHashMap, drone.isGuardingArea() ? 2 : drone.isFollowingOwner() ? 0 : 1);
            addRenderableWidget(dropDownButton);
        }
        List<GuiEventListener> guiEventListeners = new ArrayList<>();
        List<SwitchButton> exceptionButtons = new ArrayList<>(19);
        if (!exceptions.isEmpty()) {
            Label label = new Label(3, 3, Component.translatable("k_turrets.exceptions").append(":"));
            addRenderableWidget(label);
            label.setScrollable(true, true);
            guiEventListeners.add(label);
            for (int i = 0; i < exceptions.size(); i++) {
                String next = exceptions.get(i);
                SwitchButton switchButton = new SwitchButton(3, 20 * i + label.getY() + label.getHeight(), Component.literal(next), Component.literal(ChatFormatting.STRIKETHROUGH + next), true, p_93751_ -> {
                    if (p_93751_ instanceof SwitchButton switchButton1) {
                        switchButton1.state = !switchButton1.state;
                        tempExceptionStatus.put(next, switchButton1.state);
                    }
                });
                switchButton.verticalScroll = true;
                addRenderableWidget(switchButton);
                exceptionButtons.add(switchButton);
                guiEventListeners.add(switchButton);
            }
        }

        Label label = addRenderableWidget(new Label(3, !exceptionButtons.isEmpty() ? exceptionButtons.get(exceptionButtons.size() - 1).getY() + exceptionButtons.get(exceptionButtons.size() - 1).getHeight() + 20 : 3, Component.translatable("k_turrets.targets")));
        label.setScrollable(true, true);
        guiEventListeners.add(label);
        targetButtons = new ArrayList<>(targets.size());
        targets.sort(Comparator.comparing(o -> ForgeRegistries.ENTITY_TYPES.getKey(o).toString()));
        for (int i = 0; i < targets.size(); i++) {
            EntityType<?> entityType = targets.get(i);
            SwitchButton switchButton = new SwitchButton(3, 20 * i + label.getY() + label.getHeight(), Component.literal(ForgeRegistries.ENTITY_TYPES.getKey(entityType).toString()), Component.literal(ChatFormatting.STRIKETHROUGH + ForgeRegistries.ENTITY_TYPES.getKey(entityType).toString()), true, p_onPress_1_ -> {
                if (p_onPress_1_ instanceof SwitchButton) {
                    ((SwitchButton) p_onPress_1_).state = !((SwitchButton) p_onPress_1_).state;
                    tempStatusMap.put(entityType, ((SwitchButton) p_onPress_1_).state);
                }
            });
            switchButton.verticalScroll = true;
            addRenderableWidget(switchButton);
            targetButtons.add(switchButton);
            guiEventListeners.add(switchButton);
        }
        ScrollArea scrollArea = new ScrollArea(3, 3, centerX - 15, height, Component.literal(""), new IntegerColor(0x228FDBF0), guiEventListeners);
        addRenderableWidget(scrollArea);
        if (turret.getAutomaticTeam().isEmpty())
            addRenderableWidget(new Label(centerX, 160, Component.translatable("k_turrets.no.team")));
        else
            addRenderableWidget(new Label(centerX, 160, Component.translatable("k_turrets.team").append(": " + turret.getAutomaticTeam())));
        addRenderableWidget(new Label(centerX, 180, CHOOSE_HINT));
        addRenderableWidget(new Label(centerX, 200, Component.translatable(KTurrets.ID + ".range").append(": ").append("" + turret.getRange())));
        addRenderableWidget(new Label(centerX, 220, Component.translatable(KTurrets.ID + ".integrity").append(": ").append(turret.getHealth() + "/" + turret.getMaxHealth())));
        if (turret instanceof ArrowTurret || turret instanceof ArrowDrone) {
            addRenderableWidget(new Label(centerX, 240, Component.translatable(KTurrets.ID + ".damage").append(": ").append(KTurrets.ARROW_TURRET_DAMAGE.get() + "")));
        } else if (turret instanceof BrickTurret || turret instanceof BrickDrone) {
            addRenderableWidget(new Label(centerX, 240, Component.translatable(KTurrets.ID + ".damage").append(": ").append(KTurrets.BRICK_DAMAGE.get() + "/" + KTurrets.NETHERBRICK_DAMAGE.get())));
        } else if (turret instanceof BulletTurret || turret instanceof BulletDrone) {
            addRenderableWidget(new Label(centerX, 240, Component.translatable(KTurrets.ID + ".damage").append(": ").append(KTurrets.IRON_BULLET_DAMAGE.get() + "/" + KTurrets.GOLD_BULLET_DAMAGE.get())));
        } else if (turret instanceof FireballTurret || turret instanceof FireballDrone) {
            addRenderableWidget(new Label(centerX, 240, Component.translatable(KTurrets.ID + ".damage").append(": ").append(KTurrets.CHARGE_TURRET_DAMAGE.get() + "")));
        } else if (turret instanceof CobbleTurret || turret instanceof CobbleDrone) {
            addRenderableWidget(new Label(centerX, 240, Component.translatable(KTurrets.ID + ".damage").append(": ").append(KTurrets.COBBLE_TURRET_DAMAGE.get() + "")));
        } else if (turret instanceof GaussTurret || turret instanceof GaussDrone) {
            addRenderableWidget(new Label(centerX, 240, Component.translatable(KTurrets.ID + ".damage").append(": ").append(KTurrets.GAUSS_TURRET_DAMAGE.get() + "")));
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        AtomicInteger removed = new AtomicInteger();
        tempStatusMap.forEach((entityType, aBoolean) -> {
            if (aBoolean)
                targets.add(entityType);
            else {
                targets.remove(entityType);
                removed.getAndIncrement();
            }
        });
        CompoundTag compoundNBT = turret.encodeTargets(targets);
        turret.setTargets(compoundNBT);
        TurretTargets turretTargets = new TurretTargets(compoundNBT, turret.getId());
        KTurrets.channel.sendToServer(turretTargets);
        if (removed.get() > 0)
            minecraft.player.displayClientMessage(Component.translatable("k_turrets.removed", removed.get()), false);
        tempExceptionStatus.forEach((s, aBoolean) -> {
            if (aBoolean) {
                if (!exceptions.contains(s)) {
                    turret.addPlayerToExceptions(s);
                    KTurrets.channel.sendToServer(new AddPlayerException(turret.getId(), s));
                }
            } else {
                turret.removePlayerFromExceptions(s);
                KTurrets.channel.sendToServer(new RemovePlayerException(turret.getId(), s));
            }
        });
    }

    @Override
    public boolean keyReleased(int p_94715_, int p_94716_, int p_94717_) {
        if (addEntityField.isFocused()) {
            suggestions.forEach(this::removeWidget);
            suggestions.clear();
            String text = addEntityField.getValue();
            if (!text.isEmpty()) {
                List<ResourceLocation> entityTypes;
                entityTypes = new ArrayList<>(ForgeRegistries.ENTITY_TYPES.getKeys().stream().filter(resourceLocation -> resourceLocation.toString().contains(text)).toList());
                int yOffset = 20;
                entityTypes.removeAll(targets.stream().map(ForgeRegistries.ENTITY_TYPES::getKey).toList());
                for (ResourceLocation entityType : entityTypes.subList(0, Math.min(entityTypes.size(), 14))) {
                    Label hint = new Label(addEntityField.getX(), addEntityField.getY() + yOffset, Component.literal(ChatFormatting.YELLOW + entityType.toString()), this, p_93751_ -> {
                        addEntityField.setValue(p_93751_.getMessage().getString().substring(2));
                        suggestions.forEach(this::removeWidget);
                        suggestions.clear();
                        showButtonsAndHints();
                    });
                    addRenderableWidget(hint);
                    suggestions.add(hint);
                    yOffset += 14;
                }
                if (!entityTypes.isEmpty()) {
                    this.addTarget.setHidden(true);
                    if (claimTurret != null)
                        this.claimTurret.setHidden(true);
                    this.clearTargets.setHidden(true);
                    this.dismantle.setHidden(true);
                    this.mobilitySwitch.setHidden(true);
                    this.protectionFromPlayers.setHidden(true);
                    this.resetList.setHidden(true);
                    if (dropDownButton != null)
                        dropDownButton.setHidden(true);
                } else {
                    showButtonsAndHints();
                }
            } else {
                showButtonsAndHints();
            }
        }
        return super.keyReleased(p_94715_, p_94716_, p_94717_);
    }

    private void showButtonsAndHints() {
        this.addTarget.setHidden(false);
        if (claimTurret != null)
            this.claimTurret.setHidden(false);
        this.clearTargets.setHidden(false);
        this.dismantle.setHidden(false);
        this.mobilitySwitch.setHidden(false);
        this.protectionFromPlayers.setHidden(false);
        this.resetList.setHidden(false);
        if (dropDownButton != null)
            dropDownButton.setHidden(false);
    }
}
