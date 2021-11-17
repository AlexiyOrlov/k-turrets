package dev.buildtool.kturrets;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.buildtool.kturrets.packets.*;
import dev.buildtool.satako.UniqueList;
import dev.buildtool.satako.gui.*;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.stream.Collectors;

public class TurretOptionsScreen extends Screen2 {
    protected Turret turret;
    protected HashMap<EntityType<?>, Boolean> tempStatusMap;
    protected List<EntityType<?>> targets;
    private static final TranslationTextComponent CHOOSE_HINT = new TranslationTextComponent("k-turrets.choose.tooltip");
    private static final TranslationTextComponent SCROLL_HINT = new TranslationTextComponent("k-turrets.hold.alt.to.scroll");
    private static final TranslationTextComponent INVENTORY_HINT = new TranslationTextComponent("k-turrets.inventory.hint");
    private List<SwitchButton> targetButtons;
    private TextField addEntityField;

    public TurretOptionsScreen(Turret turret) {
        super(new TranslationTextComponent("k-turrets.targets"));
        this.turret = turret;
        tempStatusMap = new HashMap<>(40);
        targets = new UniqueList<>(turret.decodeTargets(turret.getTargets()));
        targets.forEach(entityType -> tempStatusMap.put(entityType, true));
    }

    @Override
    public void init() {
        super.init();
        addEntityField = addButton(new TextField(centerX, 3, 100));
        addButton(new BetterButton(centerX, 20, new TranslationTextComponent("k-turrets.add.entity.type"), p_onPress_1_ -> {
            String entityType = addEntityField.getValue();
            EntityType<?> type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entityType));
            if (type != null) {
                if (type == EntityType.PIG && !entityType.equals("minecraft:pig") && !entityType.equals("pig")) {
                    minecraft.player.sendMessage(new TranslationTextComponent("k-turrets.incorrect.entry"), Util.NIL_UUID);
                } else {
                    targets.add(type);
                    tempStatusMap.put(type, true);
                    minecraft.player.sendMessage(new TranslationTextComponent("k-turrets.added").append(" ").append(type.getDescription()), Util.NIL_UUID);
                    addEntityField.setValue(entityType.substring(0, entityType.indexOf(':')));
                }
            }
        }));
        addButton(new BetterButton(centerX, 40, new TranslationTextComponent("k-turrets.dismantle"), p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new DismantleTurret(turret.getId()));
            minecraft.player.closeContainer();
        }));
        addButton(new BetterButton(centerX, 60, new TranslationTextComponent("k-turrets.clear.list"), p_onPress_1_ -> {
            targets.clear();
            tempStatusMap.clear();
            targetButtons.forEach(buttons::remove);
        }));
        addButton(new SwitchButton(centerX, 80, new TranslationTextComponent("k-turrets.mobile"), new TranslationTextComponent("k-turrets.immobile"), turret.isMoveable(), p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new ToggleMobility(!turret.isMoveable(), turret.getId()));
            turret.setMoveable(!turret.isMoveable());
            if (p_onPress_1_ instanceof SwitchButton) {
                ((SwitchButton) p_onPress_1_).state = !((SwitchButton) p_onPress_1_).state;
            }
        }));
        addButton(new SwitchButton(centerX, 100, new TranslationTextComponent("k-turrets.protect.from.players"), new TranslationTextComponent("k-turrets.not.protect.from.players"), turret.isProtectingFromPlayers(), p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new TogglePlayerProtection(!turret.isProtectingFromPlayers(), turret.getId()));
            turret.setProtectionFromPlayers(!turret.isProtectingFromPlayers());
            if (p_onPress_1_ instanceof SwitchButton)
                ((SwitchButton) p_onPress_1_).state = !((SwitchButton) p_onPress_1_).state;
        }));
        if (!turret.getOwner().isPresent())
            addButton(new BetterButton(centerX, 120, new TranslationTextComponent("k-turrets.claim.turret"), p_onPress_1_ -> {
                KTurrets.channel.sendToServer(new ClaimTurret(turret.getId(), minecraft.player.getUUID()));
                turret.setOwner(minecraft.player.getUUID());
                minecraft.player.closeContainer();
            }));

        addButton(new Label(3, 3, new TranslationTextComponent("k-turrets.targets")));
        targetButtons = new ArrayList<>(targets.size());
        for (int i = 0; i < targets.size(); i++) {
            EntityType<?> entityType = targets.get(i);
            SwitchButton switchButton = new SwitchButton(3, 20 * i + 40, new StringTextComponent(entityType.getRegistryName().toString()), new StringTextComponent(TextFormatting.STRIKETHROUGH + entityType.getRegistryName().toString()), true, p_onPress_1_ -> {
                if (p_onPress_1_ instanceof SwitchButton) {
                    ((SwitchButton) p_onPress_1_).state = !((SwitchButton) p_onPress_1_).state;
                    tempStatusMap.put(entityType, ((SwitchButton) p_onPress_1_).state);
                }
            });
            switchButton.verticalScroll = true;
            addButton(switchButton);
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
        CompoundNBT compoundNBT = turret.encodeTargets(targets);
        turret.setTargets(compoundNBT);
        TurretTargets turretTargets = new TurretTargets(compoundNBT, turret.getId());
        KTurrets.channel.sendToServer(turretTargets);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float tick) {
        super.render(matrixStack, mouseX, mouseY, tick);
        renderWrappedToolTip(matrixStack, Collections.singletonList(new TranslationTextComponent("k-turrets.integrity").append(": " + (int) turret.getHealth() + "/" + turret.getMaxHealth())), centerX, centerY + 40, font);
        renderWrappedToolTip(matrixStack, Arrays.asList(CHOOSE_HINT, SCROLL_HINT, INVENTORY_HINT), centerX, centerY + 60, font);
        String targetEntry = addEntityField.getValue();
        if (targetEntry.contains(":") && targetEntry.indexOf(":") + 1 < targetEntry.length()) {
            List<ResourceLocation> entityTypes = ForgeRegistries.ENTITIES.getKeys().stream().filter(resourceLocation -> resourceLocation.toString().contains(targetEntry)).collect(Collectors.toList());
            if (!entityTypes.isEmpty()) {
                renderComponentTooltip(matrixStack, entityTypes.subList(0, Math.min(entityTypes.size(), 12)).stream().map(resourceLocation -> new StringTextComponent(TextFormatting.YELLOW + resourceLocation.toString())).collect(Collectors.toList()), addEntityField.x, addEntityField.y + addEntityField.getHeight() + 20);
            }
        }
    }
}
