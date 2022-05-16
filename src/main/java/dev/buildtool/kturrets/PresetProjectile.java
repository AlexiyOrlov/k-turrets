package dev.buildtool.kturrets;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public abstract class PresetProjectile extends AbstractHurtingProjectile {
    protected static final EntityDataAccessor<Integer> DAMAGE = SynchedEntityData.defineId(PresetProjectile.class, EntityDataSerializers.INT);

    public PresetProjectile(EntityType<? extends AbstractHurtingProjectile> p_i50173_1_, net.minecraft.world.level.Level p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);
    }

    public PresetProjectile(EntityType<? extends AbstractHurtingProjectile> p_i50174_1_, double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, Level world) {
        super(p_i50174_1_, p_i50174_2_, p_i50174_4_, p_i50174_6_, p_i50174_8_, p_i50174_10_, p_i50174_12_, world);
    }

    public PresetProjectile(EntityType<? extends AbstractHurtingProjectile> p_i50175_1_, LivingEntity shooter, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, net.minecraft.world.level.Level world) {
        super(p_i50175_1_, shooter, p_i50175_3_, p_i50175_5_, p_i50175_7_, world);
        setPos(shooter.getX(), shooter.getEyeY(), shooter.getZ());
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
    public Packet<?> getAddEntityPacket() {
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
        if (owner == null || !owner.isAlliedTo(target)) {
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

            this.setDeltaMovement(vector3d.add(this.xPower, this.yPower, this.zPower).scale((double) f));
            this.level.addParticle(this.getTrailParticle(), d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
            this.setPos(d0, d1, d2);
        } else {
            this.discard();
        }
    }
}
