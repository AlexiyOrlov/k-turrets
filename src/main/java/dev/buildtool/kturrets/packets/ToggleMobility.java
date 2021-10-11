package dev.buildtool.kturrets.packets;

public class ToggleMobility {
    public boolean mobile;
    public int id;

    public ToggleMobility(boolean mobile, int id) {
        this.mobile = mobile;
        this.id = id;
    }

    public ToggleMobility() {
    }
}
