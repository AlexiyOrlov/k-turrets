package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.EntityRenderer2;
import dev.buildtool.kturrets.arrow.ArrowTurretContainer;
import dev.buildtool.kturrets.arrow.ArrowTurretModel;
import dev.buildtool.kturrets.arrow.ArrowTurretScreen;
import dev.buildtool.kturrets.brick.BrickRenderer;
import dev.buildtool.kturrets.brick.BrickTurretContainer;
import dev.buildtool.kturrets.brick.BrickTurretModel;
import dev.buildtool.kturrets.brick.BrickTurretScreen;
import dev.buildtool.kturrets.bullet.BulletRenderer;
import dev.buildtool.kturrets.bullet.BulletScreen;
import dev.buildtool.kturrets.bullet.BulletTurretContainer;
import dev.buildtool.kturrets.bullet.BulletTurretModel2;
import dev.buildtool.kturrets.firecharge.FireChargeScreen;
import dev.buildtool.kturrets.firecharge.FireChargeTurretContainer;
import dev.buildtool.kturrets.firecharge.FireChargeTurretModel;
import dev.buildtool.kturrets.gauss.GaussTurretContainer;
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
        ScreenManager.register(TContainers.ARROW_TURRET, (ScreenManager.IScreenFactory<ArrowTurretContainer, ArrowTurretScreen>) (p_create_1_, p_create_2_, p_create_3_) -> new ArrowTurretScreen(p_create_1_, p_create_2_, p_create_3_, true));
        ScreenManager.register(TContainers.BULLET_TURRET, (ScreenManager.IScreenFactory<BulletTurretContainer, BulletScreen>) (p1, p2, p3) -> new BulletScreen(p1, p2, p3, true));
        ScreenManager.register(TContainers.FIRE_CHARGE_TURRET, (ScreenManager.IScreenFactory<FireChargeTurretContainer, FireChargeScreen>) (p1, p2, p3) -> new FireChargeScreen(p1, p2, p3, true));
        ScreenManager.register(TContainers.BRICK_TURRET, (ScreenManager.IScreenFactory<BrickTurretContainer, BrickTurretScreen>) (p1, p2, p3) -> new BrickTurretScreen(p1, p2, p3, true));
        ScreenManager.register(TContainers.GAUSS_TURRET, (ScreenManager.IScreenFactory<GaussTurretContainer, GaussTurretScreen>) (p1, p2, p3) -> new GaussTurretScreen(p1, p2, p3, true));
        RenderingRegistry.registerEntityRenderingHandler(TEntities.ARROW_TURRET, manager -> new EntityRenderer2<>(manager, new ArrowTurretModel(), "arrowturret", false, 0.6f));
        RenderingRegistry.registerEntityRenderingHandler(TEntities.BULLET, BulletRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(TEntities.BULLET_TURRET, manager -> new EntityRenderer2<>(manager, new BulletTurretModel2(), "bulletturret", false, 0.4f));
        RenderingRegistry.registerEntityRenderingHandler(TEntities.FIRE_CHARGE_TURRET, manager -> new EntityRenderer2<>(manager, new FireChargeTurretModel(), "firechargeturret", false, 0.6f));
        RenderingRegistry.registerEntityRenderingHandler(TEntities.BRICK, BrickRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(TEntities.BRICK_TURRET, manager -> new EntityRenderer2<>(manager, new BrickTurretModel(), "brickturret", false, 0.7f));
    }
}
