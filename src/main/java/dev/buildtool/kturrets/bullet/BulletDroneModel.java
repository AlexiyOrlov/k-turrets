package dev.buildtool.kturrets.bullet;// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.buildtool.satako.Functions;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BulletDroneModel extends EntityModel<BulletDrone> {
    private final ModelRenderer rotor1;
    private final ModelRenderer rotor2;
    private final ModelRenderer rotor3;
    private final ModelRenderer rotor4;
    private final ModelRenderer sides;
    private final ModelRenderer muzzle;
    private final ModelRenderer bb_main;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;

    public BulletDroneModel() {
        texWidth = 64;
        texHeight = 64;

        rotor1 = new ModelRenderer(this);
        rotor1.setPos(5.0F, 10.5F, 4.5F);
        rotor1.texOffs(27, 0).addBox(-0.5F, 4.5F, -1.5F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        rotor2 = new ModelRenderer(this);
        rotor2.setPos(-5.0F, 10.5F, 4.5F);
        rotor2.texOffs(24, 24).addBox(-0.5F, 4.5F, -1.5F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        rotor3 = new ModelRenderer(this);
        rotor3.setPos(-5.0F, 10.5F, -4.5F);
        rotor3.texOffs(0, 24).addBox(-0.5F, 4.5F, -1.5F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        rotor4 = new ModelRenderer(this);
        rotor4.setPos(5.0F, 10.5F, -4.5F);
        rotor4.texOffs(23, 19).addBox(-0.5F, 4.5F, -1.5F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        sides = new ModelRenderer(this);
        sides.setPos(0.0F, 14.5F, 0.0F);
        sides.texOffs(0, 29).addBox(1.0F, 3.5F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);
        sides.texOffs(27, 8).addBox(-2.0F, 3.5F, -1.0F, 1.0F, 3.0F, 2.0F, 0.0F, false);

        muzzle = new ModelRenderer(this);
        muzzle.setPos(0.0F, -0.5F, 0.0F);
        sides.addChild(muzzle);
        muzzle.texOffs(0, 0).addBox(-1.0F, 4.0F, -4.0F, 2.0F, 2.0F, 5.0F, 0.0F, false);

        bb_main = new ModelRenderer(this);
        bb_main.setPos(0.0F, 24.0F, 0.0F);
        bb_main.texOffs(11, 22).addBox(-1.0F, -9.0F, -2.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
        bb_main.texOffs(22, 29).addBox(3.0F, -7.0F, -4.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(17, 29).addBox(-4.0F, -7.0F, -4.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(12, 29).addBox(-4.0F, -7.0F, 3.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(7, 29).addBox(3.0F, -7.0F, 3.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(9, 2).addBox(3.0F, -2.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        bb_main.texOffs(0, 8).addBox(-4.0F, -2.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        bb_main.texOffs(9, 18).addBox(-3.0F, -3.0F, -1.0F, 6.0F, 1.0F, 2.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(1.5F, -7.5F, -3.0F);
        bb_main.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, -0.7854F, 0.0F);
        cube_r1.texOffs(9, 10).addBox(0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(-1.5F, -7.5F, -3.0F);
        bb_main.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.7854F, 0.0F);
        cube_r2.texOffs(0, 16).addBox(-1.5F, -0.5F, -4.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(-1.5F, -7.5F, 3.0F);
        bb_main.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, -0.7854F, 0.0F);
        cube_r3.texOffs(18, 0).addBox(-1.5F, -0.5F, -2.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(1.5F, -7.5F, 3.0F);
        bb_main.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 0.7854F, 0.0F);
        cube_r4.texOffs(18, 8).addBox(0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(BulletDrone entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        sides.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
        muzzle.xRot = Functions.getDefaultHeadPitch(headPitch);
        rotor1.yRot = ageInTicks * 1.5f;
        rotor2.yRot = -ageInTicks * 1.5f;
        rotor3.yRot = ageInTicks * 1.5f;
        rotor4.yRot = -ageInTicks * 1.5f;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        rotor1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        sides.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}