package dev.buildtool.kturrets.tasks;

import dev.buildtool.kturrets.Drone;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

public class GuardArea extends Goal {
    private final Drone drone;

    public GuardArea(Drone drone) {
        this.drone = drone;
    }

    @Override
    public boolean canUse() {
        return drone.isGuardingArea();
    }

    @Override
    public void start() {
        drone.setGuardPosition(drone.blockPosition());
    }

    @Override
    public void tick() {
        if (drone.getTarget() == null) {
            BlockPos guardPos = drone.getGuardPosition();
            if (drone.distanceToSqr(guardPos.getX() + 0.5, guardPos.getY(), guardPos.getZ() + 0.5) > 16)
                drone.getNavigation().moveTo(guardPos.getX() + 0.5, guardPos.getY(), guardPos.getZ() + 0.5, 1);
        }
    }
}
