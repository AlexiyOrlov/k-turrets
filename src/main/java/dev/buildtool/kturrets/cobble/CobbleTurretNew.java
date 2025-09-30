package dev.buildtool.kturrets.cobble;// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.buildtool.satako.Functions;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class CobbleTurretNew<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "cobbleturretv2"), "main");
	private final ModelPart rotleftright;
	private final ModelPart rotupdown;
	private final ModelPart base;

	public CobbleTurretNew(ModelPart root) {
		this.rotleftright = root.getChild("rotleftright");
		this.rotupdown = this.rotleftright.getChild("rotupdown");
		this.base = root.getChild("base");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition rotleftright = partdefinition.addOrReplaceChild("rotleftright", CubeListBuilder.create().texOffs(30, 35).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(30, 35).addBox(-2.0F, -8.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, 0.0F));

		PartDefinition rotupdown = rotleftright.addOrReplaceChild("rotupdown", CubeListBuilder.create().texOffs(0, 24).addBox(-3.0F, -6.0F, -3.9167F, 6.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(34, 0).addBox(-4.0F, -5.0F, 5.0833F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-7.0F, -8.0F, -4.9167F, 6.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(12, 43).addBox(-1.0F, -9.0F, -0.9167F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 12).addBox(1.0F, -8.0F, -4.9167F, 6.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(34, 8).addBox(-2.0F, -5.0F, -8.9167F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, -0.0833F));

		PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(30, 24).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(34, 17).addBox(-2.0F, -2.0F, -6.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(34, 21).addBox(-2.0F, -1.0F, -8.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = base.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(34, 45).addBox(-2.0F, -1.0F, -8.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(12, 39).addBox(-2.0F, -2.0F, -6.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r2 = base.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(22, 45).addBox(-2.0F, -1.0F, -8.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 39).addBox(-2.0F, -2.0F, -6.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r3 = base.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 43).addBox(-2.0F, -2.0F, -6.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(46, 17).addBox(-2.0F, -1.0F, -8.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		rotleftright.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
		rotupdown.xRot = Functions.getDefaultHeadPitch(headPitch);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		rotleftright.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}