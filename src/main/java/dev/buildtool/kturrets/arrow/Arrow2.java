package dev.buildtool.kturrets.arrow;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class Arrow2 extends ArrowEntity {
    public Arrow2(World world, AbstractArrowEntity abstractArrowEntity, LivingEntity shooter, float f) {
        super(EntityType.ARROW, world);
        copyPosition(abstractArrowEntity);
        setDeltaMovement(abstractArrowEntity.getDeltaMovement());
        setEnchantmentEffectsFromEntity(shooter, f);
        setPierceLevel(abstractArrowEntity.getPierceLevel());
    }

    @Override
    protected void onHit(RayTraceResult p_70227_1_) {
        super.onHit(p_70227_1_);
        remove();
    }
}
