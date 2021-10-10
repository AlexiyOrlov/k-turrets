package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.arrow.ArrowTurretContainer;
import dev.buildtool.kturrets.arrow.ArrowTurretModel;
import dev.buildtool.kturrets.arrow.ArrowTurretRenderer;
import dev.buildtool.kturrets.arrow.ArrowTurretScreen;
import dev.buildtool.kturrets.bullet.BulletRenderer;
import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SubscribeEvent
    public static void register(FMLClientSetupEvent clientSetupEvent) {
        ScreenManager.register(TContainers.ARROW_TURRET, (ScreenManager.IScreenFactory<ArrowTurretContainer, ArrowTurretScreen>) (p_create_1_, p_create_2_, p_create_3_) -> new ArrowTurretScreen(p_create_1_, p_create_2_, p_create_3_, true));
        ScreenManager.register(TContainers.BULLET_TURRET, (p1, p2, p3) -> new ContainerScreen2<>(p1, p2, p3, true));
        RenderingRegistry.registerEntityRenderingHandler(TEntities.ARROW_TURRET, manager -> new ArrowTurretRenderer(manager, new ArrowTurretModel(), "arrowturret", false, 0.6f));
        RenderingRegistry.registerEntityRenderingHandler(TEntities.BULLET, BulletRenderer::new);
    }
}
