package dev.buildtool.kturrets.gauss;

import dev.buildtool.kturrets.PresetProjectile;
import dev.buildtool.kturrets.registers.TEntities;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class GaussBullet extends PresetProjectile {
    public GaussBullet(Level p_i50173_2_) {
        super(TEntities.GAUSS_BULLET, p_i50173_2_);
    }

    public GaussBullet(LivingEntity shooter, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, Level world) {
        super(TEntities.GAUSS_BULLET, shooter, p_i50175_3_, p_i50175_5_, p_i50175_7_, world);
    }

    @Override
    protected DamageSource getDamageSource() {
        return new IndirectEntityDamageSource("k-turrets.gauss_bullet", this, getOwner());
    }

    @Override
    public void tick() {
        super.tick();
        step();
        step();
    }
}
