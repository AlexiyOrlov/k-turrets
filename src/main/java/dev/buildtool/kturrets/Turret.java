package dev.buildtool.kturrets;

import dev.buildtool.satako.InanimateEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.world.World;

public abstract class Turret extends InanimateEntity implements IRangedAttackMob, INamedContainerProvider {
    public Turret(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public static AttributeModifierMap.MutableAttribute createDefaultAttributes() {
        return createLivingAttributes().add(Attributes.FOLLOW_RANGE, 32).add(Attributes.MOVEMENT_SPEED, 0).add(Attributes.MAX_HEALTH, 60);
    }
}
