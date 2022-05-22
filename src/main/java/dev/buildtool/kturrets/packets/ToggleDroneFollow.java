package dev.buildtool.kturrets.packets;

public class ToggleDroneFollow {
    public boolean follow;
    public int id;

    public ToggleDroneFollow(boolean follow, int id) {
        this.follow = follow;
        this.id = id;
    }
}
