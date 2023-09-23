package dev.buildtool.kturrets.firecharge;

import dev.buildtool.kturrets.Drone;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.registers.KEntities;
import dev.buildtool.kturrets.registers.KItems;
import dev.buildtool.satako.Functions;
import dev.buildtool.satako.ItemHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class FireballDrone extends Drone {
    protected ItemHandler ammo = new ItemHandler(18) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.getItem() == KItems.EXPLOSIVE_POWDER.get();
        }
    };

    public FireballDrone(World world) {
        super(KEntities.FIRECHARGE_DRONE, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(5, new RangedAttackGoal(this, 1, KTurrets.FIREBALL_TURRET_RATE.get(), (float) getRange()));
        targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, LivingEntity.class, 0, true, true,
                livingEntity -> {
                    if (isProtectingFromPlayers() && livingEntity instanceof PlayerEntity)
                        return alienPlayers.test((LivingEntity) livingEntity);
                    if (livingEntity instanceof LivingEntity) {
                        LivingEntity entity = (LivingEntity) livingEntity;
                        return !entity.fireImmune() && decodeTargets(getTargets()).contains(entity.getType());
                    }
                    return false;
                }) {
            @Override
            public boolean canUse() {
                return ((!isFollowingOwner() && isGuardingArea()) || isFollowingOwner()) && isArmed() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return ((!isFollowingOwner() && isGuardingArea()) || isFollowingOwner()) && isArmed() && super.canContinueToUse();
            }
        });
    }

    @Override
    protected List<ItemHandler> getContainedItems() {
        return Collections.singletonList(ammo);
    }

    @Override
    public boolean isArmed() {
        return !ammo.isEmpty();
    }

    @Override
    public void performRangedAttack(LivingEntity target, float p_33318_) {
        if (target.isAlive()) {
            for (ItemStack ammoItem : ammo.getItems()) {
                if (!ammoItem.isEmpty()) {
                    ammoItem.shrink(1);
                    double d0 = target.getX() - this.getX();
                    double d1 = target.getEyeY() - getEyeY();
                    double d2 = target.getZ() - this.getZ();
                    SmallFireball fireballEntity = new SmallFireball(this, d0, d1, d2);
                    fireballEntity.setPos(getX(), getEyeY(), getZ());
                    level.addFreshEntity(fireballEntity);
                    level.playSound(null, blockPosition(), SoundEvents.FIRECHARGE_USE, SoundCategory.NEUTRAL, 1, 1);
                    break;
                }
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int p_39954_, PlayerInventory inventory, PlayerEntity p_39956_) {
        PacketBuffer friendlyByteBuf = Functions.emptyBuffer();
        friendlyByteBuf.writeInt(getId());
        return new FireballDroneContainer(p_39954_, inventory, friendlyByteBuf);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.put("Ammo", ammo.serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        ammo.deserializeNBT(compoundNBT.getCompound("Ammo"));
    }
}
