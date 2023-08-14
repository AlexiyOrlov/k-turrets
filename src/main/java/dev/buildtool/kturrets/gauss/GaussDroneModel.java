package dev.buildtool.kturrets.gauss;// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.buildtool.satako.Functions;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class GaussDroneModel extends EntityModel<GaussDrone> {
    private final ModelRenderer rotor1;
    private final ModelRenderer rotor2;
    private final ModelRenderer rotor3;
    private final ModelRenderer rotor4;
    private final ModelRenderer sides;
    private final ModelRenderer muzzle;
    private final ModelRenderer bb_main;

    public GaussDroneModel() {
        texWidth = 64;
        texHeight = 64;

        rotor1 = new ModelRenderer(this);
        rotor1.setPos(-2.5F, 12.5F, 5.5F);
        rotor1.texOffs(34, 23).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        rotor2 = new ModelRenderer(this);
        rotor2.setPos(2.5F, 12.5F, 5.5F);
        rotor2.texOffs(10, 30).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        rotor3 = new ModelRenderer(this);
        rotor3.setPos(5.5F, 12.5F, -5.5F);
        rotor3.texOffs(12, 22).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        rotor4 = new ModelRenderer(this);
        rotor4.setPos(-5.5F, 12.5F, -5.5F);
        rotor4.texOffs(12, 19).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        sides = new ModelRenderer(this);
        sides.setPos(0.0F, 24.0F, 0.0F);
        sides.texOffs(17, 30).addBox(1.0F, -9.0F, -1.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);
        sides.texOffs(29, 28).addBox(-2.0F, -9.0F, -1.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);

        muzzle = new ModelRenderer(this);
        muzzle.setPos(0.0F, -6.0F, 1.25F);
        sides.addChild(muzzle);
        muzzle.texOffs(24, 6).addBox(-1.0F, -1.0F, -5.25F, 2.0F, 2.0F, 6.0F, 0.0F, false);
        muzzle.texOffs(34, 15).addBox(-1.0F, -2.0F, 0.75F, 2.0F, 4.0F, 3.0F, 0.0F, false);

        bb_main = new ModelRenderer(this);
        bb_main.setPos(0.0F, 24.0F, 0.0F);
        bb_main.texOffs(12, 19).addBox(-6.0F, -11.0F, -6.0F, 1.0F, 1.0F, 9.0F, 0.0F, false);
        bb_main.texOffs(0, 17).addBox(5.0F, -11.0F, -6.0F, 1.0F, 1.0F, 9.0F, 0.0F, false);
        bb_main.texOffs(0, 28).addBox(-3.0F, -11.0F, -1.0F, 1.0F, 1.0F, 7.0F, 0.0F, false);
        bb_main.texOffs(24, 19).addBox(2.0F, -11.0F, -1.0F, 1.0F, 1.0F, 7.0F, 0.0F, false);
        bb_main.texOffs(0, 0).addBox(-6.0F, -10.0F, -1.0F, 12.0F, 1.0F, 4.0F, 0.0F, false);
        bb_main.texOffs(35, 0).addBox(4.0F, -9.0F, 2.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(0, 17).addBox(4.0F, -9.0F, -1.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(12, 8).addBox(4.0F, -3.0F, -5.0F, 1.0F, 1.0F, 9.0F, 0.0F, false);
        bb_main.texOffs(0, 6).addBox(-5.0F, -3.0F, -5.0F, 1.0F, 1.0F, 9.0F, 0.0F, false);
        bb_main.texOffs(12, 6).addBox(-5.0F, -9.0F, -1.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        bb_main.texOffs(0, 6).addBox(-5.0F, -9.0F, 2.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(GaussDrone entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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