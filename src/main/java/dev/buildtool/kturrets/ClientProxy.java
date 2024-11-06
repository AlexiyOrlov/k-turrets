package dev.buildtool.kturrets;

import dev.buildtool.kturrets.packets.AmmoCheck;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;


public class ClientProxy {
    public Runnable syncAmmoStatus(AmmoCheck ammoCheck) {
        return () -> {
            ClientLevel clientLevel = Minecraft.getInstance().level;
            Entity entity = clientLevel.getEntity(ammoCheck.unit);
            if (entity instanceof Turret turret) {
                turret.noAmmo = ammoCheck.noAmmo;
            }
        };
    }
}
