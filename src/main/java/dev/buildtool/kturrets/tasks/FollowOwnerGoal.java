package dev.buildtool.kturrets.tasks;

import dev.buildtool.kturrets.Drone;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FollowOwnerGoal extends Goal {
    protected Drone drone;
    protected Level level;

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
            Player player = level.getPlayerByUUID(uuid);
            if (player != null) {
                if (drone.distanceToSqr(player) > 5 * 5)
                    drone.getNavigation().moveTo(player.getX(), player.getY() + 1, player.getZ(), 1);
                else drone.getNavigation().stop();
            }
        });
    }
}
