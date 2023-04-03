package dev.buildtool.kturrets.registers;

import com.google.common.base.Suppliers;
import dev.buildtool.kturrets.KTurrets;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class WorldGeneration {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE_REGISTER = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, KTurrets.ID);
    static final Supplier<List<OreConfiguration.TargetBlockState>> TARGET_BLOCKSTATES = Suppliers.memoize(() -> List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, KBlocks.TITANIUM_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, KBlocks.DEEP_SLATE_TITANIUM_ORE.get().defaultBlockState())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> CONFIGURED_ORE_FEATURE = CONFIGURED_FEATURE_REGISTER.register("titanium_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(TARGET_BLOCKSTATES.get(), 11)));

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURE_REGISTER = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, KTurrets.ID);

    static {
        PLACED_FEATURE_REGISTER.register("titanium_ore", () -> new PlacedFeature(CONFIGURED_ORE_FEATURE.getHolder().get(), List.of(CountPlacement.of(29), InSquarePlacement.spread(), HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.absolute(384)))));
    }
}
