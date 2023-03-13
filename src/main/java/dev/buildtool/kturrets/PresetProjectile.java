package dev.buildtool.kturrets;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public abstract class PresetProjectile extends AbstractHurtingProjectile {
    protected static final EntityDataAccessor<Integer> DAMAGE = SynchedEntityData.defineId(PresetProjectile.class, EntityDataSerializers.INT);
    protected Turret turret;
    protected int movementMultiplier = 3;

    public PresetProjectile(EntityType<? extends AbstractHurtingProjectile> p_i50173_1_, net.minecraft.world.level.Level p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);
    }

    public PresetProjectile(EntityType<? extends AbstractHurtingProjectile> p_i50175_1_, Turret shooter, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, net.minecraft.world.level.Level world) {
        super(p_i50175_1_, shooter, p_i50175_3_, p_i50175_5_, p_i50175_7_, world);
        setPos(shooter.getX(), shooter.getEyeY(), shooter.getZ());
        turret = shooter;
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public float getInertia() {
        return 1;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockRayTraceResult) {
        super.onHitBlock(blockRayTraceResult);
        discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult entityRayTraceResult) {
        Entity target = entityRayTraceResult.getEntity();
        Entity owner = getOwner();
        if (turret != null && target.getType().getCategory().isFriendly() && turret.decodeTargets(turret.getTargets()).contains(target.getType())) {
            target.hurt(getDamageSource(), getDamage());
            discard();
        } else if (owner == null || !owner.isAlliedTo(target) && !target.getType().getCategory().isFriendly()) {
            target.hurt(getDamageSource(), getDamage());
            discard();
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DAMAGE, 0);
    }

    public void setDamage(int damage) {
        entityData.set(DAMAGE, damage);
    }

    public int getDamage() {
        return entityData.get(DAMAGE);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putInt("Damage", getDamage());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        setDamage(compoundNBT.getInt("Damage"));
    }

    /**
     * @return preferably {@link net.minecraft.world.damagesource.IndirectEntityDamageSource}
     */
    protected abstract DamageSource getDamageSource();

    @Override
    public void tick() {
        Entity entity = this.getOwner();
        if (this.level.isClientSide || (entity == null || !entity.isRemoved()) && this.level.hasChunkAt(this.blockPosition())) {
            super.tick();
            if (this.shouldBurn()) {
                this.setSecondsOnFire(1);
            }

            HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;
            ProjectileUtil.rotateTowardsMovement(this, 0.2F);
            float f = this.getInertia();
            if (this.isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    float f1 = 0.25F;
                    this.level.addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * f1, d1 - vec3.y * f1, d2 - vec3.z * f1, vec3.x, vec3.y, vec3.z);
                }

                f = 0.8F;
            }
            this.setDeltaMovement(vec3.add(this.xPower * movementMultiplier, this.yPower * movementMultiplier, this.zPower * movementMultiplier).scale(f));
            this.setPos(d0, d1, d2);
        } else {
            this.discard();
        }
    }
}
