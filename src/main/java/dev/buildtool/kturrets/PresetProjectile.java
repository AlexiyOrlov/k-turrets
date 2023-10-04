package dev.buildtool.kturrets;

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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

public abstract class PresetProjectile extends AbstractHurtingProjectile {
    protected static final EntityDataAccessor<Integer> DAMAGE = SynchedEntityData.defineId(PresetProjectile.class, EntityDataSerializers.INT);
    protected Turret turret;
    protected static double MOVEMENT_MULTIPLIER = KTurrets.PROJECTILE_SPEED.get();

    public PresetProjectile(EntityType<? extends AbstractHurtingProjectile> p_i50173_1_, net.minecraft.world.level.Level p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);
    }

    public PresetProjectile(EntityType<? extends AbstractHurtingProjectile> p_i50175_1_, Turret shooter, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, net.minecraft.world.level.Level world) {
        super(p_i50175_1_, shooter, p_i50175_3_, p_i50175_5_, p_i50175_7_, world);
        setPos(shooter.getX(), shooter.getEyeY(), shooter.getZ());
        turret = shooter;
        xPower *= MOVEMENT_MULTIPLIER;
        yPower *= MOVEMENT_MULTIPLIER;
        zPower *= MOVEMENT_MULTIPLIER;
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
    protected void onHitEntity(EntityHitResult entityRayTraceResult) {
        Entity entity = entityRayTraceResult.getEntity();
        entity.hurt(getDamageSource(), getDamage());
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
}
