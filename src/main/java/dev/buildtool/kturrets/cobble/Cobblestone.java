package dev.buildtool.kturrets.cobble;

import dev.buildtool.kturrets.PresetProjectile;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.KTEntities;
import net.minecraft.world.level.Level;

public class Cobblestone extends PresetProjectile {
    public Cobblestone(Level p_i50173_2_) {
        super(KTEntities.COBBLESTONE.get(), p_i50173_2_);
    }

    public Cobblestone(Turret shooter, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, Level world) {
        super(KTEntities.COBBLESTONE.get(), shooter, p_i50175_3_, p_i50175_5_, p_i50175_7_, world);
    }
}
