package dev.buildtool.kturrets.bullet;// Made with Blockbench 4.12.6
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

public class BulletTurretModelNew<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "bulletturretv3"), "main");
	private final ModelPart rotleftright;
	private final ModelPart rotupdown;
	private final ModelPart base;

	public BulletTurretModelNew(ModelPart root) {
		this.rotleftright = root.getChild("rotleftright");
		this.rotupdown = this.rotleftright.getChild("rotupdown");
		this.base = root.getChild("base");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition rotleftright = partdefinition.addOrReplaceChild("rotleftright", CubeListBuilder.create().texOffs(32, 31).addBox(-6.0F, -2.0F, -2.5F, 12.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(46, 49).addBox(4.0F, -9.0F, -2.5F, 2.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 56).addBox(-6.0F, -9.0F, -2.5F, 2.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, 0.5F));

		PartDefinition rotupdown = rotleftright.addOrReplaceChild("rotupdown", CubeListBuilder.create().texOffs(0, 0).addBox(-4.2F, -3.1736F, -5.5036F, 8.0F, 7.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(2, 21).addBox(-2.2F, -6.1736F, 1.4964F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(41, 3).addBox(2.8F, -5.1736F, -4.5036F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(32, 38).addBox(-1.2F, -1.1736F, -13.5036F, 2.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.2F, -7.8264F, 0.0036F));

		PartDefinition cube_r1 = rotupdown.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(6, 21).addBox(0.0F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.2F, -5.4236F, 2.2464F, -0.829F, 0.0F, 0.0F));

		PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 18).addBox(-8.0F, -3.0F, -1.5F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(28, 49).addBox(-5.0F, -6.0F, 1.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(62, 19).addBox(-4.5F, -3.0F, 8.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(62, 25).addBox(-4.5F, -3.0F, -4.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(14, 63).addBox(-11.0F, -3.0F, 2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(58, 63).addBox(2.0F, -3.0F, 2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 24.0F, -3.5F));

		return LayerDefinition.create(meshdefinition, 74, 74);
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