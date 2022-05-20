package dev.buildtool.kturrets.arrow;// Made with Blockbench 4.2.4
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

public class ArrowDroneModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(KTurrets.ID, "arrow_drone"), "main");
    private final ModelPart rotor1;
    private final ModelPart rotor2;
    private final ModelPart rotor3;
    private final ModelPart rotor4;
    private final ModelPart sides;
    private final ModelPart bb_main;
    private final ModelPart muzzle;

    public ArrowDroneModel(ModelPart root) {
        this.rotor1 = root.getChild("rotor1");
        this.rotor2 = root.getChild("rotor2");
        this.rotor3 = root.getChild("rotor3");
        this.rotor4 = root.getChild("rotor4");
        this.sides = root.getChild("sides");
        this.bb_main = root.getChild("bb_main");
        muzzle = sides.getChild("muzzle");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rotor1 = partdefinition.addOrReplaceChild("rotor1", CubeListBuilder.create().texOffs(38, 37).addBox(-0.5F, 2.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 10.5F, -3.5F));

        PartDefinition rotor2 = partdefinition.addOrReplaceChild("rotor2", CubeListBuilder.create().texOffs(37, 27).addBox(-0.5F, 2.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 10.5F, -3.5F));

        PartDefinition rotor3 = partdefinition.addOrReplaceChild("rotor3", CubeListBuilder.create().texOffs(33, 0).addBox(-0.5F, 2.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 10.5F, 3.5F));

        PartDefinition rotor4 = partdefinition.addOrReplaceChild("rotor4", CubeListBuilder.create().texOffs(11, 22).addBox(-0.5F, 2.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 10.5F, 3.5F));

        PartDefinition sides = partdefinition.addOrReplaceChild("sides", CubeListBuilder.create().texOffs(13, 35).addBox(1.0F, -6.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(30, 31).addBox(-2.0F, -6.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition muzzle = sides.addOrReplaceChild("muzzle", CubeListBuilder.create().texOffs(29, 19).addBox(-1.0F, 2.0F, -4.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.5F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 10).addBox(3.0F, -9.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -9.0F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(17, 31).addBox(-3.0F, -8.0F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 30).addBox(-2.0F, -7.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(23, 10).addBox(4.25F, -2.0F, -2.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(19, 22).addBox(-5.25F, -2.0F, -2.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(24, 35).addBox(8.5F, 0.0F, 0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 9).addBox(-0.5F, 0.0F, 0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -8.0F, -3.5F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 36).addBox(8.5F, 0.0F, -1.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 36).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -8.0F, 3.5F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(11, 2).addBox(-1.5F, 2.5F, -2.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -12.5F, -4.0F, 0.0F, -0.6981F, 0.0F));

        PartDefinition cube_r4 = bb_main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(11, 12).addBox(0.5F, 2.5F, -2.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -12.5F, -4.0F, 0.0F, 0.6981F, 0.0F));

        PartDefinition cube_r5 = bb_main.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 20).addBox(0.5F, 2.5F, -6.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -12.5F, 4.0F, 0.0F, -0.6981F, 0.0F));

        PartDefinition cube_r6 = bb_main.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(22, 0).addBox(-1.5F, 2.5F, -6.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -12.5F, 4.0F, 0.0F, 0.6981F, 0.0F));

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
        rotor2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rotor4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        sides.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}