package dev.buildtool.kturrets.packets;

public class ToggleGuardingArea {
    public int droneId;
    public boolean guard;

    public ToggleGuardingArea(int droneId, boolean guard) {
        this.droneId = droneId;
        this.guard = guard;
    }

}
