package dev.buildtool.kturrets.gauss;

import dev.buildtool.kturrets.PresetProjectile;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.KEntities;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class GaussBullet extends PresetProjectile {
    {
        movementMultiplier = 150;
    }

    public GaussBullet(Level p_i50173_2_) {
        super(KEntities.GAUSS_BULLET.get(), p_i50173_2_);
    }

    public GaussBullet(Turret shooter, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, Level world) {
        super(KEntities.GAUSS_BULLET.get(), shooter, p_i50175_3_, p_i50175_5_, p_i50175_7_, world);
    }

    @Override
    protected DamageSource getDamageSource() {
        return damageSources().mobProjectile(this, (LivingEntity) getOwner()); //new IndirectDamageSource("k_turrets.gauss_bullet", this, getOwner());
    }
}
