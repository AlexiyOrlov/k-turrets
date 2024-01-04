package dev.buildtool.kturrets.fireball;

import dev.buildtool.kturrets.IndirectDamageSource;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;

public class SmallFireball extends SmallFireballEntity {
    public static final double MOVEMENT_MULTIPLIER = KTurrets.PROJECTILE_SPEED.get();
    private final Turret turret;

    public SmallFireball(Turret shooter, double d0, double d1, double d2) {
        super(shooter.level, shooter, d0, d1, d2);
        this.turret = shooter;
        xPower *= MOVEMENT_MULTIPLIER;
        yPower *= MOVEMENT_MULTIPLIER;
        zPower *= MOVEMENT_MULTIPLIER;
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        Entity entity = getOwner();
        if (entity instanceof Turret) {
            Turret owner = (Turret) entity;
            if (target instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) target;
                if (owner.getOwner().isPresent() && player.getUUID().equals(owner.getOwner().get()))
                    return false;
                return !target.isAlliedTo(owner);
            }
            if (target instanceof Turret) {
                Turret turret = (Turret) target;
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
    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        if (!this.level.isClientSide) {
            Entity entity = p_213868_1_.getEntity();
            if (!entity.fireImmune()) {
                Entity entity1 = this.getOwner();
                int i = entity.getRemainingFireTicks();
                entity.setSecondsOnFire(5);
                boolean flag = entity.hurt(new IndirectDamageSource("k_turrets.fireball", entity, entity1), KTurrets.FIREBALL_TURRET_DAMAGE.get());
                if (!flag) {
                    entity.setRemainingFireTicks(i);
                } else {
                    this.doEnchantDamageEffects((LivingEntity) entity1, entity);
                }
            }
        }
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult p_37384_) {
        super.onHitBlock(p_37384_);
        remove();
    }
}
