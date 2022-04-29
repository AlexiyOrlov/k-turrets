package dev.buildtool.kturrets.registers;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.buildtool.kturrets.EntityRenderer2;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.arrow.ArrowTurretContainer;
import dev.buildtool.kturrets.arrow.ArrowTurretScreen;
import dev.buildtool.kturrets.arrow.ArrowTurretv2;
import dev.buildtool.kturrets.brick.*;
import dev.buildtool.kturrets.bullet.*;
import dev.buildtool.kturrets.cobble.*;
import dev.buildtool.kturrets.firecharge.FireChargeScreen;
import dev.buildtool.kturrets.firecharge.FireChargeTurretContainer;
import dev.buildtool.kturrets.firecharge.FirechargeTurretv2;
import dev.buildtool.kturrets.gauss.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SuppressWarnings("RedundantCast")
    @SubscribeEvent
    public static void register(FMLClientSetupEvent clientSetupEvent) {
        MenuScreens.register(TContainers.ARROW_TURRET, (MenuScreens.ScreenConstructor<ArrowTurretContainer, ArrowTurretScreen>) (p_create_1_, p_create_2_, p_create_3_) -> new ArrowTurretScreen(p_create_1_, p_create_2_, p_create_3_, true));
        MenuScreens.register(TContainers.BULLET_TURRET, (MenuScreens.ScreenConstructor<BulletTurretContainer, BulletScreen>) (p1, p2, p3) -> new BulletScreen(p1, p2, p3, true));
        MenuScreens.register(TContainers.FIRE_CHARGE_TURRET, (MenuScreens.ScreenConstructor<FireChargeTurretContainer, FireChargeScreen>) (p1, p2, p3) -> new FireChargeScreen(p1, p2, p3, true));
        MenuScreens.register(TContainers.BRICK_TURRET, (MenuScreens.ScreenConstructor<BrickTurretContainer, BrickTurretScreen>) (p1, p2, p3) -> new BrickTurretScreen(p1, p2, p3, true));
        MenuScreens.register(TContainers.GAUSS_TURRET, (MenuScreens.ScreenConstructor<GaussTurretContainer, GaussTurretScreen>) (p1, p2, p3) -> new GaussTurretScreen(p1, p2, p3, true));
        MenuScreens.register(TContainers.COBBLE_TURRET, (MenuScreens.ScreenConstructor<CobbleTurretContainer, CobbleTurretScreen>) (p1, p2, p3) -> new CobbleTurretScreen(p1, p2, p3, true));
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions registerLayerDefinitions) {
        registerLayerDefinitions.registerLayerDefinition(ArrowTurretv2.LAYER_LOCATION, ArrowTurretv2::createBodyLayer);
        registerLayerDefinitions.registerLayerDefinition(BrickTurretModelv2.LAYER_LOCATION, BrickTurretModelv2::createBodyLayer);
        registerLayerDefinitions.registerLayerDefinition(BulletTurretv2.LAYER_LOCATION, BulletTurretv2::createBodyLayer);
        registerLayerDefinitions.registerLayerDefinition(CobbleTurretv2.LAYER_LOCATION, CobbleTurretv2::createBodyLayer);
        registerLayerDefinitions.registerLayerDefinition(FirechargeTurretv2.LAYER_LOCATION, FirechargeTurretv2::createBodyLayer);
        registerLayerDefinitions.registerLayerDefinition(GaussTurretModelv2.LAYER_LOCATION, GaussTurretModelv2::createBodyLayer);
        registerLayerDefinitions.registerLayerDefinition(BrickModel.LAYER_LOCATION, BrickModel::createBodyLayer);
        registerLayerDefinitions.registerLayerDefinition(BulletModel.LAYER_LOCATION, BulletModel::createBodyLayer);
        registerLayerDefinitions.registerLayerDefinition(CobblestoneModel.LAYER_LOCATION, CobblestoneModel::createBodyLayer);
        registerLayerDefinitions.registerLayerDefinition(GaussBulletModel.LAYER_LOCATION, GaussBulletModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers registerRenderers) {
        registerRenderers.registerEntityRenderer(TEntities.ARROW_TURRET.get(), manager -> new EntityRenderer2<>(manager, new ArrowTurretv2<>(manager.bakeLayer(ArrowTurretv2.LAYER_LOCATION)), "arrow_turret", false, 0.4f));
        registerRenderers.registerEntityRenderer(TEntities.COBBLE_TURRET.get(), manager -> new EntityRenderer2<>(manager, new CobbleTurretv2<>(manager.bakeLayer(CobbleTurretv2.LAYER_LOCATION)), "cobble_turret", false, 0.2f));
        registerRenderers.registerEntityRenderer(TEntities.GAUSS_TURRET.get(), manager -> new EntityRenderer2<>(manager, new GaussTurretModelv2<>(manager.bakeLayer(GaussTurretModelv2.LAYER_LOCATION)), "gaussturret", false, 0.2f));
        registerRenderers.registerEntityRenderer(TEntities.BRICK_TURRET.get(), manager -> new EntityRenderer2<>(manager, new BrickTurretModelv2<>(manager.bakeLayer(BrickTurretModelv2.LAYER_LOCATION)), "brick_turret", false, 0.4f));
        registerRenderers.registerEntityRenderer(TEntities.FIRE_CHARGE_TURRET.get(), manager -> new EntityRenderer2<>(manager, new FirechargeTurretv2<>(manager.bakeLayer(FirechargeTurretv2.LAYER_LOCATION)), "firecharge_turret", false, 0.3f));
        registerRenderers.registerEntityRenderer(TEntities.BULLET_TURRET.get(), manager -> new EntityRenderer2<>(manager, new BulletTurretv2<>(manager.bakeLayer(BulletTurretv2.LAYER_LOCATION)), "bullet_turret", false, 0.4f));

        registerRenderers.registerEntityRenderer(TEntities.BRICK.get(), BrickEntityRenderer::new);
        registerRenderers.registerEntityRenderer(TEntities.GAUSS_BULLET.get(), GaussBulletRenderer::new);
        registerRenderers.registerEntityRenderer(TEntities.COBBLESTONE.get(), CobblestoneRenderer::new);
        registerRenderers.registerEntityRenderer(TEntities.BULLET.get(), BulletRenderer::new);
    }

    private static class BrickEntityRenderer extends EntityRenderer<Brick> {
        private static final ResourceLocation TEXTURE1 = new ResourceLocation(KTurrets.ID, "textures/entity/brick.png");
        private static final ResourceLocation TEXTURE2 = new ResourceLocation(KTurrets.ID, "textures/entity/netherbrick.png");
        private final BrickModel<Brick> brickModel;

        public BrickEntityRenderer(EntityRendererProvider.Context p_174010_) {
            super(p_174010_);
            brickModel = new BrickModel<>(p_174010_.bakeLayer(BrickModel.LAYER_LOCATION));
        }

        @Override
        public ResourceLocation getTextureLocation(Brick p_114482_) {
            return p_114482_.getDamage() == KTurrets.BRICK_DAMAGE.get() ? TEXTURE1 : TEXTURE2;
        }

        @Override
        public void render(Brick brick, float yaw, float tick, PoseStack poseStack, MultiBufferSource multiBufferSource, int light) {
            super.render(brick, yaw, tick, poseStack, multiBufferSource, light);
            poseStack.pushPose();
            poseStack.translate(0, -1.3, 0);
            brickModel.setupAnim(brick, 0, 0, tick, yaw, 0);
            brickModel.renderToBuffer(poseStack, multiBufferSource.getBuffer(RenderType.entityCutout(getTextureLocation(brick))), light, getPackedLightCoords(brick, 0xff0f7f), 1, 1, 1, 1);
            poseStack.popPose();
        }
    }
}
