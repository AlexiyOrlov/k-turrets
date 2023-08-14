package dev.buildtool.kturrets.cobble;// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.buildtool.satako.Functions;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class CobbleDroneModel extends EntityModel<CobbleDrone> {
    private final ModelRenderer rotor1;
    private final ModelRenderer rotor2;
    private final ModelRenderer rotor3;
    private final ModelRenderer rotor4;
    private final ModelRenderer rotating;
    private final ModelRenderer muzzle;
    private final ModelRenderer bb_main;
    private final ModelRenderer cube_r1;

    public CobbleDroneModel() {
        texWidth = 64;
        texHeight = 64;

        rotor1 = new ModelRenderer(this);
        rotor1.setPos(4.5F, 10.5F, 5.5F);
        rotor1.texOffs(27, 29).addBox(-0.5F, 2.5F, -1.5F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        rotor2 = new ModelRenderer(this);
        rotor2.setPos(4.5F, 10.5F, -5.5F);
        rotor2.texOffs(18, 29).addBox(-0.5F, 2.5F, -1.5F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        rotor3 = new ModelRenderer(this);
        rotor3.setPos(-4.5F, 10.5F, -5.5F);
        rotor3.texOffs(9, 29).addBox(-0.5F, 2.5F, -1.5F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        rotor4 = new ModelRenderer(this);
        rotor4.setPos(-4.5F, 10.5F, 5.5F);
        rotor4.texOffs(0, 29).addBox(-0.5F, 2.5F, -1.5F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        rotating = new ModelRenderer(this);
        rotating.setPos(0.0F, 24.0F, 0.0F);
        rotating.texOffs(28, 10).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        muzzle = new ModelRenderer(this);
        muzzle.setPos(0.0F, -4.0F, 0.25F);
        rotating.addChild(muzzle);
        muzzle.texOffs(37, 13).addBox(-1.0F, -1.0F, -4.25F, 2.0F, 2.0F, 5.0F, 0.0F, false);

        bb_main = new ModelRenderer(this);
        bb_main.setPos(0.0F, 24.0F, 0.0F);
        bb_main.texOffs(0, 12).addBox(5.0F, -10.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);
        bb_main.texOffs(0, 0).addBox(-6.0F, -10.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);
        bb_main.texOffs(23, 26).addBox(-5.0F, -10.0F, -6.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(0, 26).addBox(-5.0F, -10.0F, 5.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(21, 22).addBox(-5.0F, -8.0F, -1.0F, 10.0F, 1.0F, 2.0F, 0.0F, false);
        bb_main.texOffs(39, 0).addBox(5.0F, -2.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);
        bb_main.texOffs(39, 0).addBox(-6.0F, -2.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(5.5F, -5.5F, 0.0F);
        bb_main.addChild(cube_r1);
        setRotationAngle(cube_r1, -0.6981F, 0.0F, 0.0F);
        cube_r1.texOffs(0, 35).addBox(-0.25F, -4.5F, -1.0F, 1.0F, 10.0F, 1.0F, 0.0F, false);
        cube_r1.texOffs(0, 35).addBox(-11.75F, -4.5F, -1.0F, 1.0F, 10.0F, 1.0F, 0.0F, true);
    }

    @Override
    public void setupAnim(CobbleDrone entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        rotating.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
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
        rotating.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}