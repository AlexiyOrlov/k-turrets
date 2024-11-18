package dev.buildtool.kturrets.registers;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;


public interface UnitLimitCapability {

    int getTurretCount(UUID player);

    int getDroneCount(UUID player);

    void setTurretCount(UUID player, int count);

    void setDroneCount(UUID player, int count);

    ArrayListMultimap<UUID, Integer> getPlayersToLimits();

    class Implementation implements UnitLimitCapability {

        public ArrayListMultimap<UUID,Integer> limitMap=ArrayListMultimap.create(1,2);

        @Override
        public int getTurretCount(UUID player) {
            List<Integer> list = limitMap.get(player);
            if(list.isEmpty())
                return 0;
            return list.get(0);
        }

        @Override
        public int getDroneCount(UUID player) {
            List<Integer> list = limitMap.get(player);
            if(list.isEmpty())
                return 0;
            return list.get(1);
        }

        @Override
        public void setTurretCount(UUID player, int count) {
            List<Integer> list = limitMap.get(player);
            if(list.isEmpty())
            {
                list.add(count);
                list.add(0);
            }
            else
                list.set(0,count);
        }

        @Override
        public void setDroneCount(UUID player, int count) {
            List<Integer> list = limitMap.get(player);
            if(list.isEmpty())
            {
                list.add(0);
                list.add(count);
            }
            else
                list.set(1,count);
        }

        @Override
        public ArrayListMultimap<UUID, Integer> getPlayersToLimits() {
            return limitMap;
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
            int number=0;
            for (Map.Entry<UUID, Collection<Integer>> uuidCollectionEntry : unitLimitCapability.getPlayersToLimits().asMap().entrySet()) {
                List<Integer> collection= (List<Integer>) uuidCollectionEntry.getValue();
                UUID uuid=uuidCollectionEntry.getKey();
                compoundTag.putUUID("Player #"+number+" UUID",uuid);
                compoundTag.putInt("Player "+uuid+" turret count",collection.get(0));
                compoundTag.putInt("Player "+uuid+" drone count",collection.get(1));
                number++;
            }
            compoundTag.putInt("Turret entries",number);
            return compoundTag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            int turretEntries=nbt.getInt("Turret entries");
            for (int i = 0; i < turretEntries; i++) {
                UUID uuid=nbt.getUUID("Player #"+i+" UUID");
                int turretSlots=nbt.getInt("Player "+uuid+" turret count");
                int droneSlots=nbt.getInt("Player "+uuid+" drone count");
                unitLimitCapability.getPlayersToLimits().put(uuid,turretSlots);
                unitLimitCapability.getPlayersToLimits().put(uuid,droneSlots);
            }
        }
    }

    @SubscribeEvent
    public static void registerCapability(RegisterCapabilitiesEvent capabilitiesEvent) {
        capabilitiesEvent.register(Provider.class);
    }
}
