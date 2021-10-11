package dev.buildtool.kturrets;

import dev.buildtool.kturrets.packets.*;
import dev.buildtool.kturrets.registers.TContainers;
import dev.buildtool.kturrets.registers.TEntities;
import dev.buildtool.kturrets.registers.TItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod(KTurrets.ID)
public class KTurrets {
    public static final String ID = "k-turrets";
    static private final String NP = "1.0";
    public static SimpleChannel channel;
    public static ForgeConfigSpec.DoubleValue ARROW_TURRET_HEALTH;
    public static ForgeConfigSpec.DoubleValue ARROW_TURRET_RANGE;
    public static ForgeConfigSpec.DoubleValue ARROW_TURRET_ARMOR;
    public static ForgeConfigSpec.IntValue ARROW_TURRET_RATE;
    public static ForgeConfigSpec.IntValue BULLET_TURRET_RATE;
    public static ForgeConfigSpec.IntValue CHARGE_TURRET_RATE;
    public static ForgeConfigSpec.DoubleValue BULLET_TURRET_HEALTH;
    public static ForgeConfigSpec.DoubleValue BULLET_TURRET_RANGE;
    public static ForgeConfigSpec.DoubleValue BULLET_TURRET_ARMOR;
    public static ForgeConfigSpec.DoubleValue CHARGE_TURRET_HEALTH;
    public static ForgeConfigSpec.DoubleValue CHARGE_TURRET_RANGE;
    public static ForgeConfigSpec.DoubleValue CHARGE_TURRET_ARMOR;

    public KTurrets() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        TEntities.ENTITIES.register(eventBus);
        TItems.ITEMS.register(eventBus);
        TContainers.CONTAINERS.register(eventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, new ForgeConfigSpec.Builder().configure(builder -> {
            builder.push("Arrow turret");
            ARROW_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, 500d);
            ARROW_TURRET_RANGE = builder.defineInRange("Range", 32d, 8d, 100d);
            ARROW_TURRET_ARMOR = builder.defineInRange("Armor", 0d, 0d, 100d);
            ARROW_TURRET_RATE = builder.defineInRange("Fire rate", 13, 10, 60);
            builder.pop();
            builder.push("Bullet turret");
            BULLET_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, 500d);
            BULLET_TURRET_RANGE = builder.defineInRange("Range", 32d, 8d, 100d);
            BULLET_TURRET_ARMOR = builder.defineInRange("Armor", 0d, 0d, 100d);
            BULLET_TURRET_RATE = builder.defineInRange("Fire rate", 13, 10, 60);
            builder.pop();
            builder.push("Fire charge turret");
            CHARGE_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, 500d);
            CHARGE_TURRET_RANGE = builder.defineInRange("Range", 32d, 8d, 100d);
            CHARGE_TURRET_ARMOR = builder.defineInRange("Armor", 0d, 0d, 100d);
            CHARGE_TURRET_RATE = builder.defineInRange("Fire rate", 13, 10, 60);
            builder.pop();
            return builder.build();
        }).getRight());

        channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(ID, "network"), () -> NP, NP::equals, NP::equals);
        int packetIndex = 0;
        channel.registerMessage(packetIndex++, TurretTargets.class, (turretTargets, packetBuffer) -> {
            packetBuffer.writeInt(turretTargets.turretID);
            packetBuffer.writeNbt(turretTargets.targets);
        }, packetBuffer -> {
            int id = packetBuffer.readInt();
            CompoundNBT compoundNBT = packetBuffer.readNbt();
            return new TurretTargets(compoundNBT, id);
        }, (turretTargets, contextSupplier) -> {
            NetworkEvent.Context context = contextSupplier.get();
            ServerWorld serverWorld = context.getSender().getLevel();
            Entity entity = serverWorld.getEntity(turretTargets.turretID);
            if (entity instanceof Turret) {
                Turret turret = (Turret) entity;
                turret.setTargets(turretTargets.targets);
                context.setPacketHandled(true);
            }
        });
        channel.registerMessage(packetIndex++, DismantleTurret.class, (dismantleTurret, packetBuffer) -> packetBuffer.writeInt(dismantleTurret.id),
                packetBuffer -> new DismantleTurret(packetBuffer.readInt()),
                (dismantleTurret, contextSupplier) -> {
                    ServerWorld serverWorld = contextSupplier.get().getSender().getLevel();
                    Entity entity = serverWorld.getEntity(dismantleTurret.id);
                    if (entity instanceof Turret) {
                        Turret turret = (Turret) entity;
                        turret.getContainedItems().forEach(itemHandler -> InventoryHelper.dropContents(serverWorld, turret.blockPosition(), itemHandler.getItems()));
                        turret.remove();
                        SpawnEggItem eggItem = SpawnEggItem.byId(turret.getType());
                        serverWorld.addFreshEntity(new ItemEntity(serverWorld, turret.getX(), turret.getY(), turret.getZ(), new ItemStack(eggItem)));
                        contextSupplier.get().setPacketHandled(true);
                    }
                });
        channel.registerMessage(packetIndex++, ClaimTurret.class, (claimTurret, packetBuffer) -> {
                    packetBuffer.writeInt(claimTurret.id);
                    packetBuffer.writeUUID(claimTurret.person);
                }, packetBuffer -> new ClaimTurret(packetBuffer.readInt(), packetBuffer.readUUID()),
                (claimTurret, contextSupplier) -> {
                    ServerWorld serverWorld = contextSupplier.get().getSender().getLevel();
                    Entity entity = serverWorld.getEntity(claimTurret.id);
                    if (entity instanceof Turret) {
                        Turret turret = (Turret) entity;
                        turret.setOwner(claimTurret.person);
                        contextSupplier.get().setPacketHandled(true);
                    }
                });
        channel.registerMessage(packetIndex++, ToggleMobility.class, (toggleMobility, packetBuffer) -> {
            packetBuffer.writeInt(toggleMobility.id);
            packetBuffer.writeBoolean(toggleMobility.mobile);
        }, packetBuffer -> {
            boolean mobile = packetBuffer.readBoolean();
            return new ToggleMobility(mobile, packetBuffer.readInt());
        }, (toggleMobility, contextSupplier) -> {
            ServerWorld serverWorld = contextSupplier.get().getSender().getLevel();
            Entity entity = serverWorld.getEntity(toggleMobility.id);
            if (entity instanceof Turret) {
                ((Turret) entity).setMoveable(toggleMobility.mobile);
                contextSupplier.get().setPacketHandled(true);
            }
        });
        channel.registerMessage(packetIndex, TogglePlayerProtection.class, (togglePlayerProtection, packetBuffer) -> {
                    packetBuffer.writeBoolean(togglePlayerProtection.protect);
                    packetBuffer.writeInt(togglePlayerProtection.id);
                }, packetBuffer -> new TogglePlayerProtection(packetBuffer.readBoolean(), packetBuffer.readInt()),
                (togglePlayerProtection, contextSupplier) -> {
                    ServerWorld serverWorld = contextSupplier.get().getSender().getLevel();
                    Entity entity = serverWorld.getEntity(togglePlayerProtection.id);
                    if (entity instanceof Turret) {
                        Turret turret = (Turret) entity;
                        turret.setProtectionFromPlayers(togglePlayerProtection.protect);
                        contextSupplier.get().setPacketHandled(true);
                    }
                });
    }
}
