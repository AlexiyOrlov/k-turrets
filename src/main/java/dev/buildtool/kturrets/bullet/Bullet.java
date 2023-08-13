package dev.buildtool.kturrets.bullet;

import dev.buildtool.kturrets.PresetProjectile;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.KEntities;
import net.minecraft.world.level.Level;

public class Bullet extends PresetProjectile {

    public Bullet(Level p_i50173_2_) {
        super(KEntities.BULLET.get(), p_i50173_2_);
    }

    public Bullet(Turret p_i50175_2_, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, Level p_i50175_9_, float damage) {
        super(KEntities.BULLET.get(), p_i50175_2_, p_i50175_3_, p_i50175_5_, p_i50175_7_, p_i50175_9_);
        setDamage((int) damage);
    }
}
