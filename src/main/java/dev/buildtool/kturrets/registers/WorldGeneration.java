package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = KTurrets.ID)
public class WorldGeneration {
    @SubscribeEvent
    public static void loadingBiomes(BiomeLoadingEvent biomeLoadingEvent) {
        biomeLoadingEvent.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> KTurrets.CONFIGURED_TITANIUM_ORE);
    }
}
