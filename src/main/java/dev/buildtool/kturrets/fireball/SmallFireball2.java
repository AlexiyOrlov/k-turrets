package dev.buildtool.kturrets.fireball;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.KTDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

class SmallFireball2 extends SmallFireball {
    public SmallFireball2(Turret shooter, double d0, double d1, double d2) {
        super(shooter.level(), shooter, d0, d1, d2);
        xPower *= KTurrets.PROJECTILE_SPEED.get();
        yPower *= KTurrets.PROJECTILE_SPEED.get();
        zPower *= KTurrets.PROJECTILE_SPEED.get();
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
    protected void onHitEntity(EntityHitResult p_213868_1_) {
        if (!this.level().isClientSide) {
            Entity entity = p_213868_1_.getEntity();
            if (!entity.fireImmune()) {
                Entity turret = this.getOwner();
                int i = entity.getRemainingFireTicks();
                entity.setSecondsOnFire(5);
                DamageSource fireball = level().damageSources().source(KTDamageTypes.TURRET_FIREBALL, turret);
                boolean flag = entity.hurt(fireball, KTurrets.CHARGE_TURRET_DAMAGE.get());
                if (!flag) {
                    entity.setRemainingFireTicks(i);
                } else {
                    this.doEnchantDamageEffects((LivingEntity) turret, entity);
                }
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37384_) {
        super.onHitBlock(p_37384_);
        discard();
    }
}