package dev.buildtool.kturrets.cobble;

import dev.buildtool.kturrets.PresetProjectile;
import dev.buildtool.kturrets.registers.TEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.world.World;

public class Cobblestone extends PresetProjectile {
    public Cobblestone(World p_i50173_2_) {
        super(TEntities.COBBLESTONE, p_i50173_2_);
    }

    public Cobblestone(LivingEntity shooter, double p_i50175_3_, double p_i50175_5_, double p_i50175_7_, World world) {
        super(TEntities.COBBLESTONE, shooter, p_i50175_3_, p_i50175_5_, p_i50175_7_, world);
    }

    @Override
    protected DamageSource getDamageSource() {
        return new IndirectEntityDamageSource("k-turrets.cobblestone", this, getOwner());
    }
}
