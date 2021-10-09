package dev.buildtool.kturrets;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class EntityRenderer2<E extends LivingEntity, M extends EntityModel<E>> extends LivingRenderer<E, M> {

    private final ResourceLocation texture;
    private final boolean renderName;

    public EntityRenderer2(EntityRendererManager rendererManager, M entityModelIn, String textureName, boolean renderName, float shadowSizeIn) {
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
