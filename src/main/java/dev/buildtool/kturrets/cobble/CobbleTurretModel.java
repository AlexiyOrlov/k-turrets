package dev.buildtool.kturrets.cobble;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.buildtool.satako.Functions;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class CobbleTurretModel extends EntityModel<CobbleTurret> {
    private final ModelRenderer barrel;
    private final ModelRenderer bb_main;

    public CobbleTurretModel() {
        texWidth = 64;
        texHeight = 64;

        barrel = new ModelRenderer(this);
        barrel.setPos(0.0F, 7.0F, -0.1F);
        barrel.texOffs(21, 28).addBox(-2.0F, -6.0F, -3.9F, 4.0F, 6.0F, 4.0F, 0.0F, false);
        barrel.texOffs(25, 0).addBox(-3.0F, -8.0F, -1.9F, 6.0F, 2.0F, 6.0F, 0.0F, false);
        barrel.texOffs(25, 0).addBox(-3.0F, -1.0F, 1.1F, 6.0F, 1.0F, 3.0F, 0.0F, false);
        barrel.texOffs(0, 21).addBox(2.0F, -6.0F, -2.9F, 2.0F, 5.0F, 7.0F, 0.0F, false);
        barrel.texOffs(18, 14).addBox(-4.0F, -6.0F, -2.9F, 2.0F, 5.0F, 7.0F, 0.0F, false);
        barrel.texOffs(32, 7).addBox(-1.0F, -4.0F, -9.9F, 2.0F, 2.0F, 6.0F, 0.0F, false);
        barrel.texOffs(0, 36).addBox(-1.0F, -5.0F, 0.0F, 2.0F, 3.0F, 4.0F, 0.0F, false);

        bb_main = new ModelRenderer(this);
        bb_main.setPos(0.0F, 24.0F, 0.0F);
        bb_main.texOffs(0, 0).addBox(-3.0F, -14.0F, -3.0F, 6.0F, 14.0F, 6.0F, 0.0F, false);
        bb_main.texOffs(25, 7).addBox(-1.0F, -17.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
        bb_main.texOffs(0, 44).addBox(-7.0F, -1.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        bb_main.texOffs(0, 44).addBox(3.0F, -1.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        bb_main.texOffs(0, 44).addBox(-2.0F, -1.0F, 3.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        bb_main.texOffs(0, 44).addBox(-2.0F, -1.0F, -7.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(CobbleTurret entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        barrel.xRot = Functions.getDefaultHeadPitch(headPitch);
        barrel.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        barrel.render(matrixStack, buffer, packedLight, packedOverlay);
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}