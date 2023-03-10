package dev.buildtool.kturrets;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;

public class DroneMovementControl extends FlyingMoveControl {
    public DroneMovementControl(Mob p_24893_, int p_24894_, boolean p_24895_) {
        super(p_24893_, p_24894_, p_24895_);
    }

    @Override
    public void tick() {
        if (this.operation == MoveControl.Operation.STRAFE) {
            float f = (float) this.mob.getAttributeValue(Attributes.FLYING_SPEED);
            this.mob.setSpeed(f);
            this.mob.setZza(this.strafeForwards);
            this.mob.setXxa(this.strafeRight);
            this.operation = MoveControl.Operation.WAIT;
        } else
            super.tick();
    }
}
