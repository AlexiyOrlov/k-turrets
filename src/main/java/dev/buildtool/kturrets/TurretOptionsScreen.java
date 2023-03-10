package dev.buildtool.kturrets;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.buildtool.kturrets.packets.*;
import dev.buildtool.satako.UniqueList;
import dev.buildtool.satako.gui.*;
import net.minecraft.ChatFormatting;
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
    private static final Component SCROLL_HINT = Component.translatable("k_turrets.hold.alt.to.scroll");
    private List<SwitchButton> targetButtons;
    private TextField addEntityField;

    private final List<Label> suggestions;
    private BetterButton addTarget, dismantle, clearTargets, resetList, mobilitySwitch, protectionFromPlayers, claimTurret,
            followSwitch;
    private boolean renderLabels = true;

    public TurretOptionsScreen(Turret turret) {
        super(Component.translatable("k_turrets.targets"));
        this.turret = turret;
        tempStatusMap = new HashMap<>(40);
        targets = new UniqueList<>(turret.decodeTargets(turret.getTargets()));
        targets.forEach(entityType -> tempStatusMap.put(entityType, true));
        suggestions = new ArrayList<>(12);
    }

    @Override
    public void init() {
        super.init();
        addEntityField = addRenderableWidget(new TextField(centerX, 3, 180));
        addRenderableWidget(addTarget = new BetterButton(centerX, 20, Component.translatable("k_turrets.add.entity.type"), p_onPress_1_ -> {
            String entityType = addEntityField.getValue();
            EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(entityType));
            if (entityType.length() > 2) {
                if (type != null) {
                    if (type == EntityType.PIG && !entityType.equals("minecraft:pig") && !entityType.equals("pig")) {
                        minecraft.player.displayClientMessage(Component.translatable("k_turrets.incorrect.entry"), true);
                    } else {

                        targets.add(type);
                        tempStatusMap.put(type, true);
                        minecraft.player.displayClientMessage(Component.translatable("k_turrets.added").append(" ").append(type.getDescription()), true);
                        if (entityType.contains(":"))
                            addEntityField.setValue(entityType.substring(0, entityType.indexOf(':')));
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
            targets.forEach(entityType -> tempStatusMap.put(entityType, true));
            minecraft.screen.onClose();
            minecraft.player.closeContainer();
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
        if (!turret.getOwner().isPresent())
            addRenderableWidget(claimTurret = new BetterButton(centerX, 120, Component.translatable(turret instanceof Drone ? "k_turrets.claim.drone" : "k_turrets.claim.turret"), p_onPress_1_ -> {
                KTurrets.channel.sendToServer(new ClaimTurret(turret.getId(), minecraft.player.getUUID()));
                turret.setOwner(minecraft.player.getUUID());
                minecraft.player.closeContainer();
            }));
        else if (turret instanceof Drone drone) {
            addRenderableWidget(followSwitch = new SwitchButton(centerX, 120, Component.translatable("k_turrets.following.owner"), Component.translatable("k_turrets.staying"), drone.isFollowingOwner(), p_93751_ -> {
                KTurrets.channel.sendToServer(new ToggleDroneFollow(!drone.isFollowingOwner(), drone.getId()));
                drone.followOwner(!drone.isFollowingOwner());
                if (p_93751_ instanceof SwitchButton switchButton)
                    switchButton.state = !switchButton.state;
            }));
        }

        addRenderableWidget(new Label(3, 3, Component.translatable("k_turrets.targets")));
        targetButtons = new ArrayList<>(targets.size());
        for (int i = 0; i < targets.size(); i++) {
            EntityType<?> entityType = targets.get(i);
            SwitchButton switchButton = new SwitchButton(3, 20 * i + 40, Component.literal(ForgeRegistries.ENTITY_TYPES.getKey(entityType).toString()), Component.literal(ChatFormatting.STRIKETHROUGH + ForgeRegistries.ENTITY_TYPES.getKey(entityType).toString()), true, p_onPress_1_ -> {
                if (p_onPress_1_ instanceof SwitchButton) {
                    ((SwitchButton) p_onPress_1_).state = !((SwitchButton) p_onPress_1_).state;
                    tempStatusMap.put(entityType, ((SwitchButton) p_onPress_1_).state);
                }
            });
            switchButton.verticalScroll = true;
            addRenderableWidget(switchButton);
            targetButtons.add(switchButton);
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
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float tick) {
        super.render(matrixStack, mouseX, mouseY, tick);
        if (renderLabels) {
            renderComponentTooltip(matrixStack, Collections.singletonList(Component.translatable("k_turrets.integrity").append(": " + (int) turret.getHealth() + "/" + turret.getMaxHealth())), centerX, centerY + 40, font);
            renderComponentTooltip(matrixStack, Arrays.asList(CHOOSE_HINT, SCROLL_HINT), centerX, centerY + 80, font);
            if (turret.getAutomaticTeam().isEmpty()) {
                renderComponentTooltip(matrixStack, Collections.singletonList(Component.translatable("k_turrets.no.team")), centerX, centerY + 60, font);
            } else {
                renderComponentTooltip(matrixStack, Collections.singletonList(Component.translatable("k_turrets.team").append(": " + turret.getAutomaticTeam())), centerX, centerY + 60, font);
            }
        }
    }

    @Override
    public boolean keyReleased(int p_94715_, int p_94716_, int p_94717_) {
        if (addEntityField.isFocused()) {
            suggestions.forEach(this::removeWidget);
            suggestions.clear();
            String text = addEntityField.getValue();
            if (text.length() > 0) {
                List<ResourceLocation> entityTypes;
                if (text.contains(":"))
                    entityTypes = ForgeRegistries.ENTITY_TYPES.getKeys().stream().filter(resourceLocation -> resourceLocation.toString().contains(text)).toList();
                else
                    entityTypes = ForgeRegistries.ENTITY_TYPES.getKeys().stream().filter(resourceLocation -> resourceLocation.getNamespace().contains(text)).toList();
                int yOffset = 20;
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
                    this.addTarget.setHidden();
                    this.claimTurret.setHidden();
                    this.clearTargets.setHidden();
                    this.dismantle.setHidden();
                    if (followSwitch != null)
                        this.followSwitch.setHidden();
                    this.mobilitySwitch.setHidden();
                    this.protectionFromPlayers.setHidden();
                    this.resetList.setHidden();
                    renderLabels = false;
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
        this.addTarget.setVisible();
        this.claimTurret.setVisible();
        this.clearTargets.setVisible();
        this.dismantle.setVisible();
        if (followSwitch != null)
            this.followSwitch.setVisible();
        this.mobilitySwitch.setVisible();
        this.protectionFromPlayers.setVisible();
        this.resetList.setVisible();
        renderLabels = true;
    }
}
