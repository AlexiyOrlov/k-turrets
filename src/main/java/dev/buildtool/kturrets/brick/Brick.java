package dev.buildtool.kturrets.brick;

import dev.buildtool.kturrets.IndirectDamageSource;
import dev.buildtool.kturrets.PresetProjectile;
import dev.buildtool.kturrets.registers.TEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class Brick extends PresetProjectile {
    public Brick(World p_i50173_2_) {
        super(TEntities.BRICK, p_i50173_2_);
    }

    public Brick(LivingEntity shooter, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, World world) {
        super(TEntities.BRICK, shooter, p_i50175_3_, p_i50175_5_, p_i50175_7_, world);
    }

    @Override
    protected DamageSource getDamageSource() {
        return new IndirectDamageSource("k-turrets.brick", this, getOwner());
    }

    @Override
    public void tick() {
        super.tick();
        yRot += 1f;
    }

    @Override
    protected void onHit(RayTraceResult p_70227_1_) {
        super.onHit(p_70227_1_);
        level.playSound(null, blockPosition(), SoundEvents.GILDED_BLACKSTONE_BREAK, SoundCategory.NEUTRAL, 1, 1);
    }
}
