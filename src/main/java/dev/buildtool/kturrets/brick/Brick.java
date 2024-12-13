package dev.buildtool.kturrets.brick;

import dev.buildtool.kturrets.PresetProjectile;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.KTEntities;
import net.minecraft.world.level.Level;

public class Brick extends PresetProjectile {
    public Brick(Level p_i50173_2_) {
        super(KTEntities.BRICK.get(), p_i50173_2_);
    }

    public Brick(Turret shooter, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, Level world) {
        super(KTEntities.BRICK.get(), shooter, p_i50175_3_, p_i50175_5_, p_i50175_7_, world);
    }

    @Override
    public void tick() {
        super.tick();
        yRotO += 1f;
    }
}
