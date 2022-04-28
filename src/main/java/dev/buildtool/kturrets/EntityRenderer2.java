package dev.buildtool.kturrets;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class EntityRenderer2<E extends LivingEntity, M extends EntityModel<E>> extends LivingEntityRenderer<E, M> {

    private final ResourceLocation texture;
    private final boolean renderName;

    public EntityRenderer2(EntityRendererProvider.Context rendererManager, M entityModelIn, String textureName, boolean renderName, float shadowSizeIn) {
        super(rendererManager, entityModelIn, shadowSizeIn);
        texture = new ResourceLocation(KTurrets.ID, "textures/entity/" + textureName + ".png");
        this.renderName = renderName;
    }

    @Override
    public ResourceLocation getTextureLocation(E entity) {
        return texture;
    }

    @Override
    protected boolean shouldShowName(E entity) {
        return renderName && super.shouldShowName(entity);
    }
}
