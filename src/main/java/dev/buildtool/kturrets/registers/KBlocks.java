package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.ReloaderBlock;
import dev.buildtool.kturrets.storage.LightBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class KBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, KTurrets.ID);

    public static final RegistryObject<Block> TITANIUM_ORE = BLOCKS.register("titanium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(3, 3).lightLevel(value -> FMLLoader.isProduction() ? 0 : 15)));
    public static final RegistryObject<Block> DEEP_SLATE_TITANIUM_ORE = BLOCKS.register("deepslate_titanium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(TITANIUM_ORE.get()).strength(4.5f, 3).sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> RELOADER = BLOCKS.register("reloader", () -> new ReloaderBlock(BlockBehaviour.Properties.of().strength(3, 3).sound(SoundType.STONE)));
    public static final RegistryObject<Block> LIGHT_BLOCK=BLOCKS.register("light",() -> new LightBlock(BlockBehaviour.Properties.of()));
}
