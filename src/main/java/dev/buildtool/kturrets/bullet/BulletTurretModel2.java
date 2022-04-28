//package dev.buildtool.kturrets.bullet;
//
//import com.mojang.blaze3d.matrix.MatrixStack;
//import com.mojang.blaze3d.vertex.IVertexBuilder;
//import dev.buildtool.satako.Functions;
//import net.minecraft.client.renderer.entity.model.EntityModel;
//import net.minecraft.client.renderer.model.ModelRenderer;
//@Deprecated
//public class BulletTurretModel2 extends EntityModel<BulletTurret> {
//    private final ModelRenderer base;
//    private final ModelRenderer moveable_gun;
//
//    public BulletTurretModel2() {
//        texWidth = 64;
//        texHeight = 64;
//
//        base = new ModelRenderer(this);
//        base.setPos(0.0F, 24.0F, 0.0F);
//        base.texOffs(28, 29).addBox(-2.0F, -27.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);
//        base.texOffs(0, 0).addBox(-1.0F, -29.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
//        base.texOffs(0, 0).addBox(-6.0F, -19.0F, -6.0F, 12.0F, 3.0F, 12.0F, 0.0F, false);
//        base.texOffs(24, 41).addBox(4.0F, -16.0F, -6.0F, 2.0F, 16.0F, 2.0F, 0.0F, false);
//        base.texOffs(16, 31).addBox(4.0F, -16.0F, 4.0F, 2.0F, 16.0F, 2.0F, 0.0F, false);
//        base.texOffs(8, 31).addBox(-6.0F, -16.0F, 4.0F, 2.0F, 16.0F, 2.0F, 0.0F, false);
//        base.texOffs(0, 31).addBox(-6.0F, -16.0F, -6.0F, 2.0F, 16.0F, 2.0F, 0.0F, false);
//
//        moveable_gun = new ModelRenderer(this);
//        moveable_gun.setPos(0.0F, 8.0F, 0.0F);
//        moveable_gun.texOffs(0, 15).addBox(-1.0F, -15.0F, -13.0F, 2.0F, 2.0F, 14.0F, 0.0F, false);
//        moveable_gun.texOffs(18, 15).addBox(-2.0F, -16.0F, 1.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);
//    }
//
//    @Override
//    public void setupAnim(BulletTurret entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//        moveable_gun.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
//        moveable_gun.xRot = Functions.getDefaultHeadPitch(headPitch);
//    }
//
//    @Override
//    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//        base.render(matrixStack, buffer, packedLight, packedOverlay);
//        moveable_gun.render(matrixStack, buffer, packedLight, packedOverlay);
//    }
//
//}