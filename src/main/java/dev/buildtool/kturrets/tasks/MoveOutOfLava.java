package dev.buildtool.kturrets.tasks;

import dev.buildtool.kturrets.Drone;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.goal.Goal;
import org.apache.commons.lang3.ArrayUtils;

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

            for (Direction direction : ArrayUtils.removeElement(Direction.values(), Direction.DOWN)) {
                BlockPos nextCheck = dronePos.relative(direction, counter);
                if (drone.level.getBlockState(nextCheck).isAir()) {
                    drone.moveTo(nextCheck, 1, 1);
                    break loop;
                }
            }
            counter++;
        }
        while (counter < 60);
    }
}
