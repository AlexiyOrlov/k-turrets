package dev.buildtool.kturrets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.buildtool.satako.Constants;
import dev.buildtool.satako.IntegerColor;
import dev.buildtool.satako.Methods;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.LoadingModList;

import java.util.List;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
    static boolean noDronesNearby;
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

    @SubscribeEvent
    public static void renderDroneLocations(RenderLevelStageEvent renderLevelStageEvent) {
        if (ClientModEvents.highlightDronePositions.isDown() && renderLevelStageEvent.getStage() == RenderLevelStageEvent.Stage.AFTER_CUTOUT_BLOCKS) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            List<Drone> nearbyDrones = minecraft.level.getEntitiesOfClass(Drone.class, new AABB(player.blockPosition()).inflate(64), drone -> drone.getOwner().isPresent() && drone.getOwner().get().equals(player.getUUID()));
            if (!nearbyDrones.isEmpty()) {
                noDronesNearby = false;
                MultiBufferSource bufferSource = minecraft.renderBuffers().bufferSource();
                PoseStack poseStack = renderLevelStageEvent.getPoseStack();
                Vec3 projectedView = minecraft.gameRenderer.getMainCamera().getPosition();
                RenderSystem.disableDepthTest();
                poseStack.pushPose();
                poseStack.translate(-projectedView.x, -projectedView.y, -projectedView.z);
                IntegerColor orange = new IntegerColor(0x40FF5790);
                nearbyDrones.forEach(drone -> {
                    Vec3 dronePosition = drone.getPosition(1);
                    poseStack.translate(dronePosition.x - 0.5, dronePosition.y - 0.2, dronePosition.z - 0.5);
                    Methods.addRectangle(bufferSource.getBuffer(RenderType.guiOverlay()), poseStack.last().pose(), 0, 0, 0, orange.getRed(), orange.getGreen(), orange.getBlue(), orange.getAlpha(), false, 0);
                    poseStack.translate(-(dronePosition.x - 0.5), -(dronePosition.y - 0.2), -(dronePosition.z - 0.5));
                });
                poseStack.popPose();
            } else
                noDronesNearby = true;
        }
    }

    @SubscribeEvent
    public static void showWarning(RenderGuiOverlayEvent.Post renderGuiOverlayEvent) {
        if (ClientModEvents.highlightDronePositions.isDown() && noDronesNearby) {
            Minecraft minecraft = Minecraft.getInstance();
            int screenWidth = renderGuiOverlayEvent.getWindow().getGuiScaledWidth();
            MutableComponent warning = Component.translatable(KTurrets.ID + ".no.drones.nearby");
            renderGuiOverlayEvent.getGuiGraphics().drawString(minecraft.font, warning, screenWidth / 2 - minecraft.font.width(warning) / 2, 5, Constants.GREEN.getIntColor());
        }
    }
}
