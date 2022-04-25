package dev.buildtool.kturrets.firecharge;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dev.buildtool.satako.Functions;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
@Deprecated
public class FireChargeTurretModel extends EntityModel<FireChargeTurret> {
    private final ModelRenderer moveable_gun;
    private final ModelRenderer base;

    public FireChargeTurretModel() {
        texWidth = 64;
        texHeight = 64;

        moveable_gun = new ModelRenderer(this);
        moveable_gun.setPos(0.0F, -0.75F, 0.125F);
        moveable_gun.texOffs(0, 36).addBox(-2.0F, -1.25F, -2.125F, 4.0F, 2.0F, 5.0F, 0.0F, false);
        moveable_gun.texOffs(0, 24).addBox(-1.0F, -3.25F, -7.125F, 2.0F, 2.0F, 10.0F, 0.0F, false);
        moveable_gun.texOffs(26, 36).addBox(-3.0F, -5.25F, 2.875F, 2.0F, 9.0F, 2.0F, 0.0F, false);
        moveable_gun.texOffs(18, 36).addBox(1.0F, -5.25F, 2.875F, 2.0F, 9.0F, 2.0F, 0.0F, false);

        base = new ModelRenderer(this);
        base.setPos(0.0F, 24.0F, 0.0F);
        base.texOffs(0, 47).addBox(-1.0F, -24.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);
        base.texOffs(0, 0).addBox(-7.0F, -10.0F, -7.0F, 14.0F, 10.0F, 14.0F, 0.0F, false);
        base.texOffs(24, 24).addBox(-3.0F, -16.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(FireChargeTurret entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        moveable_gun.yRot = Functions.getDefaultHeadYaw(netHeadYaw);
        moveable_gun.xRot = Functions.getDefaultHeadPitch(headPitch);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        moveable_gun.render(matrixStack, buffer, packedLight, packedOverlay);
        base.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}