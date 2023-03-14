package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.ContainerItem;
import dev.buildtool.kturrets.EntityRenderer2;
import dev.buildtool.kturrets.arrow.*;
import dev.buildtool.kturrets.brick.*;
import dev.buildtool.kturrets.bullet.*;
import dev.buildtool.kturrets.cobble.*;
import dev.buildtool.kturrets.firecharge.*;
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
        MenuScreens.register(TContainers.ARROW_TURRET, (MenuScreens.ScreenConstructor<ArrowTurretContainer, ArrowTurretScreen>) (p_create_1_, p_create_2_, p_create_3_) -> new ArrowTurretScreen(p_create_1_, p_create_2_, p_create_3_, true));
        MenuScreens.register(TContainers.BULLET_TURRET, (MenuScreens.ScreenConstructor<BulletTurretContainer, BulletScreen>) (p1, p2, p3) -> new BulletScreen(p1, p2, p3, true));
        MenuScreens.register(TContainers.FIRE_CHARGE_TURRET, (MenuScreens.ScreenConstructor<FireChargeTurretContainer, FireChargeScreen>) (p1, p2, p3) -> new FireChargeScreen(p1, p2, p3, true));
        MenuScreens.register(TContainers.BRICK_TURRET, (MenuScreens.ScreenConstructor<BrickTurretContainer, BrickTurretScreen>) (p1, p2, p3) -> new BrickTurretScreen(p1, p2, p3, true));
        MenuScreens.register(TContainers.GAUSS_TURRET, (MenuScreens.ScreenConstructor<GaussTurretContainer, GaussTurretScreen>) (p1, p2, p3) -> new GaussTurretScreen(p1, p2, p3, true));
        MenuScreens.register(TContainers.COBBLE_TURRET, (MenuScreens.ScreenConstructor<CobbleTurretContainer, CobbleTurretScreen>) (p1, p2, p3) -> new CobbleTurretScreen(p1, p2, p3, true));

        MenuScreens.register(TContainers.BRICK_DRONE, (MenuScreens.ScreenConstructor<BrickDroneContainer, BrickDroneScreen>) (p1, p2, p3) -> new BrickDroneScreen(p1, p2, p3, true));
        MenuScreens.register(TContainers.BULLET_DRONE.get(), (MenuScreens.ScreenConstructor<BulletDroneContainer, BulletDroneScreen>) (p1, p2, p3) -> new BulletDroneScreen(p1, p2, p3, true));
        MenuScreens.register(TContainers.COBBLE_DRONE.get(), (MenuScreens.ScreenConstructor<CobbleDroneContainer, CobbleDroneScreen>) (p1, p2, p3) -> new CobbleDroneScreen(p1, p2, p3, true));
        MenuScreens.register(TContainers.ARROW_DRONE.get(), (MenuScreens.ScreenConstructor<ArrowDroneContainer, ArrowDroneScreen>) (p1, p2, p3) -> new ArrowDroneScreen(p1, p2, p3, true));
        MenuScreens.register(TContainers.GAUSS_DRONE.get(), (MenuScreens.ScreenConstructor<GaussDroneContainer, GaussDroneScreen>) (p1, p2, p3) -> new GaussDroneScreen(p1, p2, p3, true));
        MenuScreens.register(TContainers.FIRECHARGE_DRONE.get(), (MenuScreens.ScreenConstructor<FirechargeDroneContainer, FirechargeDroneScreen>) (p1, p2, p3) -> new FirechargeDroneScreen(p1, p2, p3, true));
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions definitions) {
        definitions.registerLayerDefinition(ArrowTurretModelv3.LAYER_LOCATION, ArrowTurretModelv3::createBodyLayer);
        definitions.registerLayerDefinition(BrickTurretModelv2.LAYER_LOCATION, BrickTurretModelv2::createBodyLayer);
        definitions.registerLayerDefinition(BulletTurretModelv4.LAYER_LOCATION, BulletTurretModelv4::createBodyLayer);
        definitions.registerLayerDefinition(CobbleTurretModelv3.LAYER_LOCATION, CobbleTurretModelv3::createBodyLayer);
        definitions.registerLayerDefinition(FirechargeTurretModelv3.LAYER_LOCATION, FirechargeTurretModelv3::createBodyLayer);
        definitions.registerLayerDefinition(GaussTurretModelv2.LAYER_LOCATION, GaussTurretModelv2::createBodyLayer);
        definitions.registerLayerDefinition(BrickModel.LAYER_LOCATION, BrickModel::createBodyLayer);
        definitions.registerLayerDefinition(BulletModel.LAYER_LOCATION, BulletModel::createBodyLayer);
        definitions.registerLayerDefinition(CobblestoneModel.LAYER_LOCATION, CobblestoneModel::createBodyLayer);
        definitions.registerLayerDefinition(GaussBulletModel.LAYER_LOCATION, GaussBulletModel::createBodyLayer);

        definitions.registerLayerDefinition(BrickDroneModel.LAYER_LOCATION, BrickDroneModel::createBodyLayer);
        definitions.registerLayerDefinition(BulletDroneModel.LAYER_LOCATION, BulletDroneModel::createBodyLayer);
        definitions.registerLayerDefinition(CobbleDroneModel.LAYER_LOCATION, CobbleDroneModel::createBodyLayer);
        definitions.registerLayerDefinition(ArrowDroneModel.LAYER_LOCATION, ArrowDroneModel::createBodyLayer);
        definitions.registerLayerDefinition(GaussDroneModel.LAYER_LOCATION, GaussDroneModel::createBodyLayer);
        definitions.registerLayerDefinition(FirechargeDroneModel.LAYER_LOCATION, FirechargeDroneModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers renderers) {
        renderers.registerEntityRenderer(TEntities.ARROW_TURRET.get(), manager -> new EntityRenderer2<>(manager, new ArrowTurretModelv3<>(manager.bakeLayer(ArrowTurretModelv3.LAYER_LOCATION)), "arrow_turret2", false, 0.4f));
        renderers.registerEntityRenderer(TEntities.COBBLE_TURRET.get(), manager -> new EntityRenderer2<>(manager, new CobbleTurretModelv3<>(manager.bakeLayer(CobbleTurretModelv3.LAYER_LOCATION)), "cobble_turret2", false, 0.2f));
        renderers.registerEntityRenderer(TEntities.GAUSS_TURRET.get(), manager -> new EntityRenderer2<>(manager, new GaussTurretModelv2<>(manager.bakeLayer(GaussTurretModelv2.LAYER_LOCATION)), "gaussturret", false, 0.2f));
        renderers.registerEntityRenderer(TEntities.BRICK_TURRET.get(), manager -> new EntityRenderer2<>(manager, new BrickTurretModelv2<>(manager.bakeLayer(BrickTurretModelv2.LAYER_LOCATION)), "brick_turret", false, 0.4f));
        renderers.registerEntityRenderer(TEntities.FIRE_CHARGE_TURRET.get(), manager -> new EntityRenderer2<>(manager, new FirechargeTurretModelv3<>(manager.bakeLayer(FirechargeTurretModelv3.LAYER_LOCATION)), "firecharge_turret2", false, 0.3f));
        renderers.registerEntityRenderer(TEntities.BULLET_TURRET.get(), manager -> new EntityRenderer2<>(manager, new BulletTurretModelv4<>(manager.bakeLayer(BulletTurretModelv4.LAYER_LOCATION)), "bullet_turret4", false, 0.4f));

        renderers.registerEntityRenderer(TEntities.BRICK_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new BrickDroneModel<>(p_174010_.bakeLayer(BrickDroneModel.LAYER_LOCATION)), "brick_drone", false, 0.2f));
        renderers.registerEntityRenderer(TEntities.BULLET_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new BulletDroneModel<>(p_174010_.bakeLayer(BulletDroneModel.LAYER_LOCATION)), "bullet_drone", false, 0.2f));
        renderers.registerEntityRenderer(TEntities.COBBLE_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new CobbleDroneModel<>(p_174010_.bakeLayer(CobbleDroneModel.LAYER_LOCATION)), "cobble_drone", false, 0.2f));
        renderers.registerEntityRenderer(TEntities.ARROW_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new ArrowDroneModel<>(p_174010_.bakeLayer(ArrowDroneModel.LAYER_LOCATION)), "arrow_drone", false, 0.2f));
        renderers.registerEntityRenderer(TEntities.GAUSS_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new GaussDroneModel<>(p_174010_.bakeLayer(GaussDroneModel.LAYER_LOCATION)), "gauss_drone", false, 0.2f));
        renderers.registerEntityRenderer(TEntities.FIRECHARGE_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new FirechargeDroneModel<>(p_174010_.bakeLayer(FirechargeDroneModel.LAYER_LOCATION)), "firecharge_drone", false, 0.2f));

        renderers.registerEntityRenderer(TEntities.BRICK.get(), BrickRenderer::new);
        renderers.registerEntityRenderer(TEntities.GAUSS_BULLET.get(), GaussBulletRenderer::new);
        renderers.registerEntityRenderer(TEntities.COBBLESTONE.get(), CobblestoneRenderer::new);
        renderers.registerEntityRenderer(TEntities.BULLET.get(), BulletRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayeredItemModel(RegisterColorHandlersEvent.Item registerColorHandlersEvent) {
        ContainerItem.turretPlacers.forEach(containerItem -> registerColorHandlersEvent.register((p_92672_, p_92673_) -> containerItem.getColor(p_92673_), containerItem));
    }
}
