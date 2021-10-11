package dev.buildtool.kturrets.bullet;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.buildtool.kturrets.KTurrets;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class BulletRenderer extends EntityRenderer<Bullet> {
    private static final ResourceLocation GOLD = new ResourceLocation(KTurrets.ID, "textures/entity/gold_bullet.png"),
            IRON = new ResourceLocation(KTurrets.ID, "textures/entity/iron_bullet.png");
    private final BulletModel bulletModel = new BulletModel();

    public BulletRenderer(EntityRendererManager rendererManager) {
        super(rendererManager);
        shadowRadius = 0;
    }

    @Override
    public ResourceLocation getTextureLocation(Bullet p_110775_1_) {
        if (p_110775_1_.getDamage() == 7)
            return IRON;
        else
            return GOLD;
    }

    @Override
    public void render(Bullet bullet, float yaw, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int packedLight) {
        super.render(bullet, yaw, partialTick, matrixStack, renderTypeBuffer, packedLight);
        bulletModel.setupAnim(bullet, 0, 0, partialTick, yaw, 0);
        bulletModel.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutout(getTextureLocation(bullet))), packedLight, getPackedLightCoords(bullet, 0xff0f7f), 1, 1, 1, 1);
    }
}
