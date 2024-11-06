package dev.buildtool.kturrets.tasks;

import dev.buildtool.kturrets.Drone;
import dev.buildtool.kturrets.KTurrets;
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
            super.tick();
        else {
            float distanceToPlayer = drone.distanceTo(owner);
            if (distanceToPlayer < KTurrets.OWNER_FOLLOW_DISTANCE.get())
                super.tick();
            else drone.setTarget(null);
        }
    }
}
