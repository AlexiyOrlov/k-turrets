package dev.buildtool.kturrets.gauss;
// Made with Blockbench 4.2.3
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

public class GaussTurretModelv2<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(KTurrets.ID, "gaussturret"), "main");
    private final ModelPart turret_base;
    private final ModelPart turret_angler;

    public GaussTurretModelv2(ModelPart root) {
        this.turret_base = root.getChild("turret_base");
        turret_angler = root.getChild("turret_angler");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition turret_base = partdefinition.addOrReplaceChild("turret_base", CubeListBuilder.create().texOffs(0, 12).addBox(-2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, 1.0F));

        PartDefinition turret_angler = turret_base.addOrReplaceChild("turret_angler", CubeListBuilder.create().texOffs(29, 23).addBox(-1.5F, -3.0F, -2.5F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition turret_piece = turret_angler.addOrReplaceChild("turret_piece", CubeListBuilder.create().texOffs(20, 6).addBox(-2.0F, -1.0F, -3.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition turret_neck = turret_piece.addOrReplaceChild("turret_neck", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r1 = turret_neck.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 30).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition turret_head = turret_neck.addOrReplaceChild("turret_head", CubeListBuilder.create().texOffs(16, 16).addBox(-1.5F, -3.5F, -1.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -3.0F, -4.0F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 2.5F));

        PartDefinition turret_barrel = turret_head.addOrReplaceChild("turret_barrel", CubeListBuilder.create().texOffs(15, 28).addBox(-1.5F, -2.0F, -4.0F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(-2.5F, -1.0F, -8.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(28, 14).addBox(-1.0F, 0.0F, -12.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -4.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        turret_angler.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
        turret_angler.xRot = Functions.getDefaultHeadPitch(headPitch) - 0.2618f;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        turret_base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}