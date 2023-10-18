package dev.buildtool.kturrets.fireball;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.KTDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
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
        xPower *= KTurrets.PROJECTILE_SPEED.get();
        yPower *= KTurrets.PROJECTILE_SPEED.get();
        zPower *= KTurrets.PROJECTILE_SPEED.get();
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        Entity owner = getOwner();
        if (turret != null && target.getType().getCategory().isFriendly() && (Turret.decodeTargets(turret.getTargets()).contains(target.getType()) || turret.getTarget() == target))
            return super.canHitEntity(target);
        else if (owner == null || !owner.isAlliedTo(target) && !target.getType().getCategory().isFriendly()) {
            return super.canHitEntity(target);
        } else
            return turret == null || Turret.decodeTargets(turret.getTargets()).contains(target.getType()) || !target.getType().getCategory().isFriendly();
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
