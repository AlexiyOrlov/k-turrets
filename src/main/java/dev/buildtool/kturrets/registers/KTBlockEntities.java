package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.ReloaderBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collections;

public class KTBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, KTurrets.ID);
    public static final RegistryObject<BlockEntityType<?>> RELOADER = BLOCK_ENTITIES.register("reloader", () -> new BlockEntityType<>(ReloaderBlockEntity::new, Collections.singleton(KTBlocks.RELOADER.get()), null));
}
