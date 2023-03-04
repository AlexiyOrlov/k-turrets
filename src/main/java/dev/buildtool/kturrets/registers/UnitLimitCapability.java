package dev.buildtool.kturrets.registers;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface UnitLimitCapability {

    int getTurretCount();

    int getDroneCount();

    void setTurretCount(int count);

    void setDroneCount(int count);

    class Implementation implements UnitLimitCapability {

        public int droneCount, turretCount;


        @Override
        public int getTurretCount() {
            return turretCount;
        }

        @Override
        public int getDroneCount() {
            return droneCount;
        }

        @Override
        public void setTurretCount(int count) {
            turretCount = Math.max(0, count);
        }

        @Override
        public void setDroneCount(int count) {
            droneCount = Math.max(0, count);
        }
    }

    class Provider implements ICapabilitySerializable<CompoundTag> {

        UnitLimitCapability unitLimitCapability = new Implementation();

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull net.minecraftforge.common.capabilities.Capability<T> cap, @Nullable Direction side) {
            if (cap == RegisterCapability.unitCapability)
                return LazyOptional.of(() -> unitLimitCapability).cast();
            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putInt("Current drone count", unitLimitCapability.getDroneCount());
            compoundTag.putInt("Current turret count", unitLimitCapability.getTurretCount());
            return compoundTag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            unitLimitCapability.setTurretCount(nbt.getInt("Current turret count"));
            unitLimitCapability.setDroneCount(nbt.getInt("Current drone count"));
        }
    }

    @SubscribeEvent
    public static void registerCapability(RegisterCapabilitiesEvent capabilitiesEvent) {
        capabilitiesEvent.register(Provider.class);
    }
}
