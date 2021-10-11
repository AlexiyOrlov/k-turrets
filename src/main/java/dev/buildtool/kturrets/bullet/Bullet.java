package dev.buildtool.kturrets.bullet;

import dev.buildtool.kturrets.PresetProjectile;
import dev.buildtool.kturrets.registers.TEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class Bullet extends PresetProjectile {

    public Bullet(World p_i50173_2_) {
        super(TEntities.BULLET, p_i50173_2_);
    }

    public Bullet(double p_i50174_2_, double p_i50174_4_, double p_i50174_6_, double p_i50174_8_, double p_i50174_10_, double p_i50174_12_, World p_i50174_14_) {
        super(TEntities.BULLET, p_i50174_2_, p_i50174_4_, p_i50174_6_, p_i50174_8_, p_i50174_10_, p_i50174_12_, p_i50174_14_);
    }

    public Bullet(LivingEntity p_i50175_2_, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, World p_i50175_9_, float damage) {
        super(TEntities.BULLET, p_i50175_2_, p_i50175_3_, p_i50175_5_, p_i50175_7_, p_i50175_9_);
        setDamage((int) damage);
    }

    @Override
    protected DamageSource getDamageSource() {
        return new IndirectEntityDamageSource("k-turrets.bullet", this, getOwner());
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
        super.onHitBlock(p_230299_1_);
        remove();
    }

}
