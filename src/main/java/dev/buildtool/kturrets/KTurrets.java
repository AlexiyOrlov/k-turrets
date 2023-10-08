package dev.buildtool.kturrets;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import dev.buildtool.kturrets.packets.*;
import dev.buildtool.kturrets.registers.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Mod(KTurrets.ID)
public class KTurrets {
    public static final String ID = "k_turrets";
    public static final ResourceLocation TITANIUM_INGOT = new ResourceLocation("forge", "ingots/titanium");
    static private final String NP = "1.0";
    public static SimpleChannel channel;
    public static ForgeConfigSpec.DoubleValue ARROW_TURRET_HEALTH;
    public static ForgeConfigSpec.DoubleValue ARROW_TURRET_RANGE;
    public static ForgeConfigSpec.DoubleValue ARROW_TURRET_ARMOR;
    public static ForgeConfigSpec.IntValue ARROW_TURRET_DAMAGE;
    public static ForgeConfigSpec.IntValue ARROW_TURRET_RATE;
    public static ForgeConfigSpec.IntValue BULLET_TURRET_RATE;
    public static ForgeConfigSpec.IntValue CHARGE_TURRET_RATE;
    public static ForgeConfigSpec.DoubleValue BULLET_TURRET_HEALTH;
    public static ForgeConfigSpec.DoubleValue BULLET_TURRET_RANGE;
    public static ForgeConfigSpec.DoubleValue BULLET_TURRET_ARMOR;
    public static ForgeConfigSpec.IntValue GOLD_BULLET_DAMAGE;
    public static ForgeConfigSpec.IntValue IRON_BULLET_DAMAGE;
    public static ForgeConfigSpec.DoubleValue CHARGE_TURRET_HEALTH;
    public static ForgeConfigSpec.DoubleValue CHARGE_TURRET_RANGE;
    public static ForgeConfigSpec.DoubleValue CHARGE_TURRET_ARMOR;
    public static ForgeConfigSpec.IntValue CHARGE_TURRET_DAMAGE;
    public static ForgeConfigSpec.DoubleValue BRICK_TURRET_HEALTH, BRICK_TURRET_RANGE, BRICK_TURRET_ARMOR;
    public static ForgeConfigSpec.IntValue BRICK_DAMAGE, NETHERBRICK_DAMAGE, BRICK_TURRET_RATE;
    public static ForgeConfigSpec.DoubleValue GAUSS_TURRET_HEALTH, GAUSS_TURRET_RANGE, GAUSS_TURRET_ARMOR;
    public static ForgeConfigSpec.IntValue GAUSS_TURRET_DAMAGE, GAUSS_TURRET_RATE;
    public static ForgeConfigSpec.DoubleValue COBBLE_TURRET_HEALTH, COBBLE_TURRET_RANGE, COBBLE_TURRET_ARMOR;
    public static ForgeConfigSpec.IntValue COBBLE_TURRET_DAMAGE, COBBLE_TURRET_RATE;
    public static ForgeConfigSpec.BooleanValue ENABLE_DRONE_SOUND;
    public static ForgeConfigSpec.DoubleValue PROJECTILE_SPEED;
    public static ForgeConfigSpec.ConfigValue<List<?>> TARGET_EXCEPTIONS;
    public static ForgeConfigSpec.BooleanValue SHOW_INTEGRITY;
    public static int ORE_AMOUNT;
    public KTurrets() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        TEntities.ENTITIES.register(eventBus);
        TItems.ITEMS.register(eventBus);
        TContainers.CONTAINERS.register(eventBus);
        Sounds.SOUNDS.register(eventBus);
        KBlocks.BLOCKS.register(eventBus);
        Properties properties = new Properties();
        try {
            Path path = Paths.get("config", "k_turrets.properties");
            if (Files.notExists(path)) {
                properties.put("Titanium-ore-frequency", "5");
                properties.store(Files.newBufferedWriter(path, StandardCharsets.UTF_8), "");
            }
            properties.load(Files.newInputStream(path));
            String oreFrequencyStr = (String) properties.get("Titanium-ore-frequency");
            ORE_AMOUNT = Integer.parseInt(oreFrequencyStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WorldGeneration.CONFIGURED_FEATURE_REGISTER.register(eventBus);
        WorldGeneration.PLACED_FEATURE_REGISTER.register(eventBus);

        Pair<ForgeConfigSpec, ForgeConfigSpec> configPair = new ForgeConfigSpec.Builder().configure(builder -> {
            builder.push("Common");
            PROJECTILE_SPEED = builder.comment("Gauss bullet speed is 3x of this").defineInRange("Turret and drone projectile speed", 50, 0.1, 50);
            TARGET_EXCEPTIONS = builder.comment("List of mob ids to be excluded from default targets").defineList("Target list exceptions", Collections.singletonList("minecraft:zombified_piglin"), o -> o instanceof String && ((String) o).contains(":"));
            builder.pop();

            builder.push("Turret stats");
            builder.push("Arrow turret");
            ARROW_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, 500d);
            ARROW_TURRET_RANGE = builder.defineInRange("Range", 32d, 8d, 100d);
            ARROW_TURRET_ARMOR = builder.defineInRange("Armor", 3d, 0d, 100d);
            ARROW_TURRET_RATE = builder.comment("In ticks").defineInRange("Fire rate", 20, 1, 60);
            ARROW_TURRET_DAMAGE = builder.defineInRange("Base damage", 6, 1, 100);
            builder.pop();
            builder.push("Bullet turret");
            BULLET_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, 500d);
            BULLET_TURRET_RANGE = builder.defineInRange("Range", 32d, 8d, 100d);
            BULLET_TURRET_ARMOR = builder.defineInRange("Armor", 3d, 0d, 100d);
            BULLET_TURRET_RATE = builder.comment("In ticks").defineInRange("Fire rate", 20, 1, 60);
            IRON_BULLET_DAMAGE = builder.defineInRange("Iron bullet damage", 8, 1, 100);
            GOLD_BULLET_DAMAGE = builder.defineInRange("Gold bullet damage", 7, 1, 100);
            builder.pop();
            builder.push("Fire charge turret");
            CHARGE_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, 500d);
            CHARGE_TURRET_RANGE = builder.defineInRange("Range", 32d, 8d, 100d);
            CHARGE_TURRET_ARMOR = builder.defineInRange("Armor", 3d, 0d, 100d);
            CHARGE_TURRET_RATE = builder.comment("In ticks").defineInRange("Fire rate", 20, 1, 60);
            CHARGE_TURRET_DAMAGE = builder.defineInRange("Damage", 6, 1, 100);
            builder.pop();
            builder.push("Brick turret");
            BRICK_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, 500d);
            BRICK_TURRET_RANGE = builder.defineInRange("Range", 32d, 8d, 100d);
            BRICK_TURRET_ARMOR = builder.defineInRange("Armor", 3, 0d, 100d);
            BRICK_TURRET_RATE = builder.comment("In ticks").defineInRange("Fire rate", 20, 1, 60);
            BRICK_DAMAGE = builder.defineInRange("Brick damage", 9, 1, 100);
            NETHERBRICK_DAMAGE = builder.defineInRange("Nether brick damage", 10, 1, 100);
            builder.pop();
            builder.push("Gauss turret");
            GAUSS_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, 500d);
            GAUSS_TURRET_RANGE = builder.defineInRange("Range", 32d, 8, 100d);
            GAUSS_TURRET_ARMOR = builder.defineInRange("Armor", 3, 0, 100d);
            GAUSS_TURRET_RATE = builder.comment("In ticks").defineInRange("Fire rate", 20, 1, 60);
            GAUSS_TURRET_DAMAGE = builder.defineInRange("Damage", 12, 1, 100);
            builder.pop();
            builder.push("Cobble turret");
            COBBLE_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, 500d);
            COBBLE_TURRET_RANGE = builder.defineInRange("Range", 32d, 0, 100d);
            COBBLE_TURRET_ARMOR = builder.defineInRange("Armor", 3, 0, 100d);
            COBBLE_TURRET_RATE = builder.comment("In ticks").defineInRange("Fire rate", 20, 1, 60);
            COBBLE_TURRET_DAMAGE = builder.defineInRange("Damage", 3, 1, 100);
            builder.pop();
            builder.pop();
            return builder.build();
        });
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, configPair.getRight());
        loadConfig(configPair.getRight(), FMLPaths.CONFIGDIR.get().resolve("k_turrets-common.toml").toString());

        channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(ID, "network"), () -> NP, NP::equals, NP::equals);
        int packetIndex = 0;
        channel.registerMessage(packetIndex++, TurretTargets.class, (turretTargets, packetBuffer) -> {
            packetBuffer.writeInt(turretTargets.turretID);
            packetBuffer.writeNbt(turretTargets.targets);
        }, packetBuffer -> {
            int id = packetBuffer.readInt();
            CompoundTag compoundNBT = packetBuffer.readNbt();
            return new TurretTargets(compoundNBT, id);
        }, (turretTargets, contextSupplier) -> {
            NetworkEvent.Context context = contextSupplier.get();
            ServerPlayer sender = context.getSender();
            ServerLevel serverWorld = sender.getLevel();
            Entity entity = serverWorld.getEntity(turretTargets.turretID);
            if (entity instanceof Turret turret) {
                turret.setTargets(turretTargets.targets);
                context.setPacketHandled(true);
                if (sender.getTeam() != null) {
                    turret.setManualTeam(sender.getTeam().getName());
                }
            }
        });
        channel.registerMessage(packetIndex++, DismantleTurret.class, (dismantleTurret, packetBuffer) -> packetBuffer.writeInt(dismantleTurret.id),
                packetBuffer -> new DismantleTurret(packetBuffer.readInt()),
                (dismantleTurret, contextSupplier) -> {
                    ServerLevel serverWorld = contextSupplier.get().getSender().getLevel();
                    Entity entity = serverWorld.getEntity(dismantleTurret.id);
                    if (entity instanceof Turret turret) {
                        turret.discard();

                        ItemStack egg = new ItemStack(turret.getSpawnItem());
                        egg.getOrCreateTag().put("Contained", turret.serializeNBT());
                        egg.getTag().putUUID("UUID", turret.getUUID());
                        serverWorld.addFreshEntity(new ItemEntity(serverWorld, turret.getX(), turret.getY(), turret.getZ(), egg));
                        contextSupplier.get().setPacketHandled(true);
                    }
                });
        channel.registerMessage(packetIndex++, ClaimTurret.class, (claimTurret, packetBuffer) -> {
                    packetBuffer.writeInt(claimTurret.id);
                    packetBuffer.writeUUID(claimTurret.person);
                }, packetBuffer -> new ClaimTurret(packetBuffer.readInt(), packetBuffer.readUUID()),
                (claimTurret, contextSupplier) -> {
                    ServerLevel serverWorld = contextSupplier.get().getSender().getLevel();
                    Entity entity = serverWorld.getEntity(claimTurret.id);
                    if (entity instanceof Turret turret) {
                        turret.setOwner(claimTurret.person);
                        if (turret instanceof Drone)
                            contextSupplier.get().getSender().sendMessage(new TranslatableComponent("k_turrets.drone_claimed"), turret.getUUID());
                        else
                            contextSupplier.get().getSender().sendMessage(new TranslatableComponent("k_turrets.turret_claimed"), turret.getUUID());
                        contextSupplier.get().setPacketHandled(true);
                        ServerPlayer player = contextSupplier.get().getSender();
                        if (player.getTeam() != null) {
                            turret.setManualTeam(player.getTeam().getName());
                        } else {
                            turret.setManualTeam("");
                        }
                    }
                });
        channel.registerMessage(packetIndex++, ToggleMobility.class, (toggleMobility, packetBuffer) -> {
            packetBuffer.writeInt(toggleMobility.id);
            packetBuffer.writeBoolean(toggleMobility.mobile);
        }, packetBuffer -> {
            int id = packetBuffer.readInt();
            boolean mobile = packetBuffer.readBoolean();
            return new ToggleMobility(mobile, id);
        }, (toggleMobility, contextSupplier) -> {
            ServerLevel serverWorld = contextSupplier.get().getSender().getLevel();
            Entity entity = serverWorld.getEntity(toggleMobility.id);
            if (entity instanceof Turret) {
                ((Turret) entity).setMoveable(toggleMobility.mobile);
                contextSupplier.get().setPacketHandled(true);
            }
        });
        channel.registerMessage(packetIndex++, TogglePlayerProtection.class, (togglePlayerProtection, packetBuffer) -> {
                    packetBuffer.writeBoolean(togglePlayerProtection.protect);
                    packetBuffer.writeInt(togglePlayerProtection.id);
                }, packetBuffer -> new TogglePlayerProtection(packetBuffer.readBoolean(), packetBuffer.readInt()),
                (togglePlayerProtection, contextSupplier) -> {
                    ServerLevel serverWorld = contextSupplier.get().getSender().getLevel();
                    Entity entity = serverWorld.getEntity(togglePlayerProtection.id);
                    if (entity instanceof Turret turret) {
                        turret.setProtectionFromPlayers(togglePlayerProtection.protect);
                        contextSupplier.get().setPacketHandled(true);
                    }
                });
        channel.registerMessage(packetIndex, ToggleDroneFollow.class, (toggleDroneFollow, friendlyByteBuf) -> {
            friendlyByteBuf.writeInt(toggleDroneFollow.id);
            friendlyByteBuf.writeBoolean(toggleDroneFollow.follow);
        }, friendlyByteBuf -> {
            int id = friendlyByteBuf.readInt();
            return new ToggleDroneFollow(friendlyByteBuf.readBoolean(), id);
        }, (toggleDroneFollow, contextSupplier) -> {
            ServerLevel serverLevel = contextSupplier.get().getSender().getLevel();
            Entity entity = serverLevel.getEntity(toggleDroneFollow.id);
            if (entity instanceof Drone drone) {
                drone.followOwner(toggleDroneFollow.follow);
                contextSupplier.get().setPacketHandled(true);
            }
        });
        channel.registerMessage(packetIndex++, AddPlayerException.class, (e, friendlyByteBuf) -> {
            friendlyByteBuf.writeInt(e.turretId);
            friendlyByteBuf.writeUtf(e.playerName);
        }, friendlyByteBuf -> new AddPlayerException(friendlyByteBuf.readInt(), friendlyByteBuf.readUtf()), (e, contextSupplier) -> {
            ServerLevel serverLevel = contextSupplier.get().getSender().getLevel();
            Entity entity = serverLevel.getEntity(e.turretId);
            if (entity instanceof Turret turret) {
                turret.addPlayerToExceptions(e.playerName);
                contextSupplier.get().setPacketHandled(true);
            }
        });
        channel.registerMessage(packetIndex++, RemovePlayerException.class, (e, friendlyByteBuf) -> {
                    friendlyByteBuf.writeInt(e.turretId);
                    friendlyByteBuf.writeUtf(e.playerName);
                }, friendlyByteBuf -> new RemovePlayerException(friendlyByteBuf.readInt(), friendlyByteBuf.readUtf()),
                (e, contextSupplier) -> {
                    ServerLevel serverLevel = contextSupplier.get().getSender().getLevel();
                    Entity entity = serverLevel.getEntity(e.turretId);
                    if (entity instanceof Turret turret) {
                        turret.removePlayerFromExceptions(e.playerName);
                        contextSupplier.get().getSender().displayClientMessage(new TranslatableComponent("k_turrets.removed.player.from.exceptions", e.playerName), false);
                        contextSupplier.get().setPacketHandled(true);
                    }
                });

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, new ForgeConfigSpec.Builder().configure(builder -> {
            ENABLE_DRONE_SOUND = builder.define("Enable drone flying sound", false);
            SHOW_INTEGRITY = builder.define("Show turret and drone integrity", true);
            return builder.build();
        }).getRight());
    }

    private static void loadConfig(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }
}
