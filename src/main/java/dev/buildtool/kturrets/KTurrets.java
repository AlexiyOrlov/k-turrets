package dev.buildtool.kturrets;

import dev.buildtool.kturrets.packets.TurretTargets;
import dev.buildtool.kturrets.registers.TContainers;
import dev.buildtool.kturrets.registers.TEntities;
import dev.buildtool.kturrets.registers.TItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@Mod(KTurrets.ID)
public class KTurrets {
    public static final String ID = "k-turrets";
    static private final String NP = "1.0";
    public static SimpleChannel channel;

    public KTurrets() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        TEntities.ENTITIES.register(eventBus);
        TItems.ITEMS.register(eventBus);
        TContainers.CONTAINERS.register(eventBus);
        channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(ID, "network"), () -> NP, NP::equals, NP::equals);
        int packetIndex = 0;
        channel.registerMessage(packetIndex++, TurretTargets.class, (turretTargets, packetBuffer) -> {
            packetBuffer.writeInt(turretTargets.turretID);
            turretTargets.targets.forEach(packetBuffer::writeUtf);
        }, packetBuffer -> {
            List<String> strings = new ArrayList<>(40);
            int id = packetBuffer.readInt();
            while (packetBuffer.isReadable()) {
                strings.add(packetBuffer.readUtf());
            }
            return new TurretTargets(strings, id);
        }, (turretTargets, contextSupplier) -> {
            NetworkEvent.Context context = contextSupplier.get();
            ServerWorld serverWorld = context.getSender().getLevel();
            Entity entity = serverWorld.getEntity(turretTargets.turretID);
            if (entity instanceof Turret) {
                Turret turret = (Turret) entity;
                List<EntityType<?>> targets = new ArrayList<>(40);
                turretTargets.targets.forEach(s -> {
                    EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(s));
                    targets.add(entityType);
                });
                turret.setTargets(turret.encodeTargets(targets));
                context.setPacketHandled(true);
            }
        });
    }
}
