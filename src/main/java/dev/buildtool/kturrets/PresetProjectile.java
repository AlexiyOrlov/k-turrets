package dev.buildtool.kturrets;

import dev.buildtool.kturrets.brick.Brick;
import dev.buildtool.kturrets.bullet.Bullet;
import dev.buildtool.kturrets.cobble.Cobblestone;
import dev.buildtool.kturrets.gauss.GaussBullet;
import dev.buildtool.kturrets.registers.KTDamageTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockRayTraceResult) {
        super.onHitBlock(blockRayTraceResult);
        discard();
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        Entity entity = getOwner();
        if (entity instanceof Turret owner) {
            if (target instanceof Player player) {
                if (owner.getOwner().isPresent() && player.getUUID().equals(owner.getOwner().get()))
                    return false;
                return !target.isAlliedTo(owner);
            }
            if (target instanceof Turret turret) {
                if (owner.getOwner().isPresent()) {
                    if (turret.getOwner().isPresent()) {
                        return !owner.getOwner().get().equals(turret.getOwner().get());
                    } else
                        return true;
                }
                return true;
            }
            if (target.getType().getCategory().isFriendly()) {
                return target == owner.getTarget();
            } else {
                return Turret.decodeTargets(owner.getTargets()).contains(target.getType());
            }
        }
        return false;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityRayTraceResult) {
        Entity entity = entityRayTraceResult.getEntity();
        Entity turret = getOwner();
        assert turret != null;
        DamageSource damageSource = level().damageSources().mobAttack((LivingEntity) turret);
        if (this instanceof Brick)
            damageSource = level().damageSources().source(KTDamageTypes.BRICK, turret);
        else if (this instanceof Bullet) {
            damageSource = level().damageSources().source(KTDamageTypes.BULLET, turret);
        } else if (this instanceof GaussBullet) {
            damageSource = level().damageSources().source(KTDamageTypes.GAUSS_BULLET, turret);
        } else if (this instanceof Cobblestone) {
            damageSource = level().damageSources().source(KTDamageTypes.COBBLESTONE, turret);
        }
        if (entity.hurt(damageSource, getDamage()))
            discard();
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
}
