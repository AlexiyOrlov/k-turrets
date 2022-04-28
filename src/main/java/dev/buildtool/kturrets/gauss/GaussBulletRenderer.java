package dev.buildtool.kturrets.gauss;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.buildtool.kturrets.KTurrets;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class GaussBulletRenderer extends EntityRenderer<GaussBullet> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(KTurrets.ID, "textures/entity/gauss_bullet.png");
    private final GaussBulletModel<GaussBullet> bulletModel;

    public GaussBulletRenderer(EntityRendererProvider.Context p_i46179_1_) {
        super(p_i46179_1_);
        shadowRadius = 0;
        bulletModel = new GaussBulletModel<>(p_i46179_1_.bakeLayer(new ModelLayerLocation(TEXTURE, "main")));
    }

    @Override
    public ResourceLocation getTextureLocation(GaussBullet p_110775_1_) {
        return TEXTURE;
    }

    @Override
    public void render(GaussBullet bullet, float yaw, float partialTick, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int packedLight) {
        super.render(bullet, yaw, partialTick, matrixStack, renderTypeBuffer, packedLight);
        matrixStack.pushPose();
        matrixStack.translate(0, -1.4, 0);
        bulletModel.setupAnim(bullet, 0, 0, partialTick, yaw, 0);
        bulletModel.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutout(getTextureLocation(bullet))), packedLight, getPackedLightCoords(bullet, 0xff0f7f), 1, 1, 1, 1);
        matrixStack.popPose();
    }
}
