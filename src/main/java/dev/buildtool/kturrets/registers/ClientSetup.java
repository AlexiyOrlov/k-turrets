package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.ContainerItem;
import dev.buildtool.kturrets.EntityRenderer2;
import dev.buildtool.kturrets.arrow.*;
import dev.buildtool.kturrets.brick.*;
import dev.buildtool.kturrets.bullet.*;
import dev.buildtool.kturrets.cobble.*;
import dev.buildtool.kturrets.fireball.*;
import dev.buildtool.kturrets.gauss.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SuppressWarnings("RedundantCast")
    @SubscribeEvent
    public static void register(FMLClientSetupEvent clientSetupEvent) {
        MenuScreens.register(KContainers.ARROW_TURRET, (MenuScreens.ScreenConstructor<ArrowTurretContainer, ArrowTurretScreen>) (p_create_1_, p_create_2_, p_create_3_) -> new ArrowTurretScreen(p_create_1_, p_create_2_, p_create_3_, true));
        MenuScreens.register(KContainers.BULLET_TURRET, (MenuScreens.ScreenConstructor<BulletTurretContainer, BulletScreen>) (p1, p2, p3) -> new BulletScreen(p1, p2, p3, true));
        MenuScreens.register(KContainers.FIRE_CHARGE_TURRET, (MenuScreens.ScreenConstructor<FireballTurretContainer, FireballTurretScreen>) (p1, p2, p3) -> new FireballTurretScreen(p1, p2, p3, true));
        MenuScreens.register(KContainers.BRICK_TURRET, (MenuScreens.ScreenConstructor<BrickTurretContainer, BrickTurretScreen>) (p1, p2, p3) -> new BrickTurretScreen(p1, p2, p3, true));
        MenuScreens.register(KContainers.GAUSS_TURRET, (MenuScreens.ScreenConstructor<GaussTurretContainer, GaussTurretScreen>) (p1, p2, p3) -> new GaussTurretScreen(p1, p2, p3, true));
        MenuScreens.register(KContainers.COBBLE_TURRET, (MenuScreens.ScreenConstructor<CobbleTurretContainer, CobbleTurretScreen>) (p1, p2, p3) -> new CobbleTurretScreen(p1, p2, p3, true));

        MenuScreens.register(KContainers.BRICK_DRONE, (MenuScreens.ScreenConstructor<BrickDroneContainer, BrickDroneScreen>) (p1, p2, p3) -> new BrickDroneScreen(p1, p2, p3, true));
        MenuScreens.register(KContainers.BULLET_DRONE.get(), (MenuScreens.ScreenConstructor<BulletDroneContainer, BulletDroneScreen>) (p1, p2, p3) -> new BulletDroneScreen(p1, p2, p3, true));
        MenuScreens.register(KContainers.COBBLE_DRONE.get(), (MenuScreens.ScreenConstructor<CobbleDroneContainer, CobbleDroneScreen>) (p1, p2, p3) -> new CobbleDroneScreen(p1, p2, p3, true));
        MenuScreens.register(KContainers.ARROW_DRONE.get(), (MenuScreens.ScreenConstructor<ArrowDroneContainer, ArrowDroneScreen>) (p1, p2, p3) -> new ArrowDroneScreen(p1, p2, p3, true));
        MenuScreens.register(KContainers.GAUSS_DRONE.get(), (MenuScreens.ScreenConstructor<GaussDroneContainer, GaussDroneScreen>) (p1, p2, p3) -> new GaussDroneScreen(p1, p2, p3, true));
        MenuScreens.register(KContainers.FIRECHARGE_DRONE.get(), (MenuScreens.ScreenConstructor<FireballDroneContainer, FireballDroneScreen>) (p1, p2, p3) -> new FireballDroneScreen(p1, p2, p3, true));
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions definitions) {
        definitions.registerLayerDefinition(ArrowTurretModelv3.LAYER_LOCATION, ArrowTurretModelv3::createBodyLayer);
        definitions.registerLayerDefinition(BrickTurretModelv2.LAYER_LOCATION, BrickTurretModelv2::createBodyLayer);
        definitions.registerLayerDefinition(BulletTurretModelv4.LAYER_LOCATION, BulletTurretModelv4::createBodyLayer);
        definitions.registerLayerDefinition(CobbleTurretModelv4.LAYER_LOCATION, CobbleTurretModelv4::createBodyLayer);
        definitions.registerLayerDefinition(FireballTurretModelv3.LAYER_LOCATION, FireballTurretModelv3::createBodyLayer);
        definitions.registerLayerDefinition(GaussTurretModelv2.LAYER_LOCATION, GaussTurretModelv2::createBodyLayer);
        definitions.registerLayerDefinition(BrickModel.LAYER_LOCATION, BrickModel::createBodyLayer);
        definitions.registerLayerDefinition(BulletModel.LAYER_LOCATION, BulletModel::createBodyLayer);
        definitions.registerLayerDefinition(CobblestoneModel.LAYER_LOCATION, CobblestoneModel::createBodyLayer);
        definitions.registerLayerDefinition(GaussBulletModel.LAYER_LOCATION, GaussBulletModel::createBodyLayer);

        definitions.registerLayerDefinition(BrickDroneModel.LAYER_LOCATION, BrickDroneModel::createBodyLayer);
        definitions.registerLayerDefinition(BulletDroneModel.LAYER_LOCATION, BulletDroneModel::createBodyLayer);
        definitions.registerLayerDefinition(CobbleDroneModelv2.LAYER_LOCATION, CobbleDroneModelv2::createBodyLayer);
        definitions.registerLayerDefinition(ArrowDroneModel.LAYER_LOCATION, ArrowDroneModel::createBodyLayer);
        definitions.registerLayerDefinition(GaussDroneModel.LAYER_LOCATION, GaussDroneModel::createBodyLayer);
        definitions.registerLayerDefinition(FireballDroneModel.LAYER_LOCATION, FireballDroneModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers renderers) {
        renderers.registerEntityRenderer(KEntities.ARROW_TURRET.get(), manager -> new EntityRenderer2<>(manager, new ArrowTurretModelv3<>(manager.bakeLayer(ArrowTurretModelv3.LAYER_LOCATION)), "arrow_turret2", false, 0.4f));
        renderers.registerEntityRenderer(KEntities.COBBLE_TURRET.get(), manager -> new EntityRenderer2<>(manager, new CobbleTurretModelv4<>(manager.bakeLayer(CobbleTurretModelv4.LAYER_LOCATION)), "cobble_turret2", false, 0.2f));
        renderers.registerEntityRenderer(KEntities.GAUSS_TURRET.get(), manager -> new EntityRenderer2<>(manager, new GaussTurretModelv2<>(manager.bakeLayer(GaussTurretModelv2.LAYER_LOCATION)), "gaussturret", false, 0.2f));
        renderers.registerEntityRenderer(KEntities.BRICK_TURRET.get(), manager -> new EntityRenderer2<>(manager, new BrickTurretModelv2<>(manager.bakeLayer(BrickTurretModelv2.LAYER_LOCATION)), "brick_turret", false, 0.4f));
        renderers.registerEntityRenderer(KEntities.FIRE_CHARGE_TURRET.get(), manager -> new EntityRenderer2<>(manager, new FireballTurretModelv3<>(manager.bakeLayer(FireballTurretModelv3.LAYER_LOCATION)), "firecharge_turret2", false, 0.3f));
        renderers.registerEntityRenderer(KEntities.BULLET_TURRET.get(), manager -> new EntityRenderer2<>(manager, new BulletTurretModelv4<>(manager.bakeLayer(BulletTurretModelv4.LAYER_LOCATION)), "bullet_turret4", false, 0.4f));

        renderers.registerEntityRenderer(KEntities.BRICK_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new BrickDroneModel<>(p_174010_.bakeLayer(BrickDroneModel.LAYER_LOCATION)), "brick_drone", false, 0.2f));
        renderers.registerEntityRenderer(KEntities.BULLET_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new BulletDroneModel<>(p_174010_.bakeLayer(BulletDroneModel.LAYER_LOCATION)), "bullet_drone", false, 0.2f));
        renderers.registerEntityRenderer(KEntities.COBBLE_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new CobbleDroneModelv2<>(p_174010_.bakeLayer(CobbleDroneModelv2.LAYER_LOCATION)), "cobble_drone2", false, 0.2f));
        renderers.registerEntityRenderer(KEntities.ARROW_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new ArrowDroneModel<>(p_174010_.bakeLayer(ArrowDroneModel.LAYER_LOCATION)), "arrow_drone", false, 0.2f));
        renderers.registerEntityRenderer(KEntities.GAUSS_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new GaussDroneModel<>(p_174010_.bakeLayer(GaussDroneModel.LAYER_LOCATION)), "gauss_drone", false, 0.2f));
        renderers.registerEntityRenderer(KEntities.FIRECHARGE_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new FireballDroneModel<>(p_174010_.bakeLayer(FireballDroneModel.LAYER_LOCATION)), "firecharge_drone", false, 0.2f));

        renderers.registerEntityRenderer(KEntities.BRICK.get(), BrickRenderer::new);
        renderers.registerEntityRenderer(KEntities.GAUSS_BULLET.get(), GaussBulletRenderer::new);
        renderers.registerEntityRenderer(KEntities.COBBLESTONE.get(), CobblestoneRenderer::new);
        renderers.registerEntityRenderer(KEntities.BULLET.get(), BulletRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayeredItemModel(RegisterColorHandlersEvent.Item registerColorHandlersEvent) {
        ContainerItem.turretPlacers.forEach(containerItem -> registerColorHandlersEvent.register((p_92672_, p_92673_) -> containerItem.getColor(p_92673_), containerItem));
    }
}
