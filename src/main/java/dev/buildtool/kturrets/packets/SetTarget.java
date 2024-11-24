package dev.buildtool.kturrets.packets;

public class SetTarget {
    public boolean state;
    public String id;
    public int unit;

    public SetTarget(boolean state, String id, int unit) {
        this.state = state;
        this.id = id;
        this.unit = unit;
    }
}
