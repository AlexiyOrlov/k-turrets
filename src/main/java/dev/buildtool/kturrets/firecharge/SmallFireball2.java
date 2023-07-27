package dev.buildtool.kturrets.firecharge;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

class SmallFireball2 extends SmallFireball {
    private final Turret turret;

    public SmallFireball2(Turret shooter, double d0, double d1, double d2) {
        super(shooter.level(), shooter, d0, d1, d2);
        this.turret = shooter;
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        Entity owner = getOwner();
        if (turret != null && target.getType().getCategory().isFriendly() && Turret.decodeTargets(turret.getTargets()).contains(target.getType()))
            return super.canHitEntity(target);
        else if (owner == null || !owner.isAlliedTo(target) && !target.getType().getCategory().isFriendly()) {
            return super.canHitEntity(target);
        }
        return false;
    }

    @Override
    protected void onHitEntity(EntityHitResult p_213868_1_) {
        if (!this.level().isClientSide) {
            Entity entity = p_213868_1_.getEntity();
            if (!entity.fireImmune()) {
                Entity entity1 = this.getOwner();
                int i = entity.getRemainingFireTicks();
                entity.setSecondsOnFire(5);
                boolean flag = entity.hurt(damageSources().fireball(this, entity1), KTurrets.CHARGE_TURRET_DAMAGE.get());
                if (!flag) {
                    entity.setRemainingFireTicks(i);
                } else if (entity1 instanceof LivingEntity) {
                    this.doEnchantDamageEffects((LivingEntity) entity1, entity);
                }
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37384_) {
        super.onHitBlock(p_37384_);
        discard();
    }

    @Override
    public void tick() {
        super.tick();
        int movementMultiplier = 50;
        this.setDeltaMovement(getDeltaMovement().add(this.xPower * movementMultiplier, this.yPower * movementMultiplier, this.zPower * movementMultiplier));
    }
}
