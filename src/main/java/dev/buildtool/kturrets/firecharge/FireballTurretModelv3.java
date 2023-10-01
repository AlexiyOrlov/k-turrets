package dev.buildtool.kturrets.firecharge;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
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

public class FireballTurretModelv3<T extends FireballTurret> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(KTurrets.ID, "fireball_turret"), "main");
    private final ModelPart rotating;
    private final ModelPart bb_main;

    public FireballTurretModelv3(ModelPart root) {
        this.rotating = root.getChild("rotating");
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rotating = partdefinition.addOrReplaceChild("rotating", CubeListBuilder.create().texOffs(25, 12).addBox(-2.0F, -5.75F, -2.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(-1.0F, -4.75F, -8.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(23, 32).addBox(2.0F, -11.75F, 0.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 32).addBox(-3.0F, -11.75F, 0.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.75F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(9, 32).addBox(4.0F, -5.0F, -6.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-6.0F, -5.0F, -6.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(35, 23).addBox(-6.0F, -5.0F, 4.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(26, 23).addBox(4.0F, -5.0F, 4.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(41, 0).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(17, 23).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        rotating.xRot = Functions.getDefaultHeadPitch(headPitch);
        rotating.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        rotating.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}