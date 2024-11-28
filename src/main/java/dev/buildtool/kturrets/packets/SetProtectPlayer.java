package dev.buildtool.kturrets.packets;

public class SetProtectPlayer {
    public int unitId;
    public boolean protect;

    public SetProtectPlayer(int unitId, boolean protect) {
        this.unitId = unitId;
        this.protect = protect;
    }
}
