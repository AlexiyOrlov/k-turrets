package dev.buildtool.kturrets;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.goal.Goal;

public class MoveOutOfLava extends Goal {
    private final Drone drone;

    public MoveOutOfLava(Drone drone) {
        this.drone = drone;
    }

    @Override
    public boolean canUse() {
        return drone.isInLava();
    }

    @Override
    public void start() {
        BlockPos dronePos = drone.getOnPos();
        int counter = 1;
        loop:
        do {

            for (Direction direction : Direction.values()) {
                BlockPos nextCheck = dronePos.relative(direction, counter);
                if (drone.level.getBlockState(nextCheck).isAir()) {
                    drone.moveTo(nextCheck.relative(direction), 1, 1);
                    break loop;
                }
            }
            counter++;
        }
        while (counter < 60);
    }
}
