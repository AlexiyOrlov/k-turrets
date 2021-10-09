package dev.buildtool.kturrets.arrow;

import dev.buildtool.kturrets.EntityRenderer2;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class ArrowTurretRenderer extends EntityRenderer2<ArrowTurret, ArrowTurretModel> {
    public ArrowTurretRenderer(EntityRendererManager rendererManager, ArrowTurretModel entityModelIn, String textureName, boolean renderName, float shadowSizeIn) {
        super(rendererManager, entityModelIn, textureName, renderName, shadowSizeIn);
    }
}
