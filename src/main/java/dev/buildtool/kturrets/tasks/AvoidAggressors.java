package dev.buildtool.kturrets.tasks;

import dev.buildtool.kturrets.Drone;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;
import java.util.Optional;

public class AvoidAggressors extends Goal {
    private final Drone drone;

    public AvoidAggressors(Drone drone) {
        this.drone = drone;
    }

    @Override
    public boolean canUse() {
        List<Mob> aggressors = drone.level.getEntitiesOfClass(Mob.class, drone.getBoundingBox().inflate(5), mob -> mob.getTarget() == drone);
        Optional<Mob> aggressor = aggressors.stream().filter(mob -> mob.distanceTo(drone) < 3).findAny();
        return aggressor.isPresent();
    }

    /**
     * Moves back
     */
    @Override
    public void tick() {
        drone.getMoveControl().strafe(5.0f, 0);
    }
}
