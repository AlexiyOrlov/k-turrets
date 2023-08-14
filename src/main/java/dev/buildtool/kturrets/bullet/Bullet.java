package dev.buildtool.kturrets.bullet;

import dev.buildtool.kturrets.IndirectDamageSource;
import dev.buildtool.kturrets.PresetProjectile;
import dev.buildtool.kturrets.registers.KEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class Bullet extends PresetProjectile {

    public Bullet(World p_i50173_2_) {
        super(KEntities.BULLET, p_i50173_2_);
    }

    public Bullet(LivingEntity p_i50175_2_, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, World p_i50175_9_, float damage) {
        super(KEntities.BULLET, p_i50175_2_, p_i50175_3_, p_i50175_5_, p_i50175_7_, p_i50175_9_);
        setDamage((int) damage);
    }

    @Override
    protected DamageSource getDamageSource() {
        return new IndirectDamageSource("k-turrets.bullet", this, getOwner());
    }
}
