package dev.buildtool.kturrets.firecharge;// Made with Blockbench 4.2.4
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

public class FireballDroneModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(KTurrets.ID, "firecharge_drone"), "main");
    private final ModelPart beam;
    private final ModelPart beam2;
    private final ModelPart beam3;
    private final ModelPart beam4;
    private final ModelPart rotor1;
    private final ModelPart rotor2;
    private final ModelPart rotor3;
    private final ModelPart rotor4;
    private final ModelPart rotating;
    private final ModelPart bb_main;
    private final ModelPart barrel;

    public FireballDroneModel(ModelPart root) {
        this.beam = root.getChild("beam");
        this.beam2 = root.getChild("beam2");
        this.beam3 = root.getChild("beam3");
        this.beam4 = root.getChild("beam4");
        this.rotor1 = root.getChild("rotor1");
        this.rotor2 = root.getChild("rotor2");
        this.rotor3 = root.getChild("rotor3");
        this.rotor4 = root.getChild("rotor4");
        this.rotating = root.getChild("rotating");
        this.bb_main = root.getChild("bb_main");
        barrel = rotating.getChild("barrel");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition beam = partdefinition.addOrReplaceChild("beam", CubeListBuilder.create().texOffs(30, 16).addBox(3.0F, -15.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = beam.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(13, 29).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -11.0F, 1.5F, -0.7854F, 0.7854F, 0.0F));

        PartDefinition beam2 = partdefinition.addOrReplaceChild("beam2", CubeListBuilder.create().texOffs(30, 13).addBox(-4.0F, -15.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r2 = beam2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(28, 0).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -11.0F, 1.5F, -0.7854F, -0.7854F, 0.0F));

        PartDefinition beam3 = partdefinition.addOrReplaceChild("beam3", CubeListBuilder.create().texOffs(30, 6).addBox(-4.0F, -15.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r3 = beam3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 15).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -11.0F, -1.5F, 0.7854F, 0.7854F, 0.0F));

        PartDefinition beam4 = partdefinition.addOrReplaceChild("beam4", CubeListBuilder.create().texOffs(28, 29).addBox(3.0F, -15.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r4 = beam4.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -11.0F, -1.5F, 0.7854F, -0.7854F, 0.0F));

        PartDefinition rotor1 = partdefinition.addOrReplaceChild("rotor1", CubeListBuilder.create().texOffs(0, 25).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 8.5F, 3.5F));

        PartDefinition rotor2 = partdefinition.addOrReplaceChild("rotor2", CubeListBuilder.create().texOffs(23, 22).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 8.5F, 3.5F));

        PartDefinition rotor3 = partdefinition.addOrReplaceChild("rotor3", CubeListBuilder.create().texOffs(10, 22).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 8.5F, -3.5F));

        PartDefinition rotor4 = partdefinition.addOrReplaceChild("rotor4", CubeListBuilder.create().texOffs(22, 6).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 8.5F, -3.5F));

        PartDefinition rotating = partdefinition.addOrReplaceChild("rotating", CubeListBuilder.create().texOffs(18, 22).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 0.0F));

        PartDefinition barrel = rotating.addOrReplaceChild("barrel", CubeListBuilder.create().texOffs(0, 17).addBox(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(11, 0).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(23, 29).addBox(5.0F, -5.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 29).addBox(5.0F, -5.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 25).addBox(-6.0F, -5.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 6).addBox(-6.0F, -5.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r5 = bb_main.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -4.0F, 1.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -10.0F, 0.0F, 0.0F, 0.0F, -0.5672F));

        PartDefinition cube_r6 = bb_main.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(11, 7).addBox(-1.0F, 0.0F, -4.0F, 1.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.5672F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        rotor1.yRot = ageInTicks * 1.5f;
        rotor2.yRot = -ageInTicks * 1.5f;
        rotor3.yRot = ageInTicks * 1.5f;
        rotor4.yRot = -ageInTicks * 1.5f;
        rotating.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
        barrel.xRot = Functions.getDefaultHeadPitch(headPitch);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        beam.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        beam2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        beam3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        beam4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rotating.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}