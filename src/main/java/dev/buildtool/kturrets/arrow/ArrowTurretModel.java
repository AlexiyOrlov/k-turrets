// Made with Blockbench 3.9.3
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
package dev.buildtool.kturrets.arrow;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.buildtool.satako.Functions;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ArrowTurretModel extends EntityModel<ArrowTurret> {
    private final ModelRenderer moveable_gun;
    private final ModelRenderer base;
    private final ModelRenderer support4_r1;
    private final ModelRenderer support3_r1;
    private final ModelRenderer support2_r1;
    private final ModelRenderer support1_r1;

    public ArrowTurretModel() {
        texWidth = 64;
        texHeight = 64;

        moveable_gun = new ModelRenderer(this);
        moveable_gun.setPos(0.0F, 8.0F, 0.0F);
        moveable_gun.texOffs(14, 35).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
        moveable_gun.texOffs(14, 33).addBox(-8.0F, -2.5F, -0.5F, 7.0F, 1.0F, 1.0F, 0.0F, false);
        moveable_gun.texOffs(0, 33).addBox(1.0F, -5.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);

        base = new ModelRenderer(this);
        base.setPos(0.0F, 24.0F, 0.0F);
        base.texOffs(24, 21).addBox(-2.0F, -16.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);
        base.texOffs(0, 0).addBox(-3.0F, -8.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, false);

        support4_r1 = new ModelRenderer(this);
        support4_r1.setPos(0.0F, 0.0F, 0.0F);
        base.addChild(support4_r1);
        setRotationAngle(support4_r1, 0.7854F, 0.0F, 0.0F);
        support4_r1.texOffs(24, 0).addBox(-3.0F, -6.0F, -5.25F, 6.0F, 1.0F, 6.0F, 0.0F, false);

        support3_r1 = new ModelRenderer(this);
        support3_r1.setPos(0.0F, 0.0F, 0.0F);
        base.addChild(support3_r1);
        setRotationAngle(support3_r1, -0.7854F, 0.0F, 0.0F);
        support3_r1.texOffs(0, 14).addBox(-3.0F, -6.0F, -0.75F, 6.0F, 1.0F, 6.0F, 0.0F, false);

        support2_r1 = new ModelRenderer(this);
        support2_r1.setPos(0.0F, 0.0F, 0.0F);
        base.addChild(support2_r1);
        setRotationAngle(support2_r1, 0.0F, 0.0F, 0.7854F);
        support2_r1.texOffs(24, 14).addBox(-0.75F, -6.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);

        support1_r1 = new ModelRenderer(this);
        support1_r1.setPos(0.0F, 0.0F, 0.0F);
        base.addChild(support1_r1);
        setRotationAngle(support1_r1, 0.0F, 0.0F, -0.7854F);
        support1_r1.texOffs(0, 21).addBox(-5.25F, -6.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(ArrowTurret entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        moveable_gun.yRot = (float) (Functions.getDefaultHeadPitch(headPitch) - Math.toRadians(90));
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        moveable_gun.render(matrixStack, buffer, packedLight, packedOverlay);
        base.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}