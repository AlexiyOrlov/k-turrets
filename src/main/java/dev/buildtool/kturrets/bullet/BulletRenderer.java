package dev.buildtool.kturrets.bullet;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.buildtool.kturrets.KTurrets;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BulletRenderer extends EntityRenderer<Bullet> {
    private static final ResourceLocation GOLD = new ResourceLocation(KTurrets.ID, "textures/entity/gold_bullet.png"),
            IRON = new ResourceLocation(KTurrets.ID, "textures/entity/iron_bullet.png");
    private final BulletModel<Bullet> bulletModel;

    public BulletRenderer(EntityRendererProvider.Context rendererManager) {
        super(rendererManager);
        shadowRadius = 0;
        bulletModel = new BulletModel<>(rendererManager.bakeLayer(BulletModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(Bullet p_110775_1_) {
        if (p_110775_1_.getDamage() == KTurrets.IRON_BULLET_DAMAGE.get())
            return IRON;
        else
            return GOLD;
    }

    @Override
    public void render(Bullet bullet, float yaw, float partialTick, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int packedLight) {
        super.render(bullet, yaw, partialTick, matrixStack, renderTypeBuffer, packedLight);
        matrixStack.pushPose();
        matrixStack.translate(0, -1.4, 0);
        bulletModel.setupAnim(bullet, 0, 0, partialTick, yaw, 0);
        bulletModel.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutout(getTextureLocation(bullet))), packedLight, getPackedLightCoords(bullet, 0xff0f7f), 1, 1, 1, 1);
        matrixStack.popPose();
    }
}
