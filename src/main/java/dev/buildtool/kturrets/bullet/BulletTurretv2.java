package dev.buildtool.kturrets.bullet;// Made with Blockbench 4.7.4
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
	private final ModelRenderer vertical;
	private final ModelRenderer cube_r1;
	private final ModelRenderer bb_main;

	public BulletTurretv2() {
		texWidth = 64;
		texHeight = 64;

		bone = new ModelRenderer(this);
		bone.setPos(0.0F, 24.0F, 0.0F);


		rotating = new ModelRenderer(this);
		rotating.setPos(0.0F, 21.0F, 0.0F);
		rotating.texOffs(28, 10).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
		rotating.texOffs(0, 18).addBox(-3.0F, -4.0F, -2.0F, 6.0F, 2.0F, 5.0F, 0.0F, false);
		rotating.texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		rotating.texOffs(7, 3).addBox(2.0F, -5.0F, -2.0F, 4.0F, 1.0F, 5.0F, 0.0F, false);
		rotating.texOffs(7, 3).addBox(-6.0F, -5.0F, -2.0F, 4.0F, 1.0F, 5.0F, 0.0F, false);
		rotating.texOffs(6, 54).addBox(-2.0F, -5.0F, -1.0F, 4.0F, 1.0F, 5.0F, 0.0F, false);
		rotating.texOffs(18, 12).addBox(6.0F, -11.0F, -2.0F, 1.0F, 6.0F, 6.0F, 0.0F, false);
		rotating.texOffs(18, 24).addBox(-7.0F, -11.0F, -2.0F, 1.0F, 6.0F, 6.0F, 0.0F, false);

		vertical = new ModelRenderer(this);
		vertical.setPos(0.0F, -8.0F, 1.0F);
		rotating.addChild(vertical);
		vertical.texOffs(22, 36).addBox(-6.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		vertical.texOffs(0, 34).addBox(-4.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, 0.0F, false);
		vertical.texOffs(0, 3).addBox(2.0F, -4.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		vertical.texOffs(0, 0).addBox(-3.0F, -4.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		vertical.texOffs(28, 24).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
		vertical.texOffs(10, 42).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 6.0F, 0.0F, false);
		vertical.texOffs(30, 32).addBox(2.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, 0.0F, false);
		vertical.texOffs(9, 26).addBox(4.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		vertical.texOffs(10, 30).addBox(-0.5F, -0.5F, -6.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
		vertical.texOffs(34, 16).addBox(-1.0F, -1.0F, -9.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(2.5F, -6.5F, 0.5F);
		vertical.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.829F, 0.0F, 0.0F);
		cube_r1.texOffs(32, 9).addBox(-5.5F, -2.5F, 1.5F, 1.0F, 5.0F, 1.0F, 0.0F, false);
		cube_r1.texOffs(32, 9).addBox(-0.5F, -2.5F, 1.5F, 1.0F, 5.0F, 1.0F, 0.0F, false);

		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(21, 44).addBox(-3.0F, -3.0F, -5.0F, 6.0F, 3.0F, 10.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(BulletTurret entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		rotating.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
		vertical.xRot = Functions.getDefaultHeadPitch(headPitch);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rotating.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}