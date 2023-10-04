package dev.buildtool.kturrets;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.buildtool.satako.ClientMethods;
import dev.buildtool.satako.Constants;
import dev.buildtool.satako.IntegerColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
    static boolean noDronesNearby;
    @SubscribeEvent
    public static void renderHealth(RenderLivingEvent.Post<?, ?> renderLivingEvent) {
        if (KTurrets.SHOW_INTEGRITY.get()) {
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

    @SubscribeEvent
    public static void renderDroneLocations(RenderWorldLastEvent renderLevelStageEvent) {
        if (ClientModEvents.highlightDronePositions.isDown() /*&& renderLevelStageEvent.get == RenderLevelStageEvent.Stage.AFTER_CUTOUT_BLOCKS*/) {
            Minecraft minecraft = Minecraft.getInstance();
            PlayerEntity player = minecraft.player;
            List<Drone> nearbyDrones = minecraft.level.getEntitiesOfClass(Drone.class, new AxisAlignedBB(player.blockPosition()).inflate(64), drone -> drone.getOwner().isPresent() && drone.getOwner().get().equals(player.getUUID()));
            if (!nearbyDrones.isEmpty()) {
                noDronesNearby = false;
                IRenderTypeBuffer bufferSource = minecraft.renderBuffers().bufferSource();
                MatrixStack poseStack = renderLevelStageEvent.getMatrixStack();
                Vector3d projectedView = minecraft.gameRenderer.getMainCamera().getPosition();
                poseStack.pushPose();
                poseStack.translate(-projectedView.x, -projectedView.y, -projectedView.z);
                IntegerColor orange = new IntegerColor(0x40FF5790);
                nearbyDrones.forEach(drone -> {
                    Vector3d dronePosition = drone.getPosition(1);
                    poseStack.translate(dronePosition.x - 0.5, dronePosition.y - 0.2, dronePosition.z - 0.5);
                    ClientMethods.addRectangle(bufferSource.getBuffer(RenderType.lightning()), poseStack.last().pose(), 0, 0, 0, orange.getRed(), orange.getGreen(), orange.getBlue(), orange.getAlpha(), false, 0);
                    poseStack.translate(-(dronePosition.x - 0.5), -(dronePosition.y - 0.2), -(dronePosition.z - 0.5));
                });
                poseStack.popPose();
            } else
                noDronesNearby = true;
        }
    }

    @SubscribeEvent
    public static void showWarning(RenderGameOverlayEvent.Post renderGuiOverlayEvent) {
        if (ClientModEvents.highlightDronePositions.isDown() && noDronesNearby) {
            Minecraft minecraft = Minecraft.getInstance();
            int screenWidth = renderGuiOverlayEvent.getWindow().getGuiScaledWidth();
            TranslationTextComponent warning = new TranslationTextComponent(KTurrets.ID + ".no.drones.nearby");
            ClientMethods.drawString(renderGuiOverlayEvent.getMatrixStack(), warning.getString(), screenWidth / 2 - minecraft.font.width(warning) / 2, 20, Constants.GREEN);
        }
    }
}
