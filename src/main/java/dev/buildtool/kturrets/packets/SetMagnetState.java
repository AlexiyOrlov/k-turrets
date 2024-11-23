package dev.buildtool.kturrets.packets;

public class SetMagnetState {
    public int droneId;
    public boolean state;

    public SetMagnetState(int droneId, boolean state) {
        this.droneId = droneId;
        this.state = state;
    }
}
