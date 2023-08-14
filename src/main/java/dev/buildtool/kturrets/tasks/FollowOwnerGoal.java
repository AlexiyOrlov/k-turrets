package dev.buildtool.kturrets.tasks;

import dev.buildtool.kturrets.Drone;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class FollowOwnerGoal extends Goal {
    protected Drone drone;
    protected World level;

    public FollowOwnerGoal(Drone drone) {
        this.drone = drone;
        level = drone.level;
    }

    @Override
    public boolean canUse() {
        return drone.isFollowingOwner() && drone.getOwner().isPresent();
    }

    @Override
    public void tick() {
        drone.getOwner().ifPresent(uuid -> {
            PlayerEntity player = level.getPlayerByUUID(uuid);
            if (player != null) {
                if (drone.distanceToSqr(player) > 5 * 5)
                    drone.getNavigation().moveTo(player.getX(), player.getY() + 2, player.getZ(), 1);
                else if (drone.getNavigation().isInProgress())
                    drone.getNavigation().stop();
            }
        });
    }
}
