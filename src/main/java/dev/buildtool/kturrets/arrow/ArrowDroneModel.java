package dev.buildtool.kturrets.arrow;// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.buildtool.satako.Functions;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ArrowDroneModel extends EntityModel<ArrowDrone> {
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
    private final ModelRenderer cube_r5;
    private final ModelRenderer cube_r6;

    public ArrowDroneModel() {
        texWidth = 64;
        texHeight = 64;

        rotor1 = new ModelRenderer(this);
        rotor1.setPos(2.5F, 10.5F, -3.5F);
        rotor1.texOffs(38, 37).addBox(-0.5F, 2.5F, -1.5F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        rotor2 = new ModelRenderer(this);
        rotor2.setPos(-2.5F, 10.5F, -3.5F);
        rotor2.texOffs(37, 27).addBox(-0.5F, 2.5F, -1.5F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        rotor3 = new ModelRenderer(this);
        rotor3.setPos(-2.5F, 10.5F, 3.5F);
        rotor3.texOffs(33, 0).addBox(-0.5F, 2.5F, -1.5F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        rotor4 = new ModelRenderer(this);
        rotor4.setPos(2.5F, 10.5F, 3.5F);
        rotor4.texOffs(11, 22).addBox(-0.5F, 2.5F, -1.5F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        sides = new ModelRenderer(this);
        sides.setPos(0.0F, 24.0F, 0.0F);
        sides.texOffs(13, 35).addBox(1.0F, -6.0F, -2.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        sides.texOffs(30, 31).addBox(-2.0F, -6.0F, -2.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);

        muzzle = new ModelRenderer(this);
        muzzle.setPos(0.0F, -7.0F, 0.5F);
        sides.addChild(muzzle);
        muzzle.texOffs(29, 19).addBox(-1.0F, 2.0F, -4.5F, 2.0F, 2.0F, 5.0F, 0.0F, false);

        bb_main = new ModelRenderer(this);
        bb_main.setPos(0.0F, 24.0F, 0.0F);
        bb_main.texOffs(0, 10).addBox(3.0F, -9.0F, -4.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);
        bb_main.texOffs(0, 0).addBox(-4.0F, -9.0F, -4.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);
        bb_main.texOffs(17, 31).addBox(-3.0F, -8.0F, -1.0F, 6.0F, 1.0F, 2.0F, 0.0F, false);
        bb_main.texOffs(0, 30).addBox(-2.0F, -7.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        bb_main.texOffs(23, 10).addBox(4.25F, -2.0F, -2.0F, 1.0F, 1.0F, 7.0F, 0.0F, false);
        bb_main.texOffs(19, 22).addBox(-5.25F, -2.0F, -2.0F, 1.0F, 1.0F, 7.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(-4.5F, -8.0F, -3.5F);
        bb_main.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.3491F, 0.0F, 0.0F);
        cube_r1.texOffs(24, 35).addBox(8.5F, 0.0F, 0.5F, 1.0F, 7.0F, 1.0F, 0.0F, false);
        cube_r1.texOffs(40, 9).addBox(-0.5F, 0.0F, 0.5F, 1.0F, 7.0F, 1.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(-4.5F, -8.0F, 3.5F);
        bb_main.addChild(cube_r2);
        setRotationAngle(cube_r2, -0.3491F, 0.0F, 0.0F);
        cube_r2.texOffs(0, 36).addBox(8.5F, 0.0F, -1.5F, 1.0F, 7.0F, 1.0F, 0.0F, false);
        cube_r2.texOffs(5, 36).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 7.0F, 1.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(-0.5F, -12.5F, -4.0F);
        bb_main.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, -0.6981F, 0.0F);
        cube_r3.texOffs(11, 2).addBox(-1.5F, 2.5F, -2.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(0.5F, -12.5F, -4.0F);
        bb_main.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 0.6981F, 0.0F);
        cube_r4.texOffs(11, 12).addBox(0.5F, 2.5F, -2.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);

        cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(0.5F, -12.5F, 4.0F);
        bb_main.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.0F, -0.6981F, 0.0F);
        cube_r5.texOffs(0, 20).addBox(0.5F, 2.5F, -6.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);

        cube_r6 = new ModelRenderer(this);
        cube_r6.setPos(-0.5F, -12.5F, 4.0F);
        bb_main.addChild(cube_r6);
        setRotationAngle(cube_r6, 0.0F, 0.6981F, 0.0F);
        cube_r6.texOffs(22, 0).addBox(-1.5F, 2.5F, -6.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(ArrowDrone entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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