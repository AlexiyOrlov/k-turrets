package dev.buildtool.kturrets.arrow;

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

public class ArrowTurretModelNew<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "arrowturretv1"), "main");
	private  ModelPart rotleftright;
	private  ModelPart rotupdown;
	private  ModelPart base;

	public ArrowTurretModelNew(ModelPart root) {
		this.rotleftright = root.getChild("rotleftright");
		this.rotupdown = this.rotleftright.getChild("rotupdown");
		this.base = root.getChild("base");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition rotleftright = partdefinition.addOrReplaceChild("rotleftright", CubeListBuilder.create().texOffs(0, 40).addBox(-6.0F, -2.8475F, -6.2402F, 12.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.8475F, 0.2402F));

		PartDefinition cube_r1 = rotleftright.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(48, 45).addBox(-1.0F, -7.5F, -2.0F, 2.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(48, 29).addBox(-7.0F, -7.5F, -2.0F, 2.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -5.3475F, 0.7598F, -0.6109F, 0.0F, 0.0F));

		PartDefinition rotupdown = rotleftright.addOrReplaceChild("rotupdown", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -3.0F, -11.0F, 4.0F, 6.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(0, 54).addBox(-2.0F, -6.0F, -7.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(44, 16).addBox(-1.0F, -8.0F, -3.0F, 2.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.8475F, 3.7598F));

		PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -2.0F, -7.0F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

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