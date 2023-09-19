package dev.buildtool.kturrets.gauss;

import dev.buildtool.kturrets.IndirectDamageSource;
import dev.buildtool.kturrets.PresetProjectile;
import dev.buildtool.kturrets.registers.KEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GaussBullet extends PresetProjectile {

    static {
        MOVEMENT_MULTIPLIER = MOVEMENT_MULTIPLIER * 3;
    }

    public GaussBullet(World p_i50173_2_) {
        super(KEntities.GAUSS_BULLET, p_i50173_2_);
    }

    public GaussBullet(LivingEntity shooter, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, World world) {
        super(KEntities.GAUSS_BULLET, shooter, p_i50175_3_, p_i50175_5_, p_i50175_7_, world);
    }

    @Override
    protected DamageSource getDamageSource() {
        return new IndirectDamageSource("k-turrets.gauss_bullet", this, getOwner());
    }
}
