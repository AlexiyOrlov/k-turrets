package dev.buildtool.kturrets.brick;

import dev.buildtool.kturrets.IndirectDamageSource;
import dev.buildtool.kturrets.PresetProjectile;
import dev.buildtool.kturrets.registers.TEntities;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class Brick extends PresetProjectile {
    public Brick(Level p_i50173_2_) {
        super(TEntities.BRICK.get(), p_i50173_2_);
    }

    public Brick(LivingEntity shooter, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, Level world) {
        super(TEntities.BRICK.get(), shooter, p_i50175_3_, p_i50175_5_, p_i50175_7_, world);
    }

    @Override
    protected DamageSource getDamageSource() {
        return new IndirectDamageSource("k_turrets.brick", this, getOwner());
    }

    @Override
    public void tick() {
        super.tick();
        yRotO += 1f;
    }

    @Override
    protected void onHit(HitResult p_70227_1_) {
        super.onHit(p_70227_1_);
        level.playSound(null, blockPosition(), SoundEvents.GILDED_BLACKSTONE_BREAK, SoundSource.NEUTRAL, 1, 1);
    }
}
