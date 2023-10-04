package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.EntityRenderer2;
import dev.buildtool.kturrets.arrow.*;
import dev.buildtool.kturrets.brick.*;
import dev.buildtool.kturrets.bullet.*;
import dev.buildtool.kturrets.cobble.*;
import dev.buildtool.kturrets.fireball.*;
import dev.buildtool.kturrets.gauss.*;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SuppressWarnings("RedundantCast")
    @SubscribeEvent
    public static void register(FMLClientSetupEvent clientSetupEvent) {
        ScreenManager.register(KContainers.ARROW_TURRET, (ScreenManager.IScreenFactory<ArrowTurretContainer, ArrowTurretScreen>) (p_create_1_, p_create_2_, p_create_3_) -> new ArrowTurretScreen(p_create_1_, p_create_2_, p_create_3_, true));
        ScreenManager.register(KContainers.BULLET_TURRET, (ScreenManager.IScreenFactory<BulletTurretContainer, BulletScreen>) (p1, p2, p3) -> new BulletScreen(p1, p2, p3, true));
        ScreenManager.register(KContainers.FIRE_CHARGE_TURRET, (ScreenManager.IScreenFactory<FireballTurretContainer, FireballTurretScreen>) (p1, p2, p3) -> new FireballTurretScreen(p1, p2, p3, true));
        ScreenManager.register(KContainers.BRICK_TURRET, (ScreenManager.IScreenFactory<BrickTurretContainer, BrickTurretScreen>) (p1, p2, p3) -> new BrickTurretScreen(p1, p2, p3, true));
        ScreenManager.register(KContainers.GAUSS_TURRET, (ScreenManager.IScreenFactory<GaussTurretContainer, GaussTurretScreen>) (p1, p2, p3) -> new GaussTurretScreen(p1, p2, p3, true));
        ScreenManager.register(KContainers.COBBLE_TURRET, (ScreenManager.IScreenFactory<CobbleTurretContainer, CobbleTurretScreen>) (p1, p2, p3) -> new CobbleTurretScreen(p1, p2, p3, true));
        ScreenManager.register(KContainers.ARROW_DRONE, (ScreenManager.IScreenFactory<ArrowDroneContainer, ArrowDroneScreen>) (p1, p2, p3) -> new ArrowDroneScreen(p1, p2, p3, true));
        ScreenManager.register(KContainers.BULLET_DRONE, (ScreenManager.IScreenFactory<BulletDroneContainer, BulletDroneScreen>) (p1, p2, p3) -> new BulletDroneScreen(p1, p2, p3, true));
        ScreenManager.register(KContainers.FIRECHARGE_DRONE, (ScreenManager.IScreenFactory<FireballDroneContainer, FireballDroneScreen>) (p1, p2, p3) -> new FireballDroneScreen(p1, p2, p3, true));
        ScreenManager.register(KContainers.BRICK_DRONE, (ScreenManager.IScreenFactory<BrickDroneContainer, BrickDroneScreen>) (p1, p2, p3) -> new BrickDroneScreen(p1, p2, p3, true));
        ScreenManager.register(KContainers.GAUSS_DRONE, (ScreenManager.IScreenFactory<GaussDroneContainer, GaussDroneScreen>) (p1, p2, p3) -> new GaussDroneScreen(p1, p2, p3, true));
        ScreenManager.register(KContainers.COBBLE_DRONE, (ScreenManager.IScreenFactory<CobbleDroneContainer, CobbleDroneScreen>) (p1, p2, p3) -> new CobbleDroneScreen(p1, p2, p3, true));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.ARROW_TURRET, manager -> new EntityRenderer2<>(manager, new ArrowTurretv2(), "arrow_turret", false, 0.4f));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.BULLET, BulletRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(KEntities.BULLET_TURRET, manager -> new EntityRenderer2<>(manager, new BulletTurretv2(), "bullet_turret", false, 0.4f));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.FIRE_CHARGE_TURRET, manager -> new EntityRenderer2<>(manager, new FireballTurretModelv3(), "fireball_turret", false, 0.3f));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.BRICK, BrickRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(KEntities.BRICK_TURRET, manager -> new EntityRenderer2<>(manager, new BrickTurretModelv2(), "brick_turret", false, 0.4f));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.GAUSS_TURRET, manager -> new EntityRenderer2<>(manager, new GaussTurretModelv2(), "gaussturret", false, 0.2f));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.GAUSS_BULLET, GaussBulletRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(KEntities.COBBLE_TURRET, manager -> new EntityRenderer2<>(manager, new CobbleTurretv2(), "cobble_turret", false, 0.2f));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.COBBLESTONE, CobblestoneRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(KEntities.ARROW_DRONE, manager -> new EntityRenderer2<>(manager, new ArrowDroneModel(), "arrow_drone", false, 0.2f));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.BRICK_DRONE, manager -> new EntityRenderer2<>(manager, new BrickDroneModel(), "brick_drone", false, 0.2f));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.BULLET_DRONE, manager -> new EntityRenderer2<>(manager, new BulletDroneModel(), "bullet_drone", false, 0.2f));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.COBBLE_DRONE, manager -> new EntityRenderer2<>(manager, new CobbleDroneModel(), "cobble_drone", false, 0.2f));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.FIRECHARGE_DRONE, manager -> new EntityRenderer2<>(manager, new FireballDroneModel(), "firecharge_drone", false, 0.2f));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.GAUSS_DRONE, manager -> new EntityRenderer2<>(manager, new GaussDroneModel(), "gauss_drone", false, 0.2f));
    }
}
