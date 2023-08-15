package dev.buildtool.kturrets.firecharge;// Made with Blockbench 4.7.4
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
	private final ModelRenderer arrow;
	private final ModelRenderer arrow2;
	private final ModelRenderer arrow3;
	private final ModelRenderer bone;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cube_r6;

	public FirechargeTurretv2() {
		texWidth = 64;
		texHeight = 64;

		rotating = new ModelRenderer(this);
		rotating.setPos(0.0F, 17.0F, 0.25F);
		rotating.texOffs(17, 18).addBox(-2.0F, -6.75F, -1.25F, 4.0F, 4.0F, 4.0F, 0.0F, false);
		rotating.texOffs(0, 16).addBox(-0.5F, -4.25F, -8.25F, 1.0F, 1.0F, 7.0F, 0.0F, false);
		rotating.texOffs(12, 0).addBox(-0.5F, -6.25F, -8.25F, 1.0F, 1.0F, 7.0F, 0.0F, false);
		rotating.texOffs(10, 9).addBox(0.5F, -5.25F, -8.25F, 1.0F, 1.0F, 7.0F, 0.0F, false);
		rotating.texOffs(0, 7).addBox(-1.5F, -5.25F, -8.25F, 1.0F, 1.0F, 7.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(0.0F, 2.25F, 1.75F);
		rotating.addChild(cube_r1);
		setRotationAngle(cube_r1, -0.3491F, 0.0F, 0.0F);
		cube_r1.texOffs(22, 0).addBox(-1.0F, -5.25F, -3.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		arrow = new ModelRenderer(this);
		arrow.setPos(2.5F, -4.75F, 1.0833F);
		rotating.addChild(arrow);
		setRotationAngle(arrow, 0.0F, 0.2618F, 0.0F);
		arrow.texOffs(39, 6).addBox(-0.5F, 0.5F, -0.0833F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		arrow.texOffs(39, 6).addBox(-0.5F, -1.5F, -0.0833F, 1.0F, 1.0F, 4.0F, 0.0F, false);
		arrow.texOffs(42, 22).addBox(-0.5F, -0.5F, -1.0833F, 1.0F, 1.0F, 5.0F, 0.0F, false);

		arrow2 = new ModelRenderer(this);
		arrow2.setPos(-2.5F, -4.75F, 1.0833F);
		rotating.addChild(arrow2);
		setRotationAngle(arrow2, 0.0F, -0.2618F, 0.0F);
		arrow2.texOffs(39, 6).addBox(-0.5F, 0.5F, -0.0833F, 1.0F, 1.0F, 4.0F, 0.0F, true);
		arrow2.texOffs(39, 6).addBox(-0.5F, -1.5F, -0.0833F, 1.0F, 1.0F, 4.0F, 0.0F, true);
		arrow2.texOffs(42, 22).addBox(-0.5F, -0.5F, -1.0833F, 1.0F, 1.0F, 5.0F, 0.0F, true);

		arrow3 = new ModelRenderer(this);
		arrow3.setPos(0.0F, -9.75F, 2.0833F);
		rotating.addChild(arrow3);
		setRotationAngle(arrow3, 0.0F, -0.2618F, 1.5708F);
		arrow3.texOffs(39, 13).addBox(1.5F, 0.5F, -1.8333F, 1.0F, 1.0F, 4.0F, 0.0F, true);
		arrow3.texOffs(39, 13).addBox(1.5F, -1.5F, -1.8333F, 1.0F, 1.0F, 4.0F, 0.0F, true);
		arrow3.texOffs(42, 30).addBox(1.5F, -0.5F, -2.8333F, 1.0F, 1.0F, 5.0F, 0.0F, true);

		bone = new ModelRenderer(this);
		bone.setPos(0.0F, 24.0F, 0.0F);
		bone.texOffs(0, 0).addBox(-2.0F, -7.0F, -2.0F, 4.0F, 1.0F, 5.0F, 0.0F, false);
		bone.texOffs(20, 9).addBox(-2.0F, -1.0F, -6.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(-4.0F, -0.5F, 4.0F);
		bone.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, -0.6545F, 0.0F);
		cube_r2.texOffs(20, 9).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(4.0F, -0.5F, 4.0F);
		bone.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 0.6545F, 0.0F);
		cube_r3.texOffs(20, 9).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setPos(-2.2071F, -2.75F, 2.7071F);
		bone.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.3927F, 0.7854F, 0.6545F);
		cube_r4.texOffs(0, 25).addBox(-1.5F, -3.5F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setPos(2.2071F, -2.75F, 2.7071F);
		bone.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.3927F, -0.7854F, -0.6545F);
		cube_r5.texOffs(7, 25).addBox(0.5F, -3.5F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setPos(0.0F, -3.0F, -1.5F);
		bone.addChild(cube_r6);
		setRotationAngle(cube_r6, -0.3054F, 0.0F, 0.0F);
		cube_r6.texOffs(14, 27).addBox(-1.0F, -3.5F, -1.5F, 2.0F, 7.0F, 1.0F, 0.0F, false);
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