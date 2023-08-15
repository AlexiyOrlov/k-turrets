package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.DeferredRegister;

public class KBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Block.class, KTurrets.ID);
    public static final RegistryObject<Block> TITANIUM_ORE = BLOCKS.register("titanium_ore", () -> new OreBlock(AbstractBlock.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3, 3).lightLevel(value -> FMLLoader.isProduction() ? 0 : 15)));
}
