package dev.buildtool.kturrets.packets;

import dev.buildtool.kturrets.Drone;

public class SetBehavior {
    public int drone;
    public Drone.Behavior behavior;

    public SetBehavior(int drone, Drone.Behavior behavior) {
        this.drone = drone;
        this.behavior = behavior;
    }
}
