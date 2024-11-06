package dev.buildtool.kturrets.packets;

public class AmmoCheck {
    public boolean noAmmo;
    public int unit;

    public AmmoCheck(boolean noAmmo, int unit) {
        this.noAmmo = noAmmo;
        this.unit = unit;
    }
}
