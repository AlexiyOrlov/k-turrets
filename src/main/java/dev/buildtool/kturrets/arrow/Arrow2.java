package dev.buildtool.kturrets.arrow;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;


public class Arrow2 extends Arrow {
    public Arrow2(Level world, AbstractArrow abstractArrowEntity, LivingEntity shooter, float f) {
        super(EntityType.ARROW, world);
        copyPosition(abstractArrowEntity);
        setDeltaMovement(abstractArrowEntity.getDeltaMovement());
        setEnchantmentEffectsFromEntity(shooter, f);
        setPierceLevel(abstractArrowEntity.getPierceLevel());
        if (abstractArrowEntity instanceof SpectralArrow) {
            addEffect(new MobEffectInstance(MobEffects.GLOWING, 200));
        } else if (abstractArrowEntity instanceof Arrow) {
//            FIXME
//            potion = ((Arrow) abstractArrowEntity).potion;
//            ((Arrow) abstractArrowEntity).effects.forEach(this::addEffect);
        }
        setOwner(abstractArrowEntity.getOwner());

    }

    @Override
    protected void onHit(HitResult p_70227_1_) {
        super.onHit(p_70227_1_);
        discard();
    }
}
