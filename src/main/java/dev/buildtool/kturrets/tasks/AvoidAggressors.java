package dev.buildtool.kturrets.tasks;

import dev.buildtool.kturrets.Drone;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.List;
import java.util.Optional;

public class AvoidAggressors extends Goal {
    private final Drone drone;

    public AvoidAggressors(Drone drone) {
        this.drone = drone;
    }

    @Override
    public boolean canUse() {
        List<MobEntity> aggressors = drone.level.getEntitiesOfClass(MobEntity.class, drone.getBoundingBox().inflate(5), mob -> mob.getTarget() == drone);
        Optional<MobEntity> aggressor = aggressors.stream().filter(mob -> mob.distanceTo(drone) < 3).findAny();
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
