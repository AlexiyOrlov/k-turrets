package dev.buildtool.kturrets.tasks;

import dev.buildtool.kturrets.Drone;
import net.minecraft.world.entity.ai.goal.Goal;

public class StrafeAroundTarget extends Goal {
    private final Drone drone;
    private int timer;

    public StrafeAroundTarget(Drone drone) {
        this.drone = drone;
    }

    @Override
    public boolean canUse() {
        return drone.getTarget() != null && drone.getTarget().isAlive() && drone.hasLineOfSight(drone.getTarget());
    }

    @Override
    public void tick() {
        if (timer == 0) {
            timer = drone.level.random.nextBoolean() ? 60 : -60;
        } else if (timer < 0)
            timer++;
        else
            timer--;
        drone.getMoveControl().strafe(0, Math.signum(timer) * 0.1f);
        drone.getLookControl().setLookAt(drone.getTarget(), 90, 90);
    }

    @Override
    public void stop() {
        timer = 0;
    }
}
