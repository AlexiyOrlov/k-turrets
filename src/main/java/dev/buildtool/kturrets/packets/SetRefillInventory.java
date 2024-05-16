package dev.buildtool.kturrets.packets;

public class SetRefillInventory {
    public boolean refill;
    public int turretId;

    public SetRefillInventory(boolean refill, int id) {
        this.refill = refill;
        turretId = id;
    }
}
