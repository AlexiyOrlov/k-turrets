package dev.buildtool.kturrets.gauss;

import dev.buildtool.kturrets.PresetProjectile;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.KTEntities;
import net.minecraft.world.level.Level;

public class GaussBullet extends PresetProjectile {

    public GaussBullet(Level p_i50173_2_) {
        super(KTEntities.GAUSS_BULLET.get(), p_i50173_2_);
    }

    public GaussBullet(Turret shooter, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, Level world) {
        super(KTEntities.GAUSS_BULLET.get(), shooter, p_i50175_3_, p_i50175_5_, p_i50175_7_, world);
    }
}
