package dev.buildtool.kturrets.packets;

import net.minecraft.nbt.CompoundNBT;

public class TurretTargets {
    public CompoundNBT targets;
    public int turretID;

    public TurretTargets() {
    }

    public TurretTargets(CompoundNBT targets, int id) {
        this.targets = targets;
        turretID = id;
    }
}
