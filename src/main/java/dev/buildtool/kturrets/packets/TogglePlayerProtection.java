package dev.buildtool.kturrets.packets;

public class TogglePlayerProtection {
    public boolean protect;
    public int id;

    public TogglePlayerProtection(boolean protect, int id) {
        this.protect = protect;
        this.id = id;
    }

    public TogglePlayerProtection() {
    }
}
