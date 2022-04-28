package dev.buildtool.kturrets.cobble;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.buildtool.kturrets.KTurrets;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CobblestoneRenderer extends EntityRenderer<Cobblestone> {
    private final CobblestoneModel<Cobblestone> cobblestoneModel;
    private final ResourceLocation TEXTURE = new ResourceLocation(KTurrets.ID, "textures/entity/cobblestone.png");

    public CobblestoneRenderer(EntityRendererProvider.Context p_i46179_1_) {
        super(p_i46179_1_);
        cobblestoneModel = new CobblestoneModel<>(p_i46179_1_.bakeLayer(new ModelLayerLocation(TEXTURE, "main")));
    }

    @Override
    public ResourceLocation getTextureLocation(Cobblestone p_110775_1_) {
        return TEXTURE;
    }

    @Override
    public void render(Cobblestone p_225623_1_, float yaw, float partialTick, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light) {
        super.render(p_225623_1_, yaw, partialTick, matrixStack, renderTypeBuffer, light);
        matrixStack.pushPose();
        matrixStack.translate(0, -1.3, 0);
        cobblestoneModel.setupAnim(p_225623_1_, 0, 0, partialTick, yaw, 0);
        cobblestoneModel.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutout(getTextureLocation(p_225623_1_))), light, getPackedLightCoords(p_225623_1_, 0xff0f7f), 1, 1, 1, 1);
        matrixStack.popPose();
    }
}
