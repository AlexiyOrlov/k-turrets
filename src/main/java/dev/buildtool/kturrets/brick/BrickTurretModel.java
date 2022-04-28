//package dev.buildtool.kturrets.brick;
//
//
//import com.mojang.blaze3d.matrix.MatrixStack;
//import com.mojang.blaze3d.vertex.IVertexBuilder;
//import dev.buildtool.satako.Functions;
//import net.minecraft.client.renderer.entity.model.EntityModel;
//import net.minecraft.client.renderer.model.ModelRenderer;
//@Deprecated
//public class BrickTurretModel extends EntityModel<BrickTurret> {
//    private final ModelRenderer base;
//    private final ModelRenderer moveable_gun;
//    private final ModelRenderer octagon;
//    private final ModelRenderer octagon_r1;
//
//    public BrickTurretModel() {
//        texWidth = 128;
//        texHeight = 128;
//
//        base = new ModelRenderer(this);
//        base.setPos(0.0F, 24.0F, 0.0F);
//        base.texOffs(0, 0).addBox(-6.0F, -3.0F, -6.0F, 12.0F, 3.0F, 12.0F, 0.0F, false);
//        base.texOffs(24, 41).addBox(-2.0F, -14.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, false);
//        base.texOffs(0, 41).addBox(-3.0F, -15.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
//        base.texOffs(28, 15).addBox(-4.0F, -16.0F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);
//        base.texOffs(48, 0).addBox(-5.0F, -17.0F, -5.0F, 10.0F, 1.0F, 10.0F, 0.0F, false);
//
//        moveable_gun = new ModelRenderer(this);
//        moveable_gun.setPos(0.0F, 6.0F, 0.0F);
//        moveable_gun.texOffs(0, 15).addBox(-2.0F, -5.0F, -8.0F, 4.0F, 4.0F, 10.0F, 0.0F, false);
//        moveable_gun.texOffs(0, 29).addBox(-5.0F, -7.0F, 0.0F, 3.0F, 3.0F, 9.0F, 0.0F, false);
//        moveable_gun.texOffs(60, 15).addBox(2.0F, -7.0F, 0.0F, 3.0F, 3.0F, 9.0F, 0.0F, false);
//        moveable_gun.texOffs(40, 41).addBox(-3.0F, -8.0F, 6.0F, 6.0F, 1.0F, 3.0F, 0.0F, false);
//
//        octagon = new ModelRenderer(this);
//        octagon.setPos(0.0F, 0.0F, 0.0F);
//        moveable_gun.addChild(octagon);
//        setRotationAngle(octagon, 0.0F, 0.0F, -1.5708F);
//        octagon.texOffs(24, 29).addBox(-1.0F, -1.6569F, -4.0F, 2.0F, 3.0F, 8.0F, 0.0F, false);
//        octagon.texOffs(40, 45).addBox(-1.0F, -4.0F, -1.6569F, 2.0F, 8.0F, 3.0F, 0.0F, false);
//
//        octagon_r1 = new ModelRenderer(this);
//        octagon_r1.setPos(0.0F, 0.0F, 0.0F);
//        octagon.addChild(octagon_r1);
//        setRotationAngle(octagon_r1, -0.7854F, 0.0F, 0.0F);
//        octagon_r1.texOffs(0, 56).addBox(-1.0F, -4.0F, -1.6569F, 2.0F, 8.0F, 3.0F, 0.0F, false);
//        octagon_r1.texOffs(44, 29).addBox(-1.0F, -1.6569F, -4.0F, 2.0F, 3.0F, 8.0F, 0.0F, false);
//    }
//
//    @Override
//    public void setupAnim(BrickTurret entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
//    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
//        modelRenderer.xRot = x;
//        modelRenderer.yRot = y;
//        modelRenderer.zRot = z;
//    }
//}