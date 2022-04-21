package dev.buildtool.kturrets.bullet;// Made with Blockbench 4.2.2
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.buildtool.satako.Functions;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BulletTurretv2 extends EntityModel<BulletTurret> {
    private final ModelRenderer bone;
    private final ModelRenderer rotating;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer bb_main;

    public BulletTurretv2() {
        texWidth = 64;
        texHeight = 64;

        bone = new ModelRenderer(this);
        bone.setPos(0.0F, 24.0F, 0.0F);


        rotating = new ModelRenderer(this);
        rotating.setPos(0.0F, 21.0F, 0.0F);
        rotating.texOffs(0, 0).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 2.0F, 7.0F, 0.0F, false);
        rotating.texOffs(10, 42).addBox(-2.0F, -10.0F, -2.0F, 4.0F, 4.0F, 6.0F, 0.0F, false);
        rotating.texOffs(28, 24).addBox(-2.0F, -11.0F, -1.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        rotating.texOffs(0, 34).addBox(-4.0F, -10.0F, -1.0F, 2.0F, 4.0F, 4.0F, 0.0F, false);
        rotating.texOffs(22, 36).addBox(-6.0F, -9.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        rotating.texOffs(30, 32).addBox(2.0F, -10.0F, -1.0F, 2.0F, 4.0F, 4.0F, 0.0F, false);
        rotating.texOffs(9, 26).addBox(4.0F, -9.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        rotating.texOffs(10, 30).addBox(-0.5F, -8.5F, -5.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        rotating.texOffs(34, 16).addBox(-1.0F, -9.0F, -8.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);
        rotating.texOffs(28, 10).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
        rotating.texOffs(0, 18).addBox(-3.0F, -4.0F, -2.0F, 6.0F, 2.0F, 5.0F, 0.0F, false);
        rotating.texOffs(0, 3).addBox(2.0F, -12.0F, 1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        rotating.texOffs(0, 0).addBox(-3.0F, -12.0F, 1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        rotating.texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(2.5F, -12.5F, 1.5F);
        rotating.addChild(cube_r1);
        setRotationAngle(cube_r1, -0.6109F, 0.0F, 0.0F);
        cube_r1.texOffs(0, 26).addBox(-5.5F, -0.5F, -4.5F, 1.0F, 1.0F, 7.0F, 0.0F, false);
        cube_r1.texOffs(0, 26).addBox(-0.5F, -0.5F, -4.5F, 1.0F, 1.0F, 7.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(6.5F, -8.0F, 1.0F);
        rotating.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.7854F, 0.0F, 0.0F);
        cube_r2.texOffs(18, 12).addBox(-0.5F, -3.0F, -3.0F, 1.0F, 6.0F, 6.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(-6.5F, -8.0F, 1.0F);
        rotating.addChild(cube_r3);
        setRotationAngle(cube_r3, -0.7854F, 0.0F, 0.0F);
        cube_r3.texOffs(18, 24).addBox(-0.5F, -3.0F, -3.0F, 1.0F, 6.0F, 6.0F, 0.0F, false);

        bb_main = new ModelRenderer(this);
        bb_main.setPos(0.0F, 24.0F, 0.0F);
        bb_main.texOffs(0, 9).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 3.0F, 6.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(BulletTurret entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        rotating.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
        rotating.xRot = Functions.getDefaultHeadPitch(headPitch);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bone.render(matrixStack, buffer, packedLight, packedOverlay);
        rotating.render(matrixStack, buffer, packedLight, packedOverlay);
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}