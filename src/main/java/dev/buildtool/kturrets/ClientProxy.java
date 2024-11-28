package dev.buildtool.kturrets;

import dev.buildtool.kturrets.packets.AmmoCheck;
import dev.buildtool.kturrets.packets.PickupParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;


public class ClientProxy {

    public static final DustParticleOptions PARTICLE = new DustParticleOptions(Vec3.fromRGB24(0xff47CCF0).toVector3f(), 1);

    public Runnable syncAmmoStatus(AmmoCheck ammoCheck) {
        return () -> {
            ClientLevel clientLevel = Minecraft.getInstance().level;
            Entity entity = clientLevel.getEntity(ammoCheck.unit);
            if (entity instanceof Turret turret) {
                turret.noAmmo = ammoCheck.noAmmo;
            }
        };
    }

    public Runnable pickupParticles(PickupParticles pickupParticles)
    {
        return () -> {
            ClientLevel clientLevel=Minecraft.getInstance().level;
            clientLevel.addParticle(PARTICLE,pickupParticles.x,pickupParticles.y,pickupParticles.z,0,0,0);
        };
    }
}
