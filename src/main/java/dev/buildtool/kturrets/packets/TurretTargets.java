package dev.buildtool.kturrets.packets;

import java.util.List;

public class TurretTargets {
    public List<String> targets;
    public int turretID;

    public TurretTargets() {
    }

    public TurretTargets(List<String> targets, int id) {
        this.targets = targets;
        turretID = id;
    }
}
