package dev.buildtool.kturrets.tasks;

import dev.buildtool.kturrets.Drone;
import dev.buildtool.kturrets.KTurrets;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;

public class RestrictedRangedAttackGoal extends RangedAttackGoal {
    private Drone drone;

    public RestrictedRangedAttackGoal(RangedAttackMob p_25768_, double p_25769_, int p_25770_, float p_25771_) {
        super(p_25768_, p_25769_, p_25770_, p_25771_);
        drone = (Drone) p_25768_;
    }

    @Override
    public void tick() {
        Player owner = drone.level().getPlayerByUUID(drone.getOwnerUUID());
        if (owner == null)
            work();
        else {
            float distanceToPlayer = drone.distanceTo(owner);
            if (distanceToPlayer < KTurrets.OWNER_FOLLOW_DISTANCE.get())
                work();
            else if (drone.getTarget() != null)
                drone.setTarget(null);
        }
    }

    private void work() {
        double d0 = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
        boolean flag = this.mob.getSensing().hasLineOfSight(this.target);
        if (flag) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }

        if (!(d0 > (double) this.attackRadiusSqr) && this.seeTime >= 5) {
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().moveTo(this.target.getX(), target.getY() + 3, target.getZ(), 1);
        }

        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
        if (--this.attackTime == 0) {
            if (!flag) {
                return;
            }

            float f = (float) Math.sqrt(d0) / this.attackRadius;
            float f1 = Mth.clamp(f, 0.1F, 1.0F);
            this.rangedAttackMob.performRangedAttack(this.target, f1);
            this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
        } else if (this.attackTime < 0) {
            this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
        }
    }
}
