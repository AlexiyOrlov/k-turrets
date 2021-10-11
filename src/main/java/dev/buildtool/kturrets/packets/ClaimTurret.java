package dev.buildtool.kturrets.packets;

import java.util.UUID;

public class ClaimTurret {
    public int id;
    public UUID person;

    public ClaimTurret(int id, UUID person) {
        this.id = id;
        this.person = person;
    }

    public ClaimTurret() {
    }
}
