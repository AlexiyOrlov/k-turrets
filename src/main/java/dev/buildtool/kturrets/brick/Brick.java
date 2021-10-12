package dev.buildtool.kturrets.brick;

import dev.buildtool.kturrets.PresetProjectile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.world.World;

public class Brick extends PresetProjectile {
    public Brick(EntityType<? extends DamagingProjectileEntity> p_i50173_1_, World p_i50173_2_) {
        super(p_i50173_1_, p_i50173_2_);
    }

    public Brick(EntityType<? extends DamagingProjectileEntity> p_i50175_1_, LivingEntity shooter, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, World world) {
        super(p_i50175_1_, shooter, p_i50175_3_, p_i50175_5_, p_i50175_7_, world);
    }

    @Override
    protected DamageSource getDamageSource() {
        return new IndirectEntityDamageSource("k-turrets.brick", this, getOwner());
    }
}
