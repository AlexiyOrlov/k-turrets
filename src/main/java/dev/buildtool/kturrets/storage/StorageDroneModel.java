package dev.buildtool.kturrets.storage;// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.buildtool.kturrets.KTurrets;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class StorageDroneModel extends EntityModel<StorageDrone> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(KTurrets.ID, "storagedronemodel"), "main");
    private final ModelPart rotor1;
    private final ModelPart rotor2;
    private final ModelPart rotor3;
    private final ModelPart rotor4;
    private final ModelPart bb_main;

    public StorageDroneModel(ModelPart root) {
        this.rotor1 = root.getChild("rotor1");
        this.rotor2 = root.getChild("rotor2");
        this.rotor3 = root.getChild("rotor3");
        this.rotor4 = root.getChild("rotor4");
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rotor1 = partdefinition.addOrReplaceChild("rotor1", CubeListBuilder.create(), PartPose.offset(-6.5F, 12.5F, -5.5F));

        PartDefinition cube_r1 = rotor1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(21, 20).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition rotor2 = partdefinition.addOrReplaceChild("rotor2", CubeListBuilder.create(), PartPose.offset(-6.5F, 12.5F, 8.5F));

        PartDefinition cube_r2 = rotor2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(34, 27).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition rotor3 = partdefinition.addOrReplaceChild("rotor3", CubeListBuilder.create(), PartPose.offset(6.5F, 12.5F, 8.5F));

        PartDefinition cube_r3 = rotor3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 37).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition rotor4 = partdefinition.addOrReplaceChild("rotor4", CubeListBuilder.create(), PartPose.offset(6.5F, 12.5F, -5.5F));

        PartDefinition cube_r4 = rotor4.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(21, 27).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.0F, -4.0F, 10.0F, 8.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(-3.0F, -8.0F, -8.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(39, 34).addBox(4.0F, -2.0F, -6.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(39, 40).addBox(4.0F, -2.0F, 5.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(13, 41).addBox(-6.0F, -2.0F, -6.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(24, 41).addBox(-6.0F, -2.0F, 5.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r5 = bb_main.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(26, 34).addBox(-1.0F, -1.0F, -4.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -10.0F, 9.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r6 = bb_main.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(34, 20).addBox(0.0F, -1.0F, -4.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -10.0F, 9.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r7 = bb_main.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(13, 34).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -10.0F, -6.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r8 = bb_main.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 30).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -10.0F, -6.0F, 0.0F, -0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(StorageDrone entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}