package dev.buildtool.kturrets.firecharge;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.TEntities;
import dev.buildtool.kturrets.registers.TItems;
import dev.buildtool.satako.ItemHandler;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class FireChargeTurret extends Turret {
    protected ItemHandler ammo = new ItemHandler(27) {
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (stack.getItem() == Items.FIRE_CHARGE)
                return super.insertItem(slot, stack, simulate);
            return stack;
        }
    };

    public FireChargeTurret(World world) {
        super(TEntities.FIRE_CHARGE_TURRET, world);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(5, new RangedAttackGoal(this, 0, KTurrets.CHARGE_TURRET_RATE.get(), (float) getRange()));
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
                return !ammo.isEmpty() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return isArmed() && super.canContinueToUse();
            }
        });
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distFactor) {
        if (target.isAlive()) {
            for (ItemStack ammoItem : ammo.getItems()) {
                if (ammoItem.getItem() == Items.FIRE_CHARGE) {
                    ammoItem.shrink(1);
                    double d0 = target.getX() - this.getX();
                    double d1 = target.getEyeY() - getEyeY();
                    double d2 = target.getZ() - this.getZ();
                    SmallFireballEntity fireballEntity = new SmallFireballEntity(level, this, d0, d1, d2) {
                        @Override
                        protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
                            if (!this.level.isClientSide) {
                                Entity entity = p_213868_1_.getEntity();
                                if (!entity.fireImmune()) {
                                    Entity entity1 = this.getOwner();
                                    int i = entity.getRemainingFireTicks();
                                    entity.setSecondsOnFire(5);
                                    boolean flag = entity.hurt(DamageSource.fireball(this, entity1), KTurrets.CHARGE_TURRET_DAMAGE.get());
                                    if (!flag) {
                                        entity.setRemainingFireTicks(i);
                                    } else if (entity1 instanceof LivingEntity) {
                                        this.doEnchantDamageEffects((LivingEntity) entity1, entity);
                                    }
                                }
                            }
                        }
                    };
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
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeInt(getId());
        return new FireChargeTurretContainer(p_createMenu_1_, p_createMenu_2_, packetBuffer);
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity playerEntity, Hand p_230254_2_) {
        if (canUse(playerEntity) && playerEntity.isCrouching()) {
            if (playerEntity instanceof ServerPlayerEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) playerEntity, this, packetBuffer -> packetBuffer.writeInt(getId()));
            }
            return ActionResultType.SUCCESS;
        }
        return super.mobInteract(playerEntity, p_230254_2_);
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

    @Override
    protected List<ItemHandler> getContainedItems() {
        return Collections.singletonList(ammo);
    }

    @Override
    public Item getSpawnItem() {
        return TItems.FIRECHARGE_TURRET.get();
    }

    @Override
    public boolean isArmed() {
        return !ammo.isEmpty();
    }
}
