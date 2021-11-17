package dev.buildtool.kturrets.cobble;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.buildtool.kturrets.KTurrets;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class CobblestoneRenderer extends EntityRenderer<Cobblestone> {
    private final CobblestoneModel cobblestoneModel = new CobblestoneModel();
    private ResourceLocation TEXTURE = new ResourceLocation(KTurrets.ID, "textures/entity/cobblestone.png");

    public CobblestoneRenderer(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(Cobblestone p_110775_1_) {
        return TEXTURE;
    }

    @Override
    public void render(Cobblestone p_225623_1_, float yaw, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light) {
        super.render(p_225623_1_, yaw, partialTick, matrixStack, renderTypeBuffer, light);
        matrixStack.pushPose();
        matrixStack.translate(0, -1.3, 0);
        cobblestoneModel.setupAnim(p_225623_1_, 0, 0, partialTick, yaw, 0);
        cobblestoneModel.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutout(getTextureLocation(p_225623_1_))), light, getPackedLightCoords(p_225623_1_, 0xff0f7f), 1, 1, 1, 1);
        matrixStack.popPose();
    }
}
