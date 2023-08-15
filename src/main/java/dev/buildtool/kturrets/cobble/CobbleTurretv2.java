package dev.buildtool.kturrets.cobble;// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.buildtool.satako.Functions;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class CobbleTurretv2 extends EntityModel<CobbleTurret> {
	private final ModelRenderer bone;
	private final ModelRenderer bone2;
	private final ModelRenderer bone3;
	private final ModelRenderer bone4;
	private final ModelRenderer rotating;
	private final ModelRenderer bb_main;

	public CobbleTurretv2() {
		texWidth = 64;
		texHeight = 64;

		bone = new ModelRenderer(this);
		bone.setPos(0.0F, 24.0F, 0.0F);
		bone.texOffs(30, 12).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 2.0F, 1.0F, 0.0F, false);
		bone.texOffs(28, 5).addBox(-1.0F, -1.0F, -7.0F, 2.0F, 1.0F, 3.0F, 0.0F, false);

		bone2 = new ModelRenderer(this);
		bone2.setPos(0.0F, 24.0F, 0.0F);
		bone2.texOffs(30, 9).addBox(-2.0F, -2.0F, 3.0F, 4.0F, 2.0F, 1.0F, 0.0F, false);
		bone2.texOffs(21, 6).addBox(-1.0F, -1.0F, 4.0F, 2.0F, 1.0F, 3.0F, 0.0F, false);

		bone3 = new ModelRenderer(this);
		bone3.setPos(0.0F, 24.0F, 0.0F);
		bone3.texOffs(26, 26).addBox(3.0F, -2.0F, -2.0F, 1.0F, 2.0F, 4.0F, 0.0F, false);
		bone3.texOffs(8, 30).addBox(4.0F, -1.0F, -1.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);

		bone4 = new ModelRenderer(this);
		bone4.setPos(0.0F, 24.0F, 0.0F);
		bone4.texOffs(16, 26).addBox(-4.0F, -2.0F, -2.0F, 1.0F, 2.0F, 4.0F, 0.0F, false);
		bone4.texOffs(30, 0).addBox(-7.0F, -1.0F, -1.0F, 3.0F, 1.0F, 2.0F, 0.0F, false);

		rotating = new ModelRenderer(this);
		rotating.setPos(0.0F, 20.0F, 0.0F);
		rotating.texOffs(0, 27).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		rotating.texOffs(0, 17).addBox(-2.0F, -8.0F, -2.0F, 4.0F, 4.0F, 6.0F, 0.0F, false);
		rotating.texOffs(14, 18).addBox(-1.5F, -6.25F, 4.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);
		rotating.texOffs(8, 27).addBox(-2.0F, -6.0F, -3.0F, 4.0F, 2.0F, 1.0F, 0.0F, false);
		rotating.texOffs(20, 18).addBox(-1.0F, -7.0F, -8.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
		rotating.texOffs(0, 0).addBox(-1.0F, -5.0F, -4.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		rotating.texOffs(22, 26).addBox(-1.0F, -10.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		rotating.texOffs(15, 10).addBox(-5.0F, -10.0F, -2.0F, 4.0F, 1.0F, 7.0F, 0.0F, false);
		rotating.texOffs(0, 9).addBox(1.0F, -10.0F, -2.0F, 4.0F, 1.0F, 7.0F, 0.0F, false);

		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 3.0F, 6.0F, 0.0F, false);
		bb_main.texOffs(18, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(CobbleTurret entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		rotating.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
		rotating.xRot = Functions.getDefaultHeadPitch(headPitch);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bone2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bone3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bone4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		rotating.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}