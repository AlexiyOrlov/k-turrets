package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.EntityRenderer2;
import dev.buildtool.kturrets.arrow.ArrowTurretContainer;
import dev.buildtool.kturrets.arrow.ArrowTurretScreen;
import dev.buildtool.kturrets.arrow.ArrowTurretv2;
import dev.buildtool.kturrets.brick.BrickRenderer;
import dev.buildtool.kturrets.brick.BrickTurretContainer;
import dev.buildtool.kturrets.brick.BrickTurretModelv2;
import dev.buildtool.kturrets.brick.BrickTurretScreen;
import dev.buildtool.kturrets.bullet.BulletRenderer;
import dev.buildtool.kturrets.bullet.BulletScreen;
import dev.buildtool.kturrets.bullet.BulletTurretContainer;
import dev.buildtool.kturrets.bullet.BulletTurretv2;
import dev.buildtool.kturrets.cobble.CobbleTurretContainer;
import dev.buildtool.kturrets.cobble.CobbleTurretScreen;
import dev.buildtool.kturrets.cobble.CobbleTurretv2;
import dev.buildtool.kturrets.cobble.CobblestoneRenderer;
import dev.buildtool.kturrets.firecharge.FireballTurretContainer;
import dev.buildtool.kturrets.firecharge.FireballTurretScreen;
import dev.buildtool.kturrets.firecharge.FirechargeTurretv2;
import dev.buildtool.kturrets.gauss.GaussBulletRenderer;
import dev.buildtool.kturrets.gauss.GaussTurretContainer;
import dev.buildtool.kturrets.gauss.GaussTurretModelv2;
import dev.buildtool.kturrets.gauss.GaussTurretScreen;
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
        RenderingRegistry.registerEntityRenderingHandler(KEntities.ARROW_TURRET, manager -> new EntityRenderer2<>(manager, new ArrowTurretv2(), "arrow_turret", false, 0.4f));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.BULLET, BulletRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(KEntities.BULLET_TURRET, manager -> new EntityRenderer2<>(manager, new BulletTurretv2(), "bullet_turret", false, 0.4f));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.FIRE_CHARGE_TURRET, manager -> new EntityRenderer2<>(manager, new FirechargeTurretv2(), "firecharge_turret", false, 0.3f));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.BRICK, BrickRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(KEntities.BRICK_TURRET, manager -> new EntityRenderer2<>(manager, new BrickTurretModelv2(), "brick_turret", false, 0.4f));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.GAUSS_TURRET, manager -> new EntityRenderer2<>(manager, new GaussTurretModelv2(), "gaussturret", false, 0.2f));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.GAUSS_BULLET, GaussBulletRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(KEntities.COBBLE_TURRET, manager -> new EntityRenderer2<>(manager, new CobbleTurretv2(), "cobble_turret", false, 0.2f));
        RenderingRegistry.registerEntityRenderingHandler(KEntities.COBBLESTONE, CobblestoneRenderer::new);
    }
}
