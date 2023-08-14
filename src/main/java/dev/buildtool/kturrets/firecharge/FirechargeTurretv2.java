package dev.buildtool.kturrets.firecharge;// Made with Blockbench 4.2.3
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.buildtool.satako.Functions;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class FirechargeTurretv2 extends EntityModel<FireballTurret> {
    private final ModelRenderer rotating;
    private final ModelRenderer cube_r1;
    private final ModelRenderer bone;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;
    private final ModelRenderer cube_r4;

    public FirechargeTurretv2() {
        texWidth = 64;
        texHeight = 64;

        rotating = new ModelRenderer(this);
        rotating.setPos(0.0F, 17.0F, 1.25F);
        rotating.texOffs(17, 18).addBox(-2.0F, -6.75F, -1.25F, 4.0F, 4.0F, 4.0F, 0.0F, false);
        rotating.texOffs(0, 16).addBox(-0.5F, -4.0F, -8.25F, 1.0F, 1.0F, 7.0F, 0.0F, false);
        rotating.texOffs(12, 0).addBox(-0.5F, -6.5F, -8.25F, 1.0F, 1.0F, 7.0F, 0.0F, false);
        rotating.texOffs(10, 9).addBox(0.75F, -5.25F, -8.25F, 1.0F, 1.0F, 7.0F, 0.0F, false);
        rotating.texOffs(0, 7).addBox(-1.75F, -5.25F, -8.25F, 1.0F, 1.0F, 7.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(0.0F, 2.25F, 0.75F);
        rotating.addChild(cube_r1);
        setRotationAngle(cube_r1, -0.3491F, 0.0F, 0.0F);
        cube_r1.texOffs(22, 0).addBox(-1.0F, -5.25F, -2.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);

        bone = new ModelRenderer(this);
        bone.setPos(0.0F, 24.0F, 0.0F);
        bone.texOffs(0, 0).addBox(-2.0F, -7.0F, -2.0F, 4.0F, 1.0F, 5.0F, 0.0F, false);
        bone.texOffs(20, 9).addBox(-2.0F, -1.0F, -6.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(-2.2071F, -2.75F, 2.7071F);
        bone.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.3927F, 0.7854F, 0.6545F);
        cube_r2.texOffs(0, 25).addBox(-1.5F, -3.5F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(2.2071F, -2.75F, 2.7071F);
        bone.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.3927F, -0.7854F, -0.6545F);
        cube_r3.texOffs(7, 25).addBox(0.5F, -3.5F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);

        cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(0.0F, -3.0F, -1.5F);
        bone.addChild(cube_r4);
        setRotationAngle(cube_r4, -0.3054F, 0.0F, 0.0F);
        cube_r4.texOffs(14, 27).addBox(-1.0F, -3.5F, -1.5F, 2.0F, 7.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(FireballTurret entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        rotating.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
        rotating.xRot = Functions.getDefaultHeadPitch(headPitch);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        rotating.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        bone.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}