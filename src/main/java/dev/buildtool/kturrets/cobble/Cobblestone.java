package dev.buildtool.kturrets.cobble;

import dev.buildtool.kturrets.IndirectDamageSource;
import dev.buildtool.kturrets.PresetProjectile;
import dev.buildtool.kturrets.registers.KEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class Cobblestone extends PresetProjectile {
    public Cobblestone(World p_i50173_2_) {
        super(KEntities.COBBLESTONE, p_i50173_2_);
    }

    public Cobblestone(LivingEntity shooter, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, World world) {
        super(KEntities.COBBLESTONE, shooter, p_i50175_3_, p_i50175_5_, p_i50175_7_, world);
    }

    @Override
    protected DamageSource getDamageSource() {
        return new IndirectDamageSource("k-turrets.cobblestone", this, getOwner());
    }

    @Override
    protected void onHit(RayTraceResult p_70227_1_) {
        super.onHit(p_70227_1_);
        level.playSound(null, blockPosition(), SoundEvents.NETHER_BRICKS_BREAK, SoundCategory.NEUTRAL, 1, 1);
    }
}
