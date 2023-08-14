package dev.buildtool.kturrets.packets;

public class RemovePlayerException {
    public int turretId;
    public String playerName;

    public RemovePlayerException(int turretId, String playerName) {
        this.turretId = turretId;
        this.playerName = playerName;
    }
}
