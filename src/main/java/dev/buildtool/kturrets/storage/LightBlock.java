package dev.buildtool.kturrets.storage;

import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;

public class LightBlock extends AirBlock
{
    public LightBlock(Properties pProperties) {
        super(pProperties.lightLevel(value -> 15).noCollission().noOcclusion().noLootTable().air());
    }
}
