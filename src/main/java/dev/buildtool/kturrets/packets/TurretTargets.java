package dev.buildtool.kturrets.packets;

import net.minecraft.nbt.CompoundTag;

public class TurretTargets {
    public CompoundTag targets;
    public int turretID;

    public TurretTargets() {
    }

    public TurretTargets(CompoundTag targets, int id) {
        this.targets = targets;
        turretID = id;
    }
}
