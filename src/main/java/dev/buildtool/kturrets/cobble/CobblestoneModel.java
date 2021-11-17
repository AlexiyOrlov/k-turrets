package dev.buildtool.kturrets.cobble;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class CobblestoneModel extends EntityModel<Cobblestone> {
    private final ModelRenderer bb_main;

    public CobblestoneModel() {
        texWidth = 16;
        texHeight = 16;

        bb_main = new ModelRenderer(this);
        bb_main.setPos(0.0F, 24.0F, 0.0F);
        bb_main.texOffs(0, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        bb_main.texOffs(0, 3).addBox(-1.0F, -3.0F, -2.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(10, 13).addBox(-1.0F, -3.0F, 1.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(0, 0).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
        bb_main.texOffs(0, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
        bb_main.texOffs(10, 0).addBox(1.0F, -3.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
        bb_main.texOffs(0, 8).addBox(-2.0F, -3.0F, -1.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(Cobblestone entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}