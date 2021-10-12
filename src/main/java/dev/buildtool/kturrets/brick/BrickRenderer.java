package dev.buildtool.kturrets.brick;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.buildtool.kturrets.KTurrets;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class BrickRenderer extends EntityRenderer<Brick> {
    private static final ResourceLocation TEXTURE1 = new ResourceLocation(KTurrets.ID, "textures/entity/brick.png");
    private static final ResourceLocation TEXTURE2 = new ResourceLocation(KTurrets.ID, "textures/entity/netherbrick.png");
    private final BrickModel brickModel = new BrickModel();

    public BrickRenderer(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(Brick p_110775_1_) {
        if (p_110775_1_.getDamage() == KTurrets.BRICK_DAMAGE.get())
            return TEXTURE1;
        return TEXTURE2;
    }

    @Override
    public void render(Brick p_225623_1_, float yaw, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light) {
        super.render(p_225623_1_, yaw, partialTick, matrixStack, renderTypeBuffer, light);
        matrixStack.pushPose();
        matrixStack.translate(0, -1.3, 0);
        brickModel.setupAnim(p_225623_1_, 0, 0, partialTick, yaw, 0);
        brickModel.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutout(getTextureLocation(p_225623_1_))), light, getPackedLightCoords(p_225623_1_, 0xff0f7f), 1, 1, 1, 1);
        matrixStack.popPose();
    }
}