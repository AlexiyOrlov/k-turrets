package dev.buildtool.kturrets.brick;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.buildtool.satako.Functions;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BrickTurretModelv2 extends EntityModel<BrickTurret> {
    private final ModelRenderer left;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer right;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;
    private final ModelRenderer rotating;
    private final ModelRenderer cube_r5;
    private final ModelRenderer bb_main;

    public BrickTurretModelv2() {
        texWidth = 32;
        texHeight = 32;

        left = new ModelRenderer(this);
        left.setPos(5.5F, 20.5F, 3.5F);
        left.texOffs(16, 14).addBox(-0.5F, -0.5F, -6.5F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        left.texOffs(0, 20).addBox(-5.5F, 0.5F, -4.5F, 5.0F, 1.0F, 2.0F, 0.0F, false);
        left.texOffs(10, 20).addBox(-5.5F, -0.5F, -5.5F, 1.0F, 1.0F, 4.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(0.0F, 0.0F, 0.0F);
        left.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.7854F, 0.0F, 0.0F);
        cube_r1.texOffs(8, 25).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(0.0F, 0.0F, -7.0F);
        left.addChild(cube_r2);
        setRotationAngle(cube_r2, -0.7854F, 0.0F, 0.0F);
        cube_r2.texOffs(12, 25).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, 0.0F, false);

        right = new ModelRenderer(this);
        right.setPos(-5.5F, 20.5F, 3.5F);
        right.texOffs(8, 13).addBox(-0.5F, -0.5F, -6.5F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        right.texOffs(11, 0).addBox(0.5F, 0.5F, -4.5F, 5.0F, 1.0F, 2.0F, 0.0F, false);
        right.texOffs(20, 3).addBox(4.5F, -0.5F, -5.5F, 1.0F, 1.0F, 4.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(0.0F, 0.0F, 0.0F);
        right.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.7854F, 0.0F, 0.0F);
        cube_r3.texOffs(8, 9).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, 0.0F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(0.0F, 0.0F, -7.0F);
        right.addChild(cube_r4);
        setRotationAngle(cube_r4, -0.7854F, 0.0F, 0.0F);
        cube_r4.texOffs(16, 13).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 5.0F, 1.0F, 0.0F, false);

        rotating = new ModelRenderer(this);
        rotating.setPos(0.0F, 20.0F, 0.0F);
        rotating.texOffs(0, 0).addBox(-1.0F, -4.0F, -6.0F, 2.0F, 2.0F, 7.0F, 0.0F, false);
        rotating.texOffs(11, 5).addBox(1.0F, -3.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        rotating.texOffs(12, 3).addBox(3.0F, -5.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);
        rotating.texOffs(11, 3).addBox(-3.0F, -3.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        rotating.texOffs(0, 9).addBox(-4.0F, -5.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);
        rotating.texOffs(0, 23).addBox(-0.5F, -3.5F, 1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        rotating.texOffs(20, 21).addBox(-1.5F, -5.5F, 4.0F, 3.0F, 4.0F, 1.0F, 0.0F, false);

        cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(0.0F, -4.0F, 0.0F);
        rotating.addChild(cube_r5);
        setRotationAngle(cube_r5, -0.3491F, 0.0F, 0.0F);
        cube_r5.texOffs(0, 0).addBox(-2.0F, -4.0F, -1.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        cube_r5.texOffs(0, 9).addBox(1.0F, -4.0F, -1.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);

        bb_main = new ModelRenderer(this);
        bb_main.setPos(0.0F, 24.0F, 0.0F);
        bb_main.texOffs(24, 11).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(BrickTurret entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        rotating.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
        rotating.xRot = Functions.getDefaultHeadPitch(headPitch);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        left.render(matrixStack, buffer, packedLight, packedOverlay);
        right.render(matrixStack, buffer, packedLight, packedOverlay);
        rotating.render(matrixStack, buffer, packedLight, packedOverlay);
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}