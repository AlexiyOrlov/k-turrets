package dev.buildtool.kturrets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.buildtool.kturrets.storage.StorageDrone;
import dev.buildtool.satako.Constants;
import dev.buildtool.satako.IntegerColor;
import dev.buildtool.satako.clientside.ClientMethods;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
    static boolean noDronesNearby;
    @SubscribeEvent
    public static void renderHealthIndicator(RenderLivingEvent.Post<?, ?> renderLivingEvent) {
        if (KTurrets.SHOW_INTEGRITY.get()) {
            LivingEntity livingEntity = renderLivingEvent.getEntity();
            Player player = Minecraft.getInstance().player;
            if (livingEntity instanceof Turret turret) {
                if (player.distanceTo(turret) < 23) {
                    if (turret.getOwner().isEmpty() || (turret.getOwner().isPresent() && (player.getUUID().equals(turret.getOwner().get()) || player.isAlliedTo(turret)))) {
                        PoseStack poseStack = renderLivingEvent.getPoseStack();
                        poseStack.pushPose();
                        String health = String.format("%.1f", livingEntity.getHealth()) + "/" + (int) livingEntity.getMaxHealth();
                        poseStack.scale(0.03f, 0.03f, 0.03f);
                        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
                        poseStack.mulPose(Axis.YP.rotationDegrees(180));
                        poseStack.mulPose(Axis.XP.rotationDegrees(180));
                        Font font = renderLivingEvent.getRenderer().getFont();
                        poseStack.translate(-font.width(health) / 2f, -30 - livingEntity.getBbHeight() * 30, 0);
                        font.drawInBatch(health, 0, 0, livingEntity.getHealth() < livingEntity.getMaxHealth() / 2 ? ChatFormatting.RED.getColor().intValue() : ChatFormatting.GREEN.getColor().intValue(), false, poseStack.last().pose(), renderLivingEvent.getMultiBufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
                        if (turret.noAmmo && !(turret instanceof StorageDrone)) {
                            poseStack.translate(0, -13, 0);
                            Component noAmmo = Component.translatable("k_turrets.no.ammo");
                            font.drawInBatch(noAmmo, 0, 0, ChatFormatting.RED.getColor(), false, poseStack.last().pose(), renderLivingEvent.getMultiBufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
                        }
                        poseStack.popPose();
                    }
                }
            }
        }
        LivingEntity livingEntity = renderLivingEvent.getEntity();
        Player player = Minecraft.getInstance().player;
        if (livingEntity instanceof Turret turret) {
            if (player.distanceTo(turret) < 23) {
                if (turret.noAmmo && !(turret instanceof StorageDrone)) {
                    PoseStack poseStack = renderLivingEvent.getPoseStack();
                    Font font = renderLivingEvent.getRenderer().getFont();
                    Component noAmmo = Component.translatable("k_turrets.no.ammo");
                    if (turret.getOwner().isEmpty() || (turret.getOwner().isPresent() && (player.getUUID().equals(turret.getOwner().get()) || player.isAlliedTo(turret)))) {
                        poseStack.pushPose();
                        poseStack.translate(0, -13, 0);
                        poseStack.scale(0.03f, 0.03f, 0.03f);
                        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
                        poseStack.mulPose(Axis.YP.rotationDegrees(180));
                        poseStack.mulPose(Axis.XP.rotationDegrees(180));
                        font.drawInBatch(noAmmo, 0, 0, ChatFormatting.RED.getColor(), false, poseStack.last().pose(), renderLivingEvent.getMultiBufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
                        poseStack.popPose();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void renderDroneLocations(RenderLevelStageEvent renderLevelStageEvent) {
        if (ClientModEvents.highlightDronePositions.isDown() && renderLevelStageEvent.getStage() == RenderLevelStageEvent.Stage.AFTER_CUTOUT_BLOCKS) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            List<Drone> nearbyDrones = minecraft.level.getEntitiesOfClass(Drone.class, new AABB(player.blockPosition()).inflate(128), drone -> drone.getOwner().isPresent() && drone.getOwner().get().equals(player.getUUID()));
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
                    ClientMethods.addRectangle(bufferSource.getBuffer(RenderType.guiOverlay()), poseStack.last().pose(), 0, 0, 0, orange.getRed(), orange.getGreen(), orange.getBlue(), orange.getAlpha(), false, 0);
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

    @SubscribeEvent
    public static void addTooltipInfo(ItemTooltipEvent tooltipEvent)
    {
        ItemStack stack=tooltipEvent.getItemStack();
        List<Component> tooltip=tooltipEvent.getToolTip();
        if(stack.is(KTurrets.COBBLE_UNIT_AMMO_TAG))
            tooltip.add(Component.translatable("k_turrets.cobble.unit.ammo"));
        if(stack.is(KTurrets.ARROW_UNIT_AMMO_TAG))
            tooltip.add(Component.translatable("k_turrets.arrow.unit.ammo"));
        if(stack.is(KTurrets.GAUSS_UNIT_AMMO_TAG))
            tooltip.add(Component.translatable("k_turrets.gauss.unit.ammo"));
        if(stack.is(KTurrets.BRICK_UNIT_AMMO_TAG))
            tooltip.add(Component.translatable("k_turrets.brick.unit.ammo"));
        if(stack.is(KTurrets.BULLET_UNIT_AMMO_TAG))
            tooltip.add(Component.translatable("k_turrets.bullet.unit.ammo"));
        if(stack.is(KTurrets.FIREBALL_UNIT_AMMO))
            tooltip.add(Component.translatable("k_turrets.fireball.unit.ammo"));
    }
}
