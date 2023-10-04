package dev.buildtool.kturrets.fireball;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.buildtool.satako.Functions;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class FireballTurretModelv3 extends EntityModel<FireballTurret> {
    private final ModelRenderer rotating;
    private final ModelRenderer bb_main;

    public FireballTurretModelv3() {
        texWidth = 128;
        texHeight = 128;

        rotating = new ModelRenderer(this);
        rotating.setPos(0.0F, 14.75F, 0.0F);
        rotating.texOffs(25, 12).addBox(-2.0F, -5.75F, -2.0F, 4.0F, 4.0F, 6.0F, 0.0F, false);
        rotating.texOffs(0, 23).addBox(-1.0F, -4.75F, -8.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
        rotating.texOffs(23, 32).addBox(2.0F, -11.75F, 0.0F, 1.0F, 7.0F, 1.0F, 0.0F, false);
        rotating.texOffs(18, 32).addBox(-3.0F, -11.75F, 0.0F, 1.0F, 7.0F, 1.0F, 0.0F, false);

        bb_main = new ModelRenderer(this);
        bb_main.setPos(0.0F, 24.0F, 0.0F);
        bb_main.texOffs(9, 32).addBox(4.0F, -5.0F, -6.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
        bb_main.texOffs(0, 32).addBox(-6.0F, -5.0F, -6.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
        bb_main.texOffs(35, 23).addBox(-6.0F, -5.0F, 4.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
        bb_main.texOffs(26, 23).addBox(4.0F, -5.0F, 4.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
        bb_main.texOffs(0, 0).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 1.0F, 10.0F, 0.0F, false);
        bb_main.texOffs(41, 0).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);
        bb_main.texOffs(0, 12).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
        bb_main.texOffs(17, 23).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(FireballTurret entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        rotating.xRot = Functions.getDefaultHeadYaw(netHeadYaw);
        rotating.yRot = Functions.getDefaultHeadPitch(headPitch);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        rotating.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}