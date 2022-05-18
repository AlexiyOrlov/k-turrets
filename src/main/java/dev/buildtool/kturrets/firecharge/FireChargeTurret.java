package dev.buildtool.kturrets.firecharge;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.TEntities;
import dev.buildtool.satako.Functions;
import dev.buildtool.satako.ItemHandler;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

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

    public FireChargeTurret(Level world) {
        super(TEntities.FIRE_CHARGE_TURRET.get(), world);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(5, new RangedAttackGoal(this, 0, KTurrets.CHARGE_TURRET_RATE.get(), (float) getRange()));
        targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, LivingEntity.class, 0, true, true,
                livingEntity -> {
                    if (isProtectingFromPlayers() && livingEntity instanceof Player)
                        return alienPlayers.test((LivingEntity) livingEntity);
                    if (livingEntity instanceof LivingEntity entity) {
                        return !entity.fireImmune() && decodeTargets(getTargets()).contains(entity.getType());
                    }
                    return false;
                }) {
            @Override
            public boolean canUse() {
                return !ammo.isEmpty() && super.canUse();
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
                    SmallFireball fireballEntity = new SmallFireball2(d0, d1, d2);
                    fireballEntity.setPos(getX(), getEyeY(), getZ());
                    level.addFreshEntity(fireballEntity);
                    level.playSound(null, blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.NEUTRAL, 1, 1);
                    break;
                }
            }
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_createMenu_1_, Inventory p_createMenu_2_, Player p_createMenu_3_) {
        FriendlyByteBuf packetBuffer = Functions.emptyBuffer();
        packetBuffer.writeInt(getId());
        return new FireChargeTurretContainer(p_createMenu_1_, p_createMenu_2_, packetBuffer);
    }

    @Override
    protected InteractionResult mobInteract(Player playerEntity, InteractionHand p_230254_2_) {
        if (canUse(playerEntity) && !playerEntity.isShiftKeyDown()) {
            if (playerEntity instanceof ServerPlayer) {
                NetworkHooks.openGui((ServerPlayer) playerEntity, this, packetBuffer -> packetBuffer.writeInt(getId()));
            }
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(playerEntity, p_230254_2_);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.put("Ammo", ammo.serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        ammo.deserializeNBT(compoundNBT.getCompound("Ammo"));
    }

    @Override
    protected List<ItemHandler> getContainedItems() {
        return Collections.singletonList(ammo);
    }

    private class SmallFireball2 extends SmallFireball {
        public SmallFireball2(double d0, double d1, double d2) {
            super(FireChargeTurret.this.level, FireChargeTurret.this, d0, d1, d2);
        }

        @Override
        protected void onHitEntity(EntityHitResult p_213868_1_) {
            if (!this.level.isClientSide) {
                Entity entity = p_213868_1_.getEntity();
                if (!entity.fireImmune()) {
                    Entity entity1 = this.getOwner();
                    if (entity1 == null || !entity1.isAlliedTo(entity)) {
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
        }

        @Override
        public void tick() {
            super.tick();
            step();
        }

        /**
         * Copy of {@link AbstractHurtingProjectile#tick()}
         */
        protected void step() {
            Entity entity = this.getOwner();
            if (this.level.isClientSide || (entity == null || !entity.isRemoved()) && this.level.hasChunkAt(this.blockPosition())) {
                super.tick();
                if (this.shouldBurn()) {
                    this.setSecondsOnFire(1);
                }

                HitResult raytraceresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
                if (raytraceresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                    this.onHit(raytraceresult);
                }

                this.checkInsideBlocks();
                Vec3 vector3d = this.getDeltaMovement();
                double d0 = this.getX() + vector3d.x;
                double d1 = this.getY() + vector3d.y;
                double d2 = this.getZ() + vector3d.z;
                ProjectileUtil.rotateTowardsMovement(this, 0.2F);
                float f = this.getInertia();
                if (this.isInWater()) {
                    for (int i = 0; i < 4; ++i) {
                        this.level.addParticle(ParticleTypes.BUBBLE, d0 - vector3d.x * 0.25D, d1 - vector3d.y * 0.25D, d2 - vector3d.z * 0.25D, vector3d.x, vector3d.y, vector3d.z);
                    }

                    f = 0.8F;
                }

                this.setDeltaMovement(vector3d.add(this.xPower, this.yPower, this.zPower).scale(f));
                this.level.addParticle(this.getTrailParticle(), d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
                this.setPos(d0, d1, d2);
            } else {
                this.discard();
            }
        }
    }
}
