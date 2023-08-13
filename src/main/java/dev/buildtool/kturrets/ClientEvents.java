package dev.buildtool.kturrets;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.LoadingModList;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void renderHealthIndicator(RenderLivingEvent.Post<?, ?> renderLivingEvent) {
        if (LoadingModList.get().getModFileById("neat") == null && KTurrets.SHOW_INTEGRITY.get()) {
            LivingEntity livingEntity = renderLivingEvent.getEntity();
            if (livingEntity instanceof Turret) {
                PoseStack poseStack = renderLivingEvent.getPoseStack();
                poseStack.pushPose();
                String health = String.format("%.1f", livingEntity.getHealth()) + "/" + (int) livingEntity.getMaxHealth();
                poseStack.scale(0.03f, 0.03f, 0.03f);
                poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                poseStack.mulPose(Axis.XP.rotationDegrees(180));
                poseStack.translate(-renderLivingEvent.getRenderer().getFont().width(health) / 2f, -30 - livingEntity.getBbHeight() * 30, 0);
                renderLivingEvent.getRenderer().getFont().drawInBatch(health, 0, 0, livingEntity.getHealth() < livingEntity.getMaxHealth() / 2 ? ChatFormatting.RED.getColor().intValue() : ChatFormatting.GREEN.getColor().intValue(), false, poseStack.last().pose(), renderLivingEvent.getMultiBufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
                poseStack.popPose();
            }
        }
    }
}
