package dev.buildtool.kturrets.gauss;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.buildtool.kturrets.KTurrets;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class GaussBulletRenderer extends EntityRenderer<GaussBullet> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(KTurrets.ID, "textures/entity/gauss_bullet.png");
    private final GaussBulletModel bulletModel = new GaussBulletModel();

    public GaussBulletRenderer(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
        shadowRadius = 0;
    }

    @Override
    public ResourceLocation getTextureLocation(GaussBullet p_110775_1_) {
        return TEXTURE;
    }

    @Override
    public void render(GaussBullet bullet, float yaw, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int packedLight) {
        super.render(bullet, yaw, partialTick, matrixStack, renderTypeBuffer, packedLight);
        matrixStack.pushPose();
        matrixStack.translate(0, -1.4, 0);
        bulletModel.setupAnim(bullet, 0, 0, partialTick, yaw, 0);
        bulletModel.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutout(getTextureLocation(bullet))), packedLight, getPackedLightCoords(bullet, 0xff0f7f), 1, 1, 1, 1);
        matrixStack.popPose();
    }
}
