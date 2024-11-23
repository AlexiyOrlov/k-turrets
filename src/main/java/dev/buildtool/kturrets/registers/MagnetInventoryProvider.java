package dev.buildtool.kturrets.registers;

import dev.buildtool.satako.ItemHandler;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MagnetInventoryProvider implements ICapabilitySerializable<CompoundTag> {
    ItemHandler itemHandler=new ItemHandler(27);
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap== ForgeCapabilities.ITEM_HANDLER)
        {
            return LazyOptional.of(() -> itemHandler).cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag t=new CompoundTag();
        t.put("Filters",itemHandler.serializeNBT());
        return t;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("Filters"));
    }
}
