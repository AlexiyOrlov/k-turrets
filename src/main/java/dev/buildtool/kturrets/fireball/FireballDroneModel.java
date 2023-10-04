package dev.buildtool.kturrets.fireball;// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.buildtool.satako.Functions;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class FireballDroneModel extends EntityModel<FireballDrone> {
    private final ModelRenderer beam;
    private final ModelRenderer cube_r1;
    private final ModelRenderer beam2;
    private final ModelRenderer cube_r2;
    private final ModelRenderer beam3;
    private final ModelRenderer cube_r3;
    private final ModelRenderer beam4;
    private final ModelRenderer cube_r4;
    private final ModelRenderer rotor1;
    private final ModelRenderer rotor2;
    private final ModelRenderer rotor3;
    private final ModelRenderer rotor4;
    private final ModelRenderer rotating;
    private final ModelRenderer barrel;
    private final ModelRenderer bb_main;
    private final ModelRenderer cube_r5;
    private final ModelRenderer cube_r6;

    public FireballDroneModel() {
        texWidth = 64;
        texHeight = 64;

        beam = new ModelRenderer(this);
        beam.setPos(0.0F, 24.0F, 0.0F);
        beam.texOffs(30, 16).addBox(3.0F, -15.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(1.5F, -11.0F, 1.5F);
        beam.addChild(cube_r1);
        setRotationAngle(cube_r1, -0.7854F, 0.7854F, 0.0F);
        cube_r1.texOffs(13, 29).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);

        beam2 = new ModelRenderer(this);
        beam2.setPos(0.0F, 24.0F, 0.0F);
        beam2.texOffs(30, 13).addBox(-4.0F, -15.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(-1.5F, -11.0F, 1.5F);
        beam2.addChild(cube_r2);
        setRotationAngle(cube_r2, -0.7854F, -0.7854F, 0.0F);
        cube_r2.texOffs(28, 0).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);

        beam3 = new ModelRenderer(this);
        beam3.setPos(0.0F, 24.0F, 0.0F);
        beam3.texOffs(30, 6).addBox(-4.0F, -15.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(-1.5F, -11.0F, -1.5F);
        beam3.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.7854F, 0.7854F, 0.0F);
        cube_r3.texOffs(0, 15).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);

        beam4 = new ModelRenderer(this);
        beam4.setPos(0.0F, 24.0F, 0.0F);
        beam4.texOffs(28, 29).addBox(3.0F, -15.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(1.5F, -11.0F, -1.5F);
        beam4.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.7854F, -0.7854F, 0.0F);
        cube_r4.texOffs(0, 0).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, 0.0F, false);

        rotor1 = new ModelRenderer(this);
        rotor1.setPos(3.5F, 8.5F, 3.5F);
        rotor1.texOffs(0, 25).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, false);

        rotor2 = new ModelRenderer(this);
        rotor2.setPos(-3.5F, 8.5F, 3.5F);
        rotor2.texOffs(23, 22).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, false);

        rotor3 = new ModelRenderer(this);
        rotor3.setPos(-3.5F, 8.5F, -3.5F);
        rotor3.texOffs(10, 22).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, false);

        rotor4 = new ModelRenderer(this);
        rotor4.setPos(3.5F, 8.5F, -3.5F);
        rotor4.texOffs(22, 6).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, false);

        rotating = new ModelRenderer(this);
        rotating.setPos(0.0F, 15.0F, 0.0F);
        rotating.texOffs(18, 22).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        barrel = new ModelRenderer(this);
        barrel.setPos(0.0F, 2.0F, 0.0F);
        rotating.addChild(barrel);
        barrel.texOffs(0, 17).addBox(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 5.0F, 0.0F, false);

        bb_main = new ModelRenderer(this);
        bb_main.setPos(0.0F, 24.0F, 0.0F);
        bb_main.texOffs(11, 0).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
        bb_main.texOffs(23, 29).addBox(5.0F, -5.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(18, 29).addBox(5.0F, -5.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(0, 25).addBox(-6.0F, -5.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(22, 6).addBox(-6.0F, -5.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(2.0F, -10.0F, 0.0F);
        bb_main.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.0F, 0.0F, -0.5672F);
        cube_r5.texOffs(0, 0).addBox(0.0F, 0.0F, -4.0F, 1.0F, 6.0F, 8.0F, 0.0F, false);

        cube_r6 = new ModelRenderer(this);
        cube_r6.setPos(-2.0F, -10.0F, 0.0F);
        bb_main.addChild(cube_r6);
        setRotationAngle(cube_r6, 0.0F, 0.0F, 0.5672F);
        cube_r6.texOffs(11, 7).addBox(-1.0F, 0.0F, -4.0F, 1.0F, 6.0F, 8.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(FireballDrone entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        rotating.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
        barrel.xRot = Functions.getDefaultHeadPitch(headPitch);
        rotor1.yRot = ageInTicks * 1.5f;
        rotor2.yRot = -ageInTicks * 1.5f;
        rotor3.yRot = ageInTicks * 1.5f;
        rotor4.yRot = -ageInTicks * 1.5f;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        beam.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        beam2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        beam3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        beam4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rotating.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}