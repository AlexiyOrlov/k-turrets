package dev.buildtool.kturrets.firecharge;

import dev.buildtool.kturrets.IndirectDamageSource;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.phys.EntityHitResult;

class SmallFireball2 extends SmallFireball {
    private final Turret turret;
    static double MOVEMENT_MULTIPLIER = KTurrets.PROJECTILE_SPEED.get();

    public SmallFireball2(Turret shooter, double d0, double d1, double d2) {
        super(shooter.level, shooter, d0, d1, d2);
        turret = shooter;
        xPower *= MOVEMENT_MULTIPLIER;
        yPower *= MOVEMENT_MULTIPLIER;
        zPower *= MOVEMENT_MULTIPLIER;
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
                    boolean flag = entity.hurt(new IndirectDamageSource("k_turrets.fireball", entity, entity1), KTurrets.CHARGE_TURRET_DAMAGE.get());
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
}
