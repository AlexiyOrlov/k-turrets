package dev.buildtool.kturrets.brick;// Made with Blockbench 4.2.4
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.satako.Functions;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class BrickDroneModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(KTurrets.ID, "brick_drone"), "main");
    private final ModelPart rotor1;
    private final ModelPart base;
    private final ModelPart rotor2;
    private final ModelPart rotor3;
    private final ModelPart rotor4;
    private final ModelPart sides;
    private final ModelPart muzzle;

    public BrickDroneModel(ModelPart root) {
        this.rotor1 = root.getChild("rotor1");
        this.base = root.getChild("base");
        this.rotor2 = root.getChild("rotor2");
        this.rotor3 = root.getChild("rotor3");
        this.rotor4 = root.getChild("rotor4");
        this.sides = root.getChild("sides");
        muzzle = sides.getChild("muzzle");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rotor1 = partdefinition.addOrReplaceChild("rotor1", CubeListBuilder.create().texOffs(0, 20).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, 14.5F, 6.0F));

        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(17, 14).addBox(-4.0F, -9.0F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-6.0F, -9.0F, -6.0F, 2.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(4.0F, -9.0F, -6.0F, 2.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(17, 0).addBox(-2.0F, -8.0F, -3.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = base.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -6.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 28).addBox(-11.0F, -6.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -4.0F, 5.5F, 0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r2 = base.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(10, 28).addBox(-11.0F, -6.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 28).addBox(-1.0F, -6.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, -4.0F, -5.5F, -0.6981F, 0.0F, 0.0F));

        PartDefinition rotor2 = partdefinition.addOrReplaceChild("rotor2", CubeListBuilder.create().texOffs(0, 14).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.5F, 14.5F, 6.0F));

        PartDefinition rotor3 = partdefinition.addOrReplaceChild("rotor3", CubeListBuilder.create().texOffs(0, 6).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.5F, 14.5F, -6.0F));

        PartDefinition rotor4 = partdefinition.addOrReplaceChild("rotor4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, 14.5F, -6.0F));

        PartDefinition sides = partdefinition.addOrReplaceChild("sides", CubeListBuilder.create().texOffs(27, 31).addBox(1.0F, -1.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 31).addBox(-2.0F, -1.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition muzzle = sides.addOrReplaceChild("muzzle", CubeListBuilder.create().texOffs(23, 22).addBox(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        sides.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
        muzzle.xRot = Functions.getDefaultHeadPitch(headPitch);
        rotor1.yRot = ageInTicks * 1.5f;
        rotor2.yRot = -ageInTicks * 1.5f;
        rotor3.yRot = ageInTicks * 1.5f;
        rotor4.yRot = -ageInTicks * 1.5f;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        rotor1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        sides.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}