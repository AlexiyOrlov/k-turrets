package dev.buildtool.kturrets;

import dev.buildtool.satako.ItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Extends Mob entity because of goals
 */
public abstract class Turret extends MobEntity implements IRangedAttackMob, INamedContainerProvider {
    public static DataParameter<CompoundNBT> TARGETS = EntityDataManager.defineId(Turret.class, DataSerializers.COMPOUND_TAG);

    public Turret(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    public static AttributeModifierMap.MutableAttribute createDefaultAttributes() {
        return createLivingAttributes().add(Attributes.FOLLOW_RANGE, 32).add(Attributes.MOVEMENT_SPEED, 0).add(Attributes.MAX_HEALTH, 60).add(Attributes.ATTACK_DAMAGE, 4);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        CompoundNBT compoundNBT = new CompoundNBT();
        List<EntityType<?>> targets = ForgeRegistries.ENTITIES.getValues().stream().filter(entityType1 -> !entityType1.getCategory().isFriendly()).collect(Collectors.toList());
        for (int i = 0; i < targets.size(); i++) {
            compoundNBT.putString("Target#" + i, targets.get(i).getRegistryName().toString());
        }
        compoundNBT.putInt("Count", targets.size());
        entityData.define(TARGETS, compoundNBT);
    }

    public void setTargets(CompoundNBT compoundNBT) {
        entityData.set(TARGETS, compoundNBT);
    }

    public CompoundNBT getTargets() {
        return entityData.get(TARGETS);
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
            openTargetScreen();
        }
        return ActionResultType.SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    private void openTargetScreen() {
        Minecraft.getInstance().setScreen(new TargetOptionScreen(this));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.put("Targets", getTargets());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        setTargets(compoundNBT.getCompound("Targets"));
    }

    public List<EntityType<?>> decodeTargets(CompoundNBT compoundNBT) {
        int count = compoundNBT.getInt("Count");
        List<EntityType<?>> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String next = compoundNBT.getString("Target#" + i);
            list.add(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(next)));
        }
        return list;
    }

    public CompoundNBT encodeTargets(List<EntityType<?>> list) {
        CompoundNBT compoundNBT = new CompoundNBT();
        for (int i = 0; i < list.size(); i++) {
            EntityType<?> entityType = list.get(i);
            compoundNBT.putString("Target#" + i, entityType.getRegistryName().toString());
        }
        compoundNBT.putInt("Count", list.size());
        return compoundNBT;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        getContainedItems().forEach(itemHandler -> InventoryHelper.dropContents(level, blockPosition(), itemHandler.getItems()));
    }

    protected abstract List<ItemHandler> getContainedItems();

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.SHIELD_BLOCK;
    }

    @Override
    public boolean canBeAffected(EffectInstance effectInstance) {
        Effect effect = effectInstance.getEffect();
        if (effect == Effects.POISON || effect == Effects.HEAL || effect == Effects.HEALTH_BOOST || effect == Effects.REGENERATION || effect == Effects.WITHER)
            return false;
        return super.canBeAffected(effectInstance);
    }
}
