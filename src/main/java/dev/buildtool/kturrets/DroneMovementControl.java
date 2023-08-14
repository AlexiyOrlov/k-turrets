package dev.buildtool.kturrets;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.controller.MovementController;

public class DroneMovementControl extends FlyingMovementController {
    public DroneMovementControl(MobEntity p_i225710_1_, int p_i225710_2_, boolean p_i225710_3_) {
        super(p_i225710_1_, p_i225710_2_, p_i225710_3_);
    }

    @Override
    public void tick() {
        if (this.operation == MovementController.Action.STRAFE) {
            float f = (float) this.mob.getAttributeValue(Attributes.FLYING_SPEED);
            this.mob.setSpeed(f);
            this.mob.setZza(this.strafeForwards);
            this.mob.setXxa(this.strafeRight);
            this.operation = MovementController.Action.WAIT;
        } else
            super.tick();
    }
}
