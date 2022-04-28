package dev.buildtool.kturrets;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.buildtool.kturrets.packets.*;
import dev.buildtool.satako.UniqueList;
import dev.buildtool.satako.gui.*;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.stream.Collectors;

public class TurretOptionsScreen extends Screen2 {
    protected Turret turret;
    protected HashMap<EntityType<?>, Boolean> tempStatusMap;
    protected List<EntityType<?>> targets;
    private static final TranslatableComponent CHOOSE_HINT = new TranslatableComponent("k-turrets.choose.tooltip");
    private static final TranslatableComponent SCROLL_HINT = new TranslatableComponent("k-turrets.hold.alt.to.scroll");
    private static final TranslatableComponent INVENTORY_HINT = new TranslatableComponent("k-turrets.inventory.hint");
    private List<SwitchButton> targetButtons;
    private TextField addEntityField;

    public TurretOptionsScreen(Turret turret) {
        super(new TranslatableComponent("k-turrets.targets"));
        this.turret = turret;
        tempStatusMap = new HashMap<>(40);
        targets = new UniqueList<>(turret.decodeTargets(turret.getTargets()));
        targets.forEach(entityType -> tempStatusMap.put(entityType, true));
    }

    @Override
    public void init() {
        super.init();
        addEntityField = addWidget(new TextField(centerX, 3, 100));
        addWidget(new BetterButton(centerX, 20, new TranslatableComponent("k-turrets.add.entity.type"), p_onPress_1_ -> {
            String entityType = addEntityField.getValue();
            EntityType<?> type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entityType));
            if (type != null) {
                if (type == EntityType.PIG && !entityType.equals("minecraft:pig") && !entityType.equals("pig")) {
                    minecraft.player.sendMessage(new TranslatableComponent("k-turrets.incorrect.entry"), Util.NIL_UUID);
                } else {
                    targets.add(type);
                    tempStatusMap.put(type, true);
                    minecraft.player.sendMessage(new TranslatableComponent("k-turrets.added").append(" ").append(type.getDescription()), Util.NIL_UUID);
                    addEntityField.setValue(entityType.substring(0, entityType.indexOf(':')));
                }
            }
        }));
        addWidget(new BetterButton(centerX, 40, new TranslatableComponent("k-turrets.dismantle"), p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new DismantleTurret(turret.getId()));
            minecraft.player.closeContainer();
        }));
        addWidget(new BetterButton(centerX, 60, new TranslatableComponent("k-turrets.clear.list"), p_onPress_1_ -> {
            targets.clear();
            tempStatusMap.clear();
            targetButtons.forEach(children()::remove);
        }));
        addWidget(new SwitchButton(centerX, 80, new TranslatableComponent("k-turrets.mobile"), new TranslatableComponent("k-turrets.immobile"), turret.isMoveable(), p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new ToggleMobility(!turret.isMoveable(), turret.getId()));
            turret.setMoveable(!turret.isMoveable());
            if (p_onPress_1_ instanceof SwitchButton) {
                ((SwitchButton) p_onPress_1_).state = !((SwitchButton) p_onPress_1_).state;
            }
        }));
        addWidget(new SwitchButton(centerX, 100, new TranslatableComponent("k-turrets.protect.from.players"), new TranslatableComponent("k-turrets.not.protect.from.players"), turret.isProtectingFromPlayers(), p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new TogglePlayerProtection(!turret.isProtectingFromPlayers(), turret.getId()));
            turret.setProtectionFromPlayers(!turret.isProtectingFromPlayers());
            if (p_onPress_1_ instanceof SwitchButton)
                ((SwitchButton) p_onPress_1_).state = !((SwitchButton) p_onPress_1_).state;
        }));
        if (!turret.getOwner().isPresent())
            addWidget(new BetterButton(centerX, 120, new TranslatableComponent("k-turrets.claim.turret"), p_onPress_1_ -> {
                KTurrets.channel.sendToServer(new ClaimTurret(turret.getId(), minecraft.player.getUUID()));
                turret.setOwner(minecraft.player.getUUID());
                minecraft.player.closeContainer();
            }));

        addWidget(new Label(3, 3, new TranslatableComponent("k-turrets.targets")));
        targetButtons = new ArrayList<>(targets.size());
        for (int i = 0; i < targets.size(); i++) {
            EntityType<?> entityType = targets.get(i);
            SwitchButton switchButton = new SwitchButton(3, 20 * i + 40, new TextComponent(entityType.getRegistryName().toString()), new TextComponent(ChatFormatting.STRIKETHROUGH + entityType.getRegistryName().toString()), true, p_onPress_1_ -> {
                if (p_onPress_1_ instanceof SwitchButton) {
                    ((SwitchButton) p_onPress_1_).state = !((SwitchButton) p_onPress_1_).state;
                    tempStatusMap.put(entityType, ((SwitchButton) p_onPress_1_).state);
                }
            });
            switchButton.verticalScroll = true;
            addWidget(switchButton);
            targetButtons.add(switchButton);
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        tempStatusMap.forEach((entityType, aBoolean) -> {
            if (aBoolean)
                targets.add(entityType);
            else
                targets.remove(entityType);
        });
        CompoundTag compoundNBT = turret.encodeTargets(targets);
        turret.setTargets(compoundNBT);
        TurretTargets turretTargets = new TurretTargets(compoundNBT, turret.getId());
        KTurrets.channel.sendToServer(turretTargets);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float tick) {
        super.render(matrixStack, mouseX, mouseY, tick);
        renderComponentTooltip(matrixStack, Collections.singletonList(new TranslatableComponent("k-turrets.integrity").append(": " + (int) turret.getHealth() + "/" + turret.getMaxHealth())), centerX, centerY + 40, font);
        renderComponentTooltip(matrixStack, Arrays.asList(CHOOSE_HINT, SCROLL_HINT, INVENTORY_HINT), centerX, centerY + 60, font);
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
