package dev.buildtool.kturrets;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.LoadingModList;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void renderHealth(RenderLivingEvent.Post<?, ?> renderLivingEvent) {
        if (LoadingModList.get().getModFileById("neat") == null) {
            LivingEntity livingEntity = renderLivingEvent.getEntity();
            if (livingEntity instanceof Turret) {
                MatrixStack matrixStack = renderLivingEvent.getMatrixStack();
                matrixStack.pushPose();
                String health = String.format("%.1f", livingEntity.getHealth()) + "/" + (int) livingEntity.getMaxHealth();
                matrixStack.scale(0.03f, 0.03f, 0.03f);
                matrixStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(180));
                matrixStack.mulPose(Vector3f.XP.rotationDegrees(180));
                matrixStack.translate(-renderLivingEvent.getRenderer().getFont().width(health) / 2f, -30 - livingEntity.getBbHeight() * 30, 0);
                renderLivingEvent.getRenderer().getFont().draw(matrixStack, health, 0, 0, livingEntity.getHealth() < livingEntity.getMaxHealth() / 2 ? TextFormatting.RED.getColor() : TextFormatting.GREEN.getColor());
                matrixStack.popPose();
            }
        }
    }
}
