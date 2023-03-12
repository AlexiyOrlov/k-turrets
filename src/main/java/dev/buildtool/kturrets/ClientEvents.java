package dev.buildtool.kturrets;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
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
        if (LoadingModList.get().getModFileById("neat") == null) {
            LivingEntity livingEntity = renderLivingEvent.getEntity();
            if (livingEntity instanceof Turret) {
                PoseStack poseStack = renderLivingEvent.getPoseStack();
                poseStack.pushPose();
                String health = (int) livingEntity.getHealth() + "/" + (int) livingEntity.getMaxHealth();
                poseStack.scale(0.03f, 0.03f, 0.03f);
                poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180));
                poseStack.mulPose(Vector3f.XP.rotationDegrees(180));
                poseStack.translate(-renderLivingEvent.getRenderer().getFont().width(health) / 2f, -30 - livingEntity.getBbHeight() * 30, 0);
                renderLivingEvent.getRenderer().getFont().draw(renderLivingEvent.getPoseStack(), health, 0, 0, livingEntity.getHealth() < livingEntity.getMaxHealth() / 2 ? ChatFormatting.RED.getColor() : ChatFormatting.GREEN.getColor());
                poseStack.popPose();
            }
        }
    }
}
