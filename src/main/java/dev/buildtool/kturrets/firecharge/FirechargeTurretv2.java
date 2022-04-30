package dev.buildtool.kturrets.firecharge;// Made with Blockbench 4.2.3
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
@Deprecated
public class FirechargeTurretv2<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(KTurrets.ID, "firecharge_turret"), "main");
    private final ModelPart rotating;
    private final ModelPart bone;

    public FirechargeTurretv2(ModelPart root) {
        this.rotating = root.getChild("rotating");
        this.bone = root.getChild("bone");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rotating = partdefinition.addOrReplaceChild("rotating", CubeListBuilder.create().texOffs(17, 18).addBox(-2.0F, -6.75F, -1.25F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-0.5F, -4.0F, -8.25F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(12, 0).addBox(-0.5F, -6.5F, -8.25F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(10, 9).addBox(0.75F, -5.25F, -8.25F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(-1.75F, -5.25F, -8.25F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, 1.25F));

        PartDefinition cube_r1 = rotating.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, -5.25F, -2.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.25F, 0.75F, -0.3491F, 0.0F, 0.0F));

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -7.0F, -2.0F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(20, 9).addBox(-2.0F, -1.0F, -6.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 25).addBox(-1.5F, -3.5F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.2071F, -2.75F, 2.7071F, 0.3927F, 0.7854F, 0.6545F));

        PartDefinition cube_r3 = bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(7, 25).addBox(0.5F, -3.5F, -1.0F, 1.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2071F, -2.75F, 2.7071F, 0.3927F, -0.7854F, -0.6545F));

        PartDefinition cube_r4 = bone.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(14, 27).addBox(-1.0F, -3.5F, -1.5F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -1.5F, -0.3054F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        rotating.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
        rotating.xRot = Functions.getDefaultHeadPitch(headPitch);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        rotating.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
