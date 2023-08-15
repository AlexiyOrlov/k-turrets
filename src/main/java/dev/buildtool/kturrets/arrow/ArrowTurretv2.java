package dev.buildtool.kturrets.arrow;// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.buildtool.satako.Functions;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ArrowTurretv2 extends EntityModel<ArrowTurret> {
	private final ModelRenderer rotating_base;
	private final ModelRenderer rotating_gun;
	private final ModelRenderer bb_main;

	public ArrowTurretv2() {
		texWidth = 64;
		texHeight = 64;

		rotating_base = new ModelRenderer(this);
		rotating_base.setPos(0.0F, 24.0F, 0.0F);
		rotating_base.texOffs(0, 14).addBox(-5.0F, -3.0F, -5.0F, 10.0F, 2.0F, 10.0F, 0.0F, false);
		rotating_base.texOffs(36, 27).addBox(3.0F, -13.0F, -1.0F, 2.0F, 10.0F, 4.0F, 0.0F, false);
		rotating_base.texOffs(27, 27).addBox(-5.0F, -13.0F, -1.0F, 2.0F, 10.0F, 4.0F, 0.0F, false);

		rotating_gun = new ModelRenderer(this);
		rotating_gun.setPos(0.0F, -10.0F, -0.5F);
		rotating_base.addChild(rotating_gun);
		rotating_gun.texOffs(0, 5).addBox(1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		rotating_gun.texOffs(0, 27).addBox(-1.0F, -1.0F, -6.5F, 2.0F, 2.0F, 11.0F, 0.0F, false);
		rotating_gun.texOffs(0, 0).addBox(-3.0F, -1.0F, -0.5F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		rotating_gun.texOffs(0, 0).addBox(-1.0F, 1.0F, 3.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);
		rotating_gun.texOffs(0, 0).addBox(-3.0F, -1.0F, 3.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);
		rotating_gun.texOffs(0, 0).addBox(1.0F, -1.0F, 3.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);
		rotating_gun.texOffs(0, 0).addBox(-1.0F, -3.0F, 3.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);

		bb_main = new ModelRenderer(this);
		bb_main.setPos(0.0F, 24.0F, 0.0F);
		bb_main.texOffs(0, 0).addBox(-6.0F, -1.0F, -6.0F, 12.0F, 1.0F, 12.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(ArrowTurret entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		rotating_base.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
		rotating_gun.xRot = Functions.getDefaultHeadPitch(headPitch);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		rotating_base.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}