package dev.buildtool.kturrets.tasks;

import dev.buildtool.kturrets.Drone;
import net.minecraft.entity.ai.goal.Goal;

public class Strafe extends Goal {
    private final Drone drone;
    private int timer;

    public Strafe(Drone drone) {
        this.drone = drone;
    }

    @Override
    public boolean canUse() {
        return drone.getTarget() != null && drone.getTarget().isAlive();
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
        if (drone.getY() <= drone.getTarget().getY() + 1)
            drone.getMoveControl().setWantedPosition(drone.getX(), drone.getY() + 1, drone.getZ(), 1);
    }

    @Override
    public void stop() {
        timer = 0;
    }
}
