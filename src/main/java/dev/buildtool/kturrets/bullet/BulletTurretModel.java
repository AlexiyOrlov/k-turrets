package dev.buildtool.kturrets.bullet;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BulletTurretModel extends EntityModel<BulletTurret> {
    private final ModelRenderer base;
    private final ModelRenderer moveable_gun;

    public BulletTurretModel() {
        texWidth = 64;
        texHeight = 64;

        base = new ModelRenderer(this);
        base.setPos(0.0F, 24.0F, 0.0F);
        base.texOffs(14, 9).addBox(-1.0F, -15.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
        base.texOffs(0, 27).addBox(-0.5F, -16.0F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        base.texOffs(0, 0).addBox(-3.0F, -11.0F, -3.0F, 6.0F, 3.0F, 6.0F, 0.0F, false);
        base.texOffs(8, 18).addBox(2.0F, -8.0F, -3.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
        base.texOffs(4, 18).addBox(2.0F, -8.0F, 2.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
        base.texOffs(0, 18).addBox(-3.0F, -8.0F, 2.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
        base.texOffs(22, 9).addBox(-3.0F, -8.0F, -3.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);

        moveable_gun = new ModelRenderer(this);
        moveable_gun.setPos(0.0F, 8.0F, 0.0F);
        moveable_gun.texOffs(24, 0).addBox(-0.5F, -1.0F, -6.5F, 1.0F, 1.0F, 7.0F, 0.0F, false);
        moveable_gun.texOffs(0, 9).addBox(-1.0F, -1.5F, 0.5F, 2.0F, 2.0F, 5.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(BulletTurret entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        base.render(matrixStack, buffer, packedLight, packedOverlay);
        moveable_gun.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}