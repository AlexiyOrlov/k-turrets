package dev.buildtool.kturrets.brick;// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.buildtool.satako.Functions;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BrickDroneModel extends EntityModel<BrickDrone> {
    private final ModelRenderer rotor1;
    private final ModelRenderer base;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer rotor2;
    private final ModelRenderer rotor3;
    private final ModelRenderer rotor4;
    private final ModelRenderer sides;
    private final ModelRenderer muzzle;

    public BrickDroneModel() {
        texWidth = 64;
        texHeight = 64;

        rotor1 = new ModelRenderer(this);
        rotor1.setPos(5.5F, 14.5F, 6.0F);
        rotor1.texOffs(0, 20).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);

        base = new ModelRenderer(this);
        base.setPos(0.0F, 24.0F, 0.0F);
        base.texOffs(17, 14).addBox(-4.0F, -9.0F, -2.0F, 8.0F, 1.0F, 4.0F, 0.0F, false);
        base.texOffs(0, 14).addBox(-6.0F, -9.0F, -6.0F, 2.0F, 1.0F, 12.0F, 0.0F, false);
        base.texOffs(0, 0).addBox(4.0F, -9.0F, -6.0F, 2.0F, 1.0F, 12.0F, 0.0F, false);
        base.texOffs(17, 0).addBox(-2.0F, -8.0F, -3.0F, 4.0F, 3.0F, 6.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(5.5F, -4.0F, 5.5F);
        base.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.6981F, 0.0F, 0.0F);
        cube_r1.texOffs(0, 28).addBox(-1.0F, -6.0F, -0.5F, 1.0F, 10.0F, 1.0F, 0.0F, false);
        cube_r1.texOffs(5, 28).addBox(-11.0F, -6.0F, -0.5F, 1.0F, 10.0F, 1.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(5.5F, -4.0F, -5.5F);
        base.addChild(cube_r2);
        setRotationAngle(cube_r2, -0.6981F, 0.0F, 0.0F);
        cube_r2.texOffs(10, 28).addBox(-11.0F, -6.0F, -0.5F, 1.0F, 10.0F, 1.0F, 0.0F, false);
        cube_r2.texOffs(15, 28).addBox(-1.0F, -6.0F, -0.5F, 1.0F, 10.0F, 1.0F, 0.0F, false);

        rotor2 = new ModelRenderer(this);
        rotor2.setPos(-5.5F, 14.5F, 6.0F);
        rotor2.texOffs(0, 14).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);

        rotor3 = new ModelRenderer(this);
        rotor3.setPos(-5.5F, 14.5F, -6.0F);
        rotor3.texOffs(0, 6).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);

        rotor4 = new ModelRenderer(this);
        rotor4.setPos(5.5F, 14.5F, -6.0F);
        rotor4.texOffs(0, 0).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);

        sides = new ModelRenderer(this);
        sides.setPos(0.0F, 20.0F, 0.0F);
        sides.texOffs(27, 31).addBox(1.0F, -1.0F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, false);
        sides.texOffs(20, 31).addBox(-2.0F, -1.0F, -1.0F, 1.0F, 4.0F, 2.0F, 0.0F, false);

        muzzle = new ModelRenderer(this);
        muzzle.setPos(0.0F, 2.0F, 0.0F);
        sides.addChild(muzzle);
        muzzle.texOffs(23, 22).addBox(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(BrickDrone entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
        base.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        sides.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}