package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.*;
import dev.buildtool.kturrets.arrow.*;
import dev.buildtool.kturrets.brick.*;
import dev.buildtool.kturrets.bullet.*;
import dev.buildtool.kturrets.cobble.*;
import dev.buildtool.kturrets.fireball.*;
import dev.buildtool.kturrets.gauss.*;
import dev.buildtool.kturrets.storage.*;
import net.minecraft.client.gui.screens.MenuScreens;
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
        MenuScreens.register(KTContainers.ARROW_TURRET.get(), (MenuScreens.ScreenConstructor<ArrowTurretContainer, ArrowTurretScreen>) (p_create_1_, p_create_2_, p_create_3_) -> new ArrowTurretScreen(p_create_1_, p_create_2_, p_create_3_, true));
        MenuScreens.register(KTContainers.BULLET_TURRET.get(), (MenuScreens.ScreenConstructor<BulletTurretContainer, BulletTurretScreen>) (p1, p2, p3) -> new BulletTurretScreen(p1, p2, p3, true));
        MenuScreens.register(KTContainers.FIRE_CHARGE_TURRET.get(), (MenuScreens.ScreenConstructor<FireballTurretContainer, FireballTurretScreen>) (p1, p2, p3) -> new FireballTurretScreen(p1, p2, p3, true));
        MenuScreens.register(KTContainers.BRICK_TURRET.get(), (MenuScreens.ScreenConstructor<BrickTurretContainer, BrickTurretScreen>) (p1, p2, p3) -> new BrickTurretScreen(p1, p2, p3, true));
        MenuScreens.register(KTContainers.GAUSS_TURRET.get(), (MenuScreens.ScreenConstructor<GaussTurretContainer, GaussTurretScreen>) (p1, p2, p3) -> new GaussTurretScreen(p1, p2, p3, true));
        MenuScreens.register(KTContainers.COBBLE_TURRET.get(), (MenuScreens.ScreenConstructor<CobbleTurretContainer, CobbleTurretScreen>) (p1, p2, p3) -> new CobbleTurretScreen(p1, p2, p3, true));

        MenuScreens.register(KTContainers.BRICK_DRONE.get(), (MenuScreens.ScreenConstructor<BrickDroneContainer, BrickDroneScreen>) (p1, p2, p3) -> new BrickDroneScreen(p1, p2, p3, true));
        MenuScreens.register(KTContainers.BULLET_DRONE.get(), (MenuScreens.ScreenConstructor<BulletDroneContainer, BulletDroneScreen>) (p1, p2, p3) -> new BulletDroneScreen(p1, p2, p3, true));
        MenuScreens.register(KTContainers.COBBLE_DRONE.get(), (MenuScreens.ScreenConstructor<CobbleDroneContainer, CobbleDroneScreen>) (p1, p2, p3) -> new CobbleDroneScreen(p1, p2, p3, true));
        MenuScreens.register(KTContainers.ARROW_DRONE.get(), (MenuScreens.ScreenConstructor<ArrowDroneContainer, ArrowDroneScreen>) (p1, p2, p3) -> new ArrowDroneScreen(p1, p2, p3, true));
        MenuScreens.register(KTContainers.GAUSS_DRONE.get(), (MenuScreens.ScreenConstructor<GaussDroneContainer, GaussDroneScreen>) (p1, p2, p3) -> new GaussDroneScreen(p1, p2, p3, true));
        MenuScreens.register(KTContainers.FIRECHARGE_DRONE.get(), (MenuScreens.ScreenConstructor<FireballDroneContainer, FireballDroneScreen>) (p1, p2, p3) -> new FireballDroneScreen(p1, p2, p3, true));

        MenuScreens.register(KTContainers.STORAGE_DRONE.get(), (MenuScreens.ScreenConstructor<StorageDroneMenu, StorageDroneMenuScreen>) StorageDroneMenuScreen::new);

        MenuScreens.register(KTContainers.RELOADER.get(), (MenuScreens.ScreenConstructor<ReloaderMenu, ReloaderScreen>) (p1, p2, p3) -> new ReloaderScreen(p1, p2, p3, true));

        MenuScreens.register(KTContainers.MAGNET.get(),(MenuScreens.ScreenConstructor<MagnetMenu, MagnetScreen>) MagnetScreen::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions definitions) {
        if(KTurrets.useNewArrowTurretModel.get())
            definitions.registerLayerDefinition(ArrowTurretModelNew.LAYER_LOCATION,ArrowTurretModelNew::createBodyLayer);
        else
            definitions.registerLayerDefinition(ArrowTurretModelv3.LAYER_LOCATION, ArrowTurretModelv3::createBodyLayer);
        if(KTurrets.useNewBrickTurretModel.get())
            definitions.registerLayerDefinition(BrickTurretModelNew.LAYER_LOCATION,BrickTurretModelNew::createBodyLayer);
        else
            definitions.registerLayerDefinition(BrickTurretModelv2.LAYER_LOCATION, BrickTurretModelv2::createBodyLayer);
        if(KTurrets.useNewBulletTurretModel.get())
            definitions.registerLayerDefinition(BulletTurretModelNew.LAYER_LOCATION,BulletTurretModelNew::createBodyLayer);
        else
            definitions.registerLayerDefinition(BulletTurretModelv4.LAYER_LOCATION, BulletTurretModelv4::createBodyLayer);
        if(KTurrets.useNewCobbleTurretModel.get())
            definitions.registerLayerDefinition(CobbleTurretNew.LAYER_LOCATION,CobbleTurretNew::createBodyLayer);
        else
            definitions.registerLayerDefinition(CobbleTurretModelv4.LAYER_LOCATION, CobbleTurretModelv4::createBodyLayer);
        if(KTurrets.useNewFireballTurretModel.get())
            definitions.registerLayerDefinition(FireballTurretModelNew.LAYER_LOCATION,FireballTurretModelNew::createBodyLayer);
        else
            definitions.registerLayerDefinition(FireballTurretModelv4.LAYER_LOCATION, FireballTurretModelv4::createBodyLayer);
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

        definitions.registerLayerDefinition(StorageDroneModel.LAYER_LOCATION, StorageDroneModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers renderers) {
        if(KTurrets.useNewArrowTurretModel.get())
            renderers.registerEntityRenderer(KTEntities.ARROW_TURRET.get(), context -> new EntityRenderer2<>(context,new ArrowTurretModelNew<>(context.bakeLayer(ArrowTurretModelNew.LAYER_LOCATION)),"arrowturretvariant1",0.4f));
        else
            renderers.registerEntityRenderer(KTEntities.ARROW_TURRET.get(), manager -> new EntityRenderer2<>(manager, new ArrowTurretModelv3<>(manager.bakeLayer(ArrowTurretModelv3.LAYER_LOCATION)), "arrow_turret2", 0.4f));
        if(KTurrets.useNewCobbleTurretModel.get())
            renderers.registerEntityRenderer(KTEntities.COBBLE_TURRET.get(), context -> new EntityRenderer2<>(context,new CobbleTurretNew<>(context.bakeLayer(CobbleTurretNew.LAYER_LOCATION)),"cobbleturrettexture",0.4f));
        else
            renderers.registerEntityRenderer(KTEntities.COBBLE_TURRET.get(), manager -> new EntityRenderer2<>(manager, new CobbleTurretModelv4<>(manager.bakeLayer(CobbleTurretModelv4.LAYER_LOCATION)), "cobble_turret2", 0.2f));
        renderers.registerEntityRenderer(KTEntities.GAUSS_TURRET.get(), manager -> new EntityRenderer2<>(manager, new GaussTurretModelv2<>(manager.bakeLayer(GaussTurretModelv2.LAYER_LOCATION)), "gaussturret", 0.2f));
        if(KTurrets.useNewBrickTurretModel.get())
            renderers.registerEntityRenderer(KTEntities.BRICK_TURRET.get(), context -> new EntityRenderer2<>(context,new BrickTurretModelNew<>(context.bakeLayer(BrickTurretModelNew.LAYER_LOCATION)),"brickturrettexture",0.4f));
        else
            renderers.registerEntityRenderer(KTEntities.BRICK_TURRET.get(), manager -> new EntityRenderer2<>(manager, new BrickTurretModelv2<>(manager.bakeLayer(BrickTurretModelv2.LAYER_LOCATION)), "brick_turret", 0.4f));
        if(KTurrets.useNewFireballTurretModel.get())
            renderers.registerEntityRenderer(KTEntities.FIRE_CHARGE_TURRET.get(), context -> new EntityRenderer2<>(context,new FireballTurretModelNew<>(context.bakeLayer(FireballTurretModelNew.LAYER_LOCATION)),"fireballturretnew",0.4f));
        else
            renderers.registerEntityRenderer(KTEntities.FIRE_CHARGE_TURRET.get(), manager -> new EntityRenderer2<>(manager, new FireballTurretModelv4<>(manager.bakeLayer(FireballTurretModelv4.LAYER_LOCATION)), "fireball_turret", 0.3f));
        if(KTurrets.useNewBulletTurretModel.get())
            renderers.registerEntityRenderer(KTEntities.BULLET_TURRET.get(), context -> new EntityRenderer2<>(context,new BulletTurretModelNew<>(context.bakeLayer(BulletTurretModelNew.LAYER_LOCATION)),"bulletturrettexture",0.4f));
        else
            renderers.registerEntityRenderer(KTEntities.BULLET_TURRET.get(), manager -> new EntityRenderer2<>(manager, new BulletTurretModelv4<>(manager.bakeLayer(BulletTurretModelv4.LAYER_LOCATION)), "bullet_turret4", 0.4f));

        renderers.registerEntityRenderer(KTEntities.BRICK_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new BrickDroneModel<>(p_174010_.bakeLayer(BrickDroneModel.LAYER_LOCATION)), "brick_drone", 0.2f));
        renderers.registerEntityRenderer(KTEntities.BULLET_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new BulletDroneModel<>(p_174010_.bakeLayer(BulletDroneModel.LAYER_LOCATION)), "bullet_drone", 0.2f));
        renderers.registerEntityRenderer(KTEntities.COBBLE_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new CobbleDroneModelv2<>(p_174010_.bakeLayer(CobbleDroneModelv2.LAYER_LOCATION)), "cobble_drone", 0.2f));
        renderers.registerEntityRenderer(KTEntities.ARROW_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new ArrowDroneModel<>(p_174010_.bakeLayer(ArrowDroneModel.LAYER_LOCATION)), "arrow_drone", 0.2f));
        renderers.registerEntityRenderer(KTEntities.GAUSS_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new GaussDroneModel<>(p_174010_.bakeLayer(GaussDroneModel.LAYER_LOCATION)), "gauss_drone", 0.2f));
        renderers.registerEntityRenderer(KTEntities.FIRECHARGE_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new FireballDroneModel<>(p_174010_.bakeLayer(FireballDroneModel.LAYER_LOCATION)), "firecharge_drone", 0.2f));

        renderers.registerEntityRenderer(KTEntities.STORAGE_DRONE.get(), p_174010_ -> new EntityRenderer2<>(p_174010_, new StorageDroneModel(p_174010_.bakeLayer(StorageDroneModel.LAYER_LOCATION)), "storage_drone", 0.4f));

        renderers.registerEntityRenderer(KTEntities.BRICK.get(), BrickRenderer::new);
        renderers.registerEntityRenderer(KTEntities.GAUSS_BULLET.get(), GaussBulletRenderer::new);
        renderers.registerEntityRenderer(KTEntities.COBBLESTONE.get(), CobblestoneRenderer::new);
        renderers.registerEntityRenderer(KTEntities.BULLET.get(), BulletRenderer::new);
    }
}
