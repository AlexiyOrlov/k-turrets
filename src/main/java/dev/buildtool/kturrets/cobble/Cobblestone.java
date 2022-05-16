package dev.buildtool.kturrets.cobble;

import dev.buildtool.kturrets.IndirectDamageSource;
import dev.buildtool.kturrets.PresetProjectile;
import dev.buildtool.kturrets.registers.TEntities;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class Cobblestone extends PresetProjectile {
    public Cobblestone(Level p_i50173_2_) {
        super(TEntities.COBBLESTONE.get(), p_i50173_2_);
    }

    public Cobblestone(LivingEntity shooter, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, Level world) {
        super(TEntities.COBBLESTONE.get(), shooter, p_i50175_3_, p_i50175_5_, p_i50175_7_, world);
    }

    @Override
    protected DamageSource getDamageSource() {
        return new IndirectDamageSource("k-turrets.cobblestone", this, getOwner());
    }

    @Override
    protected void onHit(HitResult p_70227_1_) {
        super.onHit(p_70227_1_);
        level.playSound(null, blockPosition(), SoundEvents.NETHER_BRICKS_BREAK, SoundSource.NEUTRAL, 1, 1);
    }
}
