package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.ArrowTurretContainer;
import dev.buildtool.kturrets.ArrowTurretScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    @SubscribeEvent
    public static void register(FMLClientSetupEvent clientSetupEvent) {
        ScreenManager.register(TContainers.ARROW_TURRET, (ScreenManager.IScreenFactory<ArrowTurretContainer, ArrowTurretScreen>) (p_create_1_, p_create_2_, p_create_3_) -> new ArrowTurretScreen(p_create_1_, p_create_2_, p_create_3_, true));
    }
}
