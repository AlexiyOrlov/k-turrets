package dev.buildtool.kturrets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class PresetProjectile extends DamagingProjectileEntity {
    private Turret turret;
    protected static double MOVEMENT_MULTIPLIER = KTurrets.PROJECTILE_SPEED.get();
    protected static final DataParameter<Integer> DAMAGE = EntityDataManager.defineId(PresetProjectile.class, DataSerializers.INT);

    public PresetProjectile(EntityType<? extends DamagingProjectileEntity> p_i50173_1_, World p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);
    }

    public PresetProjectile(EntityType<? extends DamagingProjectileEntity> p_i50174_1_, double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, World world) {
        super(p_i50174_1_, p_i50174_2_, p_i50174_4_, p_i50174_6_, p_i50174_8_, p_i50174_10_, p_i50174_12_, world);
    }

    public PresetProjectile(EntityType<? extends DamagingProjectileEntity> p_i50175_1_, LivingEntity shooter, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, World world) {
        super(p_i50175_1_, shooter, p_i50175_3_, p_i50175_5_, p_i50175_7_, world);
        setPos(shooter.getX(), shooter.getEyeY(), shooter.getZ());
        turret = (Turret) shooter;
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    protected float getInertia() {
        return 1;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult blockRayTraceResult) {
        super.onHitBlock(blockRayTraceResult);
        remove();
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult entityRayTraceResult) {
        Entity entity = entityRayTraceResult.getEntity();
        Entity owner = getOwner();
        if (owner == null || !owner.isAlliedTo(entity)) {
            entity.hurt(getDamageSource(), getDamage());
            remove();
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
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putInt("Damage", getDamage());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        setDamage(compoundNBT.getInt("Damage"));
    }

    /**
     * @return preferably {@link IndirectDamageSource}
     */
    protected abstract DamageSource getDamageSource();

    /**
     * Copy of {@link DamagingProjectileEntity#tick()}
     */
    protected void step() {
        Entity entity = this.getOwner();
        if (this.level.isClientSide || (entity == null || !entity.removed) && this.level.hasChunkAt(this.blockPosition())) {
            super.tick();
            if (this.shouldBurn()) {
                this.setSecondsOnFire(1);
            }

            RayTraceResult raytraceresult = ProjectileHelper.getHitResult(this, this::canHitEntity);
            if (raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                this.onHit(raytraceresult);
            }

            this.checkInsideBlocks();
            Vector3d vector3d = this.getDeltaMovement();
            double d0 = this.getX() + vector3d.x;
            double d1 = this.getY() + vector3d.y;
            double d2 = this.getZ() + vector3d.z;
            ProjectileHelper.rotateTowardsMovement(this, 0.2F);
            float f = this.getInertia();
            if (this.isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    float f1 = 0.25F;
                    this.level.addParticle(ParticleTypes.BUBBLE, d0 - vector3d.x * 0.25D, d1 - vector3d.y * 0.25D, d2 - vector3d.z * 0.25D, vector3d.x, vector3d.y, vector3d.z);
                }

                f = 0.8F;
            }

            this.setDeltaMovement(vector3d.add(this.xPower, this.yPower, this.zPower).scale((double) f));
            this.level.addParticle(this.getTrailParticle(), d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
            this.setPos(d0, d1, d2);
        } else {
            this.remove();
        }
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        Entity owner = getOwner();
        if (turret != null && target.getType().getCategory().isFriendly() && Turret.decodeTargets(turret.getTargets()).contains(target.getType()))
            return super.canHitEntity(target);
        else if (owner == null || !owner.isAlliedTo(target) && !target.getType().getCategory().isFriendly()) {
            return super.canHitEntity(target);
        } else
            return turret == null || Turret.decodeTargets(turret.getTargets()).contains(target.getType()) || !target.getType().getCategory().isFriendly();
    }

    @Override
    public void tick() {
        super.tick();
        setDeltaMovement(getDeltaMovement().add(xPower * MOVEMENT_MULTIPLIER, yPower * MOVEMENT_MULTIPLIER, zPower * MOVEMENT_MULTIPLIER));
    }
}
