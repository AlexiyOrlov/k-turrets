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
import dev.buildtool.kturrets.firecharge.FireChargeScreen;
import dev.buildtool.kturrets.firecharge.FireChargeTurretContainer;
import dev.buildtool.kturrets.firecharge.FirechargeTurretv2;
import dev.buildtool.kturrets.gauss.GaussBulletRenderer;
import dev.buildtool.kturrets.gauss.GaussTurretContainer;
import dev.buildtool.kturrets.gauss.GaussTurretModelv2;
import dev.buildtool.kturrets.gauss.GaussTurretScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
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
        EntityRenderers.register(TEntities.ARROW_TURRET, manager -> new EntityRenderer2<>(manager, new ArrowTurretv2<>(manager.bakeLayer(ArrowTurretv2.LAYER_LOCATION)), "arrow_turret", false, 0.4f));
        EntityRenderers.register(TEntities.BULLET, BulletRenderer::new);
        EntityRenderers.register(TEntities.BULLET_TURRET, manager -> new EntityRenderer2<>(manager, new BulletTurretv2<>(manager.bakeLayer(BulletTurretv2.LAYER_LOCATION)), "bullet_turret", false, 0.4f));
        EntityRenderers.register(TEntities.FIRE_CHARGE_TURRET, manager -> new EntityRenderer2<>(manager, new FirechargeTurretv2<>(manager.bakeLayer(FirechargeTurretv2.LAYER_LOCATION)), "firecharge_turret", false, 0.3f));
        EntityRenderers.register(TEntities.BRICK, BrickRenderer::new);
        EntityRenderers.register(TEntities.BRICK_TURRET, manager -> new EntityRenderer2<>(manager, new BrickTurretModelv2<>(manager.bakeLayer(BrickTurretModelv2.LAYER_LOCATION)), "brick_turret", false, 0.4f));
        EntityRenderers.register(TEntities.GAUSS_TURRET, manager -> new EntityRenderer2<>(manager, new GaussTurretModelv2<>(manager.bakeLayer(GaussTurretModelv2.LAYER_LOCATION)), "gaussturret", false, 0.2f));
        EntityRenderers.register(TEntities.GAUSS_BULLET, GaussBulletRenderer::new);
        EntityRenderers.register(TEntities.COBBLE_TURRET, manager -> new EntityRenderer2<>(manager, new CobbleTurretv2<>(manager.bakeLayer(CobbleTurretv2.LAYER_LOCATION)), "cobble_turret", false, 0.2f));
        EntityRenderers.register(TEntities.COBBLESTONE, CobblestoneRenderer::new);
        ForgeHooksClient.registerLayerDefinition(ArrowTurretv2.LAYER_LOCATION, ArrowTurretv2::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(BrickTurretModelv2.LAYER_LOCATION, BrickTurretModelv2::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(BulletTurretv2.LAYER_LOCATION, BulletTurretv2::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(CobbleTurretv2.LAYER_LOCATION, CobbleTurretv2::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(FirechargeTurretv2.LAYER_LOCATION, FirechargeTurretv2::createBodyLayer);
        ForgeHooksClient.registerLayerDefinition(GaussTurretModelv2.LAYER_LOCATION, GaussTurretModelv2::createBodyLayer);
    }
}
