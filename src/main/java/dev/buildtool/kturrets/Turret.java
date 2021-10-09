package dev.buildtool.kturrets;

import dev.buildtool.satako.UniqueList;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Extends Mob entity because of goals
 */
public abstract class Turret extends MobEntity implements IRangedAttackMob, INamedContainerProvider {
    protected UniqueList<EntityType<?>> targets;
    protected boolean immobile = true;

    public Turret(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        targets = new UniqueList<>(ForgeRegistries.ENTITIES.getValues().stream().filter(entityType1 -> !entityType1.getCategory().isFriendly()).collect(Collectors.toList()));
    }

    public static AttributeModifierMap.MutableAttribute createDefaultAttributes() {
        return createLivingAttributes().add(Attributes.FOLLOW_RANGE, 32).add(Attributes.MOVEMENT_SPEED, 0).add(Attributes.MAX_HEALTH, 60).add(Attributes.ATTACK_DAMAGE, 4);
    }

    @Override
    protected abstract void registerGoals();

    @Override
    public boolean attackable() {
        return false;
    }

    /**
     * By player
     */
    @Override
    public boolean isAttackable() {
        return true;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlotType p_184582_1_) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlotType p_184201_1_, ItemStack p_184201_2_) {

    }

    @Override
    public HandSide getMainArm() {
        return HandSide.RIGHT;
    }

    protected double getRange() {
        return getAttributeValue(Attributes.FOLLOW_RANGE);
    }

    protected double getDamage() {
        return getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity playerEntity, Hand p_230254_2_) {
        if (level.isClientSide) {
            openTargetScreen(playerEntity);
        }
        return ActionResultType.SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    private void openTargetScreen(PlayerEntity playerEntity) {
        Minecraft.getInstance().setScreen(new TargetOptionScreen(this));
    }

    @Override
    protected boolean isImmobile() {
        return isAlive() ? immobile : super.isImmobile();
    }

    public void setImmobile(boolean immobile) {
        this.immobile = immobile;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putBoolean("Immobile", immobile);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        immobile = compoundNBT.getBoolean("Immobile");
    }
}
