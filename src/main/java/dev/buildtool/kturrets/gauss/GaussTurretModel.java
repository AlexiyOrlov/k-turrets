package dev.buildtool.kturrets.gauss;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.buildtool.satako.Functions;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class GaussTurretModel extends EntityModel<GaussTurret> {
    private final ModelRenderer turret_base;
    private final ModelRenderer turret_angler;
    private final ModelRenderer turret_piece;
    private final ModelRenderer turret_neck;
    private final ModelRenderer cube_r1;
    private final ModelRenderer turret_head;
    private final ModelRenderer turret_barrel;

    public GaussTurretModel() {
        texWidth = 64;
        texHeight = 64;

        turret_base = new ModelRenderer(this);
        turret_base.setPos(0.0F, 22.0F, 1.0F);
        turret_base.texOffs(0, 12).addBox(-2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 6.0F, 0.0F, false);

        turret_angler = new ModelRenderer(this);
        turret_angler.setPos(0.0F, -2.0F, 0.0F);
        turret_base.addChild(turret_angler);
        setRotationAngle(turret_angler, -0.2618F, 0.0F, 0.0F);
        turret_angler.texOffs(29, 23).addBox(-1.5F, -3.0F, -2.5F, 3.0F, 4.0F, 5.0F, 0.0F, false);

        turret_piece = new ModelRenderer(this);
        turret_piece.setPos(0.0F, -3.0F, 0.0F);
        turret_angler.addChild(turret_piece);
        setRotationAngle(turret_piece, 0.2618F, 0.0F, 0.0F);
        turret_piece.texOffs(20, 6).addBox(-2.0F, -1.0F, -3.0F, 4.0F, 2.0F, 6.0F, 0.0F, false);

        turret_neck = new ModelRenderer(this);
        turret_neck.setPos(0.0F, -1.0F, 0.0F);
        turret_piece.addChild(turret_neck);


        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(0.0F, 0.0F, 0.0F);
        turret_neck.addChild(cube_r1);
        setRotationAngle(cube_r1, -0.7854F, 0.0F, 0.0F);
        cube_r1.texOffs(0, 30).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 6.0F, 4.0F, 0.0F, false);

        turret_head = new ModelRenderer(this);
        turret_head.setPos(0.0F, -3.0F, 2.5F);
        turret_neck.addChild(turret_head);
        turret_head.texOffs(16, 16).addBox(-1.5F, -3.5F, -1.0F, 3.0F, 6.0F, 6.0F, 0.0F, false);
        turret_head.texOffs(0, 0).addBox(-3.0F, -3.0F, -4.0F, 6.0F, 5.0F, 7.0F, 0.0F, false);

        turret_barrel = new ModelRenderer(this);
        turret_barrel.setPos(0.0F, -0.5F, -4.0F);
        turret_head.addChild(turret_barrel);
        turret_barrel.texOffs(15, 28).addBox(-1.5F, -2.0F, -4.0F, 3.0F, 4.0F, 5.0F, 0.0F, false);
        turret_barrel.texOffs(0, 23).addBox(-2.5F, -1.0F, -8.0F, 5.0F, 2.0F, 5.0F, 0.0F, false);
        turret_barrel.texOffs(26, 32).addBox(1.0F, 0.0F, -11.0F, 2.0F, 2.0F, 5.0F, 0.0F, false);
        turret_barrel.texOffs(28, 14).addBox(-3.0F, 0.0F, -11.0F, 2.0F, 2.0F, 5.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(GaussTurret entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        turret_angler.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
        turret_angler.xRot = Functions.getDefaultHeadPitch(headPitch);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        turret_base.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}