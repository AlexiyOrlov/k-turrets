package dev.buildtool.kturrets.bullet;// Made with Blockbench 4.2.2
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
public class BulletTurretv2<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(KTurrets.ID, "bullet_turret"), "main");
    private final ModelPart bone;
    private final ModelPart rotating;
    private final ModelPart bb_main;

    public BulletTurretv2(ModelPart root) {
        this.bone = root.getChild("bone");
        this.rotating = root.getChild("rotating");
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition rotating = partdefinition.addOrReplaceChild("rotating", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(10, 42).addBox(-2.0F, -10.0F, -2.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(28, 24).addBox(-2.0F, -11.0F, -1.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 34).addBox(-4.0F, -10.0F, -1.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(22, 36).addBox(-6.0F, -9.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 32).addBox(2.0F, -10.0F, -1.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(9, 26).addBox(4.0F, -9.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 30).addBox(-0.5F, -8.5F, -5.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(34, 16).addBox(-1.0F, -9.0F, -8.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(28, 10).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-3.0F, -4.0F, -2.0F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(2.0F, -12.0F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -12.0F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, 0.0F));

        PartDefinition cube_r1 = rotating.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 26).addBox(-5.5F, -0.5F, -4.5F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-0.5F, -0.5F, -4.5F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -12.5F, 1.5F, -0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r2 = rotating.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(18, 12).addBox(-0.5F, -3.0F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, -8.0F, 1.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r3 = rotating.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(18, 24).addBox(-0.5F, -3.0F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, -8.0F, 1.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        rotating.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
        rotating.xRot = Functions.getDefaultHeadPitch(headPitch);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rotating.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}