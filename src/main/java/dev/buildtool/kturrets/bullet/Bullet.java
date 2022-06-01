package dev.buildtool.kturrets.bullet;

import dev.buildtool.kturrets.IndirectDamageSource;
import dev.buildtool.kturrets.PresetProjectile;
import dev.buildtool.kturrets.registers.TEntities;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class Bullet extends PresetProjectile {

    public Bullet(Level p_i50173_2_) {
        super(TEntities.BULLET.get(), p_i50173_2_);
    }

    public Bullet(LivingEntity p_i50175_2_, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, Level p_i50175_9_, float damage) {
        super(TEntities.BULLET.get(), p_i50175_2_, p_i50175_3_, p_i50175_5_, p_i50175_7_, p_i50175_9_);
        setDamage((int) damage);
    }

    @Override
    protected DamageSource getDamageSource() {
        return new IndirectDamageSource("k_turrets.bullet", this, getOwner());
    }
}
