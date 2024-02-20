package dev.buildtool.kturrets.tasks;

import dev.buildtool.kturrets.Turret;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class AttackTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {
    private final Turret turret;

    public AttackTargetGoal(Turret turret) {
        super(turret, LivingEntity.class, 0, true, true, livingEntity -> {
            if (livingEntity instanceof Player) {
                if (turret.isProtectingFromPlayers())
                    return turret.alienPlayers.test(livingEntity);
                else return false;
            }
            return Turret.decodeTargets(turret.getTargets()).contains(livingEntity.getType());
        });
        this.turret = turret;
    }

    @Override
    public boolean canUse() {
        return turret.isArmed() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return turret.isArmed() && super.canContinueToUse();
    }

    @Override
    protected AABB getTargetSearchArea(double p_26069_) {
        return mob.getBoundingBox().inflate(p_26069_, p_26069_, p_26069_);
    }
}
