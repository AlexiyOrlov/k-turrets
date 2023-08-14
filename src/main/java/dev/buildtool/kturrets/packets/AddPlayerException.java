package dev.buildtool.kturrets.packets;

public class AddPlayerException {
    public int turretId;
    public String playerName;

    public AddPlayerException(int turretId, String playerName) {
        this.turretId = turretId;
        this.playerName = playerName;
    }
}
