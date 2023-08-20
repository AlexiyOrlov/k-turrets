package dev.buildtool.kturrets;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.buildtool.kturrets.packets.*;
import dev.buildtool.satako.UniqueList;
import dev.buildtool.satako.gui.*;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
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
    private static final TranslatableComponent CHOOSE_HINT = new TranslatableComponent("k_turrets.choose.tooltip");
    private static final TranslatableComponent SCROLL_HINT = new TranslatableComponent("k_turrets.hold.alt.to.scroll");
    private static final TranslatableComponent INVENTORY_HINT = new TranslatableComponent("k_turrets.inventory.hint");
    private List<SwitchButton> targetButtons;
    private TextField addEntityField;
    private List<String> exceptions;
    private HashMap<String, Boolean> tempExceptionStatus;

    public TurretOptionsScreen(Turret turret) {
        super(new TranslatableComponent("k_turrets.targets"));
        this.turret = turret;
        tempStatusMap = new HashMap<>(40);
        targets = new UniqueList<>(Turret.decodeTargets(turret.getTargets()));
        targets.forEach(entityType -> tempStatusMap.put(entityType, true));
    }

    @Override
    public void init() {
        super.init();
        exceptions = turret.getExceptions();
        tempExceptionStatus = new HashMap<>(1);
        exceptions.forEach(s -> tempExceptionStatus.put(s, true));
        addEntityField = addRenderableWidget(new TextField(centerX, 3, 100));
        addRenderableWidget(new BetterButton(centerX, 20, new TranslatableComponent("k_turrets.add.entity.type"), p_onPress_1_ -> {
            String s = addEntityField.getValue();
            if (s.startsWith("!")) {
                if (s.length() > 1) {
                    String playerName = s.substring(1);
                    if (exceptions.contains(playerName))
                        minecraft.player.displayClientMessage(new TranslatableComponent("k_turrets.player.is.already.in.exceptions", playerName), true);
                    else {
                        turret.addPlayerToExceptions(playerName);
                        tempExceptionStatus.put(playerName, true);
                        KTurrets.channel.sendToServer(new AddPlayerException(turret.getId(), playerName));
                        addEntityField.setValue("");
                    }
                }
            } else {
                EntityType<?> type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(s));
                if (s.length() > 2) {
                    if (type != null) {
                        if (type == EntityType.PIG && !s.equals("minecraft:pig") && !s.equals("pig")) {
                            minecraft.player.sendMessage(new TranslatableComponent("k_turrets.incorrect.entry"), Util.NIL_UUID);
                        } else {
                            targets.add(type);
                            tempStatusMap.put(type, true);
                            minecraft.player.sendMessage(new TranslatableComponent("k_turrets.added").append(" ").append(type.getDescription()), Util.NIL_UUID);
                            if (s.contains(":"))
                                addEntityField.setValue(s.substring(0, s.indexOf(':')));
                        }
                    }
                }
            }
        }));
        addRenderableWidget(new BetterButton(centerX, 40, new TranslatableComponent("k_turrets.dismantle"), p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new DismantleTurret(turret.getId()));
            minecraft.player.closeContainer();
        }));
        BetterButton clearTargets = new BetterButton(centerX, 60, new TranslatableComponent("k_turrets.clear.list"), p_onPress_1_ -> {
            targets.clear();
            tempStatusMap.clear();
            targetButtons.forEach(renderables::remove);
        });
        addRenderableWidget(clearTargets);
        addRenderableWidget(new BetterButton(clearTargets.x + clearTargets.getWidth(), 60, new TranslatableComponent("k_turrets.reset.list"), p_93751_ -> {
            targets = ForgeRegistries.ENTITIES.getValues().stream().filter(entityType1 -> !entityType1.getCategory().isFriendly()).collect(Collectors.toList());
            targets.forEach(entityType -> tempStatusMap.put(entityType, true));
            minecraft.screen.onClose();
            minecraft.player.closeContainer();
        }));
        addRenderableWidget(new SwitchButton(centerX, 80, new TranslatableComponent("k_turrets.mobile"), new TranslatableComponent("k_turrets.immobile"), turret.isMoveable(), p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new ToggleMobility(!turret.isMoveable(), turret.getId()));
            turret.setMoveable(!turret.isMoveable());
            if (p_onPress_1_ instanceof SwitchButton) {
                ((SwitchButton) p_onPress_1_).state = !((SwitchButton) p_onPress_1_).state;
            }
        }));
        addRenderableWidget(new SwitchButton(centerX, 100, new TranslatableComponent("k_turrets.protect.from.players"), new TranslatableComponent("k_turrets.not.protect.from.players"), turret.isProtectingFromPlayers(), p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new TogglePlayerProtection(!turret.isProtectingFromPlayers(), turret.getId()));
            turret.setProtectionFromPlayers(!turret.isProtectingFromPlayers());
            if (p_onPress_1_ instanceof SwitchButton)
                ((SwitchButton) p_onPress_1_).state = !((SwitchButton) p_onPress_1_).state;
        }));
        if (!turret.getOwner().isPresent())
            addRenderableWidget(new BetterButton(centerX, 120, new TranslatableComponent(turret instanceof Drone ? "k_turrets.claim.drone" : "k_turrets.claim.turret"), p_onPress_1_ -> {
                KTurrets.channel.sendToServer(new ClaimTurret(turret.getId(), minecraft.player.getUUID()));
                turret.setOwner(minecraft.player.getUUID());
                minecraft.player.closeContainer();
            }));
        else if (turret instanceof Drone drone) {
            addRenderableWidget(new SwitchButton(centerX, 120, new TranslatableComponent("k_turrets.following.owner"), new TranslatableComponent("k_turrets.staying"), drone.isFollowingOwner(), p_93751_ -> {
                KTurrets.channel.sendToServer(new ToggleDroneFollow(!drone.isFollowingOwner(), drone.getId()));
                drone.followOwner(!drone.isFollowingOwner());
                if (p_93751_ instanceof SwitchButton switchButton)
                    switchButton.state = !switchButton.state;
            }));
        }
        List<GuiEventListener> guiEventListeners = new ArrayList<>();
        List<SwitchButton> exceptionButtons = new ArrayList<>(19);
        if (!exceptions.isEmpty()) {
            Label label = new Label(3, 3, new TranslatableComponent("k_turrets.exceptions").append(":"));
            addRenderableWidget(label);
            guiEventListeners.add(label);
            for (int i = 0; i < exceptions.size(); i++) {
                String next = exceptions.get(i);
                SwitchButton switchButton = new SwitchButton(3, 20 * i + label.y + label.getHeight(), new TextComponent(next), new TextComponent(ChatFormatting.STRIKETHROUGH + next), true, p_93751_ -> {
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

        Label label = addRenderableWidget(new Label(3, !exceptionButtons.isEmpty() ? exceptionButtons.get(exceptionButtons.size() - 1).getY() + exceptionButtons.get(exceptionButtons.size() - 1).getHeight() + 20 : 3, new TranslatableComponent("k_turrets.targets")));
        guiEventListeners.add(label);
        targetButtons = new ArrayList<>(targets.size());
        targets.sort(Comparator.comparing(o -> ForgeRegistries.ENTITIES.getKey(o).toString()));
        for (int i = 0; i < targets.size(); i++) {
            EntityType<?> entityType = targets.get(i);
            SwitchButton switchButton = new SwitchButton(3, 20 * i + label.y + label.getHeight(), new TextComponent(ForgeRegistries.ENTITIES.getKey(entityType).toString()), new TextComponent(ChatFormatting.STRIKETHROUGH + ForgeRegistries.ENTITIES.getKey(entityType).toString()), true, p_onPress_1_ -> {
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
            minecraft.player.displayClientMessage(new TranslatableComponent("k_turrets.removed", removed.get()), false);
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
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float tick) {
        super.render(matrixStack, mouseX, mouseY, tick);
        renderComponentTooltip(matrixStack, Collections.singletonList(new TranslatableComponent("k_turrets.integrity").append(": " + (int) turret.getHealth() + "/" + turret.getMaxHealth())), centerX, centerY + 40, font);
        renderComponentTooltip(matrixStack, Arrays.asList(CHOOSE_HINT, SCROLL_HINT), centerX, centerY + 80, font);
        if (turret.getManualTeam().isEmpty()) {
            renderComponentTooltip(matrixStack, Collections.singletonList(new TranslatableComponent("k_turrets.no.team")), centerX, centerY + 60, font);
        } else {
            renderComponentTooltip(matrixStack, Collections.singletonList(new TranslatableComponent("k_turrets.team").append(": " + turret.getManualTeam())), centerX, centerY + 60, font);
        }
        String targetEntry = addEntityField.getValue();
        if (targetEntry.length() > 0) {
            List<ResourceLocation> entityTypes;
            if (targetEntry.contains(":"))
                entityTypes = ForgeRegistries.ENTITIES.getKeys().stream().filter(resourceLocation -> resourceLocation.toString().contains(targetEntry)).collect(Collectors.toList());
            else
                entityTypes = ForgeRegistries.ENTITIES.getKeys().stream().filter(resourceLocation -> resourceLocation.getNamespace().contains(targetEntry)).collect(Collectors.toList());
            if (!entityTypes.isEmpty()) {
                renderComponentTooltip(matrixStack, entityTypes.subList(0, Math.min(entityTypes.size(), 12)).stream().map(resourceLocation -> new TextComponent(ChatFormatting.YELLOW + resourceLocation.toString())).collect(Collectors.toList()), addEntityField.x, addEntityField.y + addEntityField.getHeight() + 20);
            }
        }
    }
}
