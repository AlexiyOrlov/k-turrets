package dev.buildtool.kturrets;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import dev.buildtool.kturrets.packets.*;
import dev.buildtool.kturrets.registers.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Mod(KTurrets.ID)
public class KTurrets {
    public static final String ID = "k_turrets";
    public static final Logger LOGGER = LogManager.getLogger("K-Turrets");
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
    public static ForgeConfigSpec.BooleanValue SHOW_INTEGRITY;
    public static ForgeConfigSpec.IntValue TURRET_LIMIT_PER_PLAYER, DRONE_LIMIT_PER_PLAYER;
    public static ForgeConfigSpec.DoubleValue PROJECTILE_SPEED;
    public static ForgeConfigSpec.ConfigValue<List<?>> TARGET_EXCEPTIONS;
    public static ForgeConfigSpec.BooleanValue SET_OWNER_AUTO;
    public static ForgeConfigSpec.ConfigValue<String> COBBLE_TURRET_AMMO;
    public static ForgeConfigSpec.BooleanValue USE_CUSTOM_COBBLE_TURRET_AMMO;
    public static ForgeConfigSpec.ConfigValue<String> GAUSS_TURRET_AMMO;
    public static ForgeConfigSpec.BooleanValue uSE_CUSTOM_ARROW_TURRET_AMMO;
    public static ForgeConfigSpec.ConfigValue<String> ARROW_TURRET_AMMO;
    public static ForgeConfigSpec.BooleanValue USE_CUSTOM_BULLET_TURRET_AMMO;
    public static ForgeConfigSpec.ConfigValue<String> CUSTOM_BULLET_TURRET_AMMO;
    public static ForgeConfigSpec.BooleanValue USE_CUSTOM_BRICK_TURRET_AMMO;
    public static ForgeConfigSpec.ConfigValue<String> CUSTOM_BRICK_TURRET_AMMO;
    public static ForgeConfigSpec.ConfigValue<String> CUSTOM_FIREBALL_TURRET_AMMO;
    public static DeferredRegister<CreativeModeTab> TAB_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ID);
    public KTurrets() {
        CreativeModeTab creativeModeTab = CreativeModeTab.builder().title(Component.translatable(ID)).icon(() -> new ItemStack(KItems.GAUSS_BULLET.get())).displayItems((p_270258_, p_259752_) -> {
            p_259752_.accept(KItems.COBBLE_TURRET.get());
            p_259752_.accept(KItems.ARROW_TURRET.get());
            p_259752_.accept(KItems.FIRECHARGE_TURRET.get());
            p_259752_.accept(KItems.BRICK_TURRET.get());
            p_259752_.accept(KItems.BULLET_TURRET.get());
            p_259752_.accept(KItems.GAUSS_TURRET.get());

            p_259752_.accept(KItems.EXPLOSIVE_POWDER.get());
            p_259752_.accept(KItems.GAUSS_BULLET.get());
            p_259752_.accept(KItems.TITANIUM_ORE.get());
            p_259752_.accept(KItems.DEEPSLATE_TITANIUM_ORE.get());
            p_259752_.accept(KItems.RAW_TITANIUM.get());
            p_259752_.accept(KItems.TITANIUM_INGOT.get());
            p_259752_.accept(KItems.TARGET_COPIER.get());

            p_259752_.accept(KItems.COBBLE_DRONE.get());
            p_259752_.accept(KItems.ARROW_DRONE.get());
            p_259752_.accept(KItems.FIREBALL_DRONE.get());
            p_259752_.accept(KItems.BRICK_DRONE.get());
            p_259752_.accept(KItems.BULLET_DRONE.get());
            p_259752_.accept(KItems.GAUSS_DRONE.get());
        }).build();
        TAB_REGISTER.register("only", () -> creativeModeTab);

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        KEntities.ENTITIES.register(eventBus);
        KItems.ITEMS.register(eventBus);
        KContainers.CONTAINERS.register(eventBus);
        Sounds.SOUNDS.register(eventBus);
        KBlocks.BLOCKS.register(eventBus);
        TAB_REGISTER.register(eventBus);

        Pair<ForgeConfigSpec, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(builder -> {
            builder.push("Common");
            PROJECTILE_SPEED = builder.comment("Gauss bullet speed is 3x of this").defineInRange("Turret and drone projectile speed", 50, 0.1, 50);
            TARGET_EXCEPTIONS = builder.comment("List of mob ids to be excluded from default targets").defineList("Target list exceptions", Collections.singletonList("minecraft:zombified_piglin"), o -> o instanceof String && ((String) o).contains(":"));
            SET_OWNER_AUTO = builder.define("Set ownership automatically", true);
            builder.pop();

            builder.push("Turret stats");
            builder.push("Arrow turret");
            ARROW_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, Double.MAX_VALUE);
            ARROW_TURRET_RANGE = builder.defineInRange("Range", 32d, 8d, 100d);
            ARROW_TURRET_ARMOR = builder.defineInRange("Armor", 3d, 0d, 100d);
            ARROW_TURRET_RATE = builder.comment("In ticks").defineInRange("Fire rate", 20, 1, 60);
            ARROW_TURRET_DAMAGE = builder.defineInRange("Base damage", 6, 1, 100);
            uSE_CUSTOM_ARROW_TURRET_AMMO = builder.define("Use custom ammo", false);
            ARROW_TURRET_AMMO = builder.define("Ammo", "minecraft:arrow");
            builder.pop();
            builder.push("Bullet turret");
            BULLET_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, Double.MAX_VALUE);
            BULLET_TURRET_RANGE = builder.defineInRange("Range", 32d, 8d, 100d);
            BULLET_TURRET_ARMOR = builder.defineInRange("Armor", 3d, 0d, 100d);
            BULLET_TURRET_RATE = builder.comment("In ticks").defineInRange("Fire rate", 20, 1, 60);
            IRON_BULLET_DAMAGE = builder.defineInRange("Iron bullet damage", 8, 1, 100);
            GOLD_BULLET_DAMAGE = builder.defineInRange("Gold bullet damage", 7, 1, 100);
            USE_CUSTOM_BULLET_TURRET_AMMO = builder.comment("If true, uses iron bullet damage").define("Use custom ammo", false);
            CUSTOM_BULLET_TURRET_AMMO = builder.define("Ammo", "minecraft:iron_nugget");
            builder.pop();
            builder.push("Fire charge turret");
            CHARGE_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, Double.MAX_VALUE);
            CHARGE_TURRET_RANGE = builder.defineInRange("Range", 32d, 8d, 100d);
            CHARGE_TURRET_ARMOR = builder.defineInRange("Armor", 3d, 0d, 100d);
            CHARGE_TURRET_RATE = builder.comment("In ticks").defineInRange("Fire rate", 20, 1, 60);
            CHARGE_TURRET_DAMAGE = builder.defineInRange("Damage", 6, 1, 100);
            CUSTOM_FIREBALL_TURRET_AMMO = builder.define("Ammo", "k_turrets:explosive_powder");
            builder.pop();
            builder.push("Brick turret");
            USE_CUSTOM_BRICK_TURRET_AMMO = builder.comment("If true, will use nether brick damage").define("Use custom ammo", false);
            CUSTOM_BRICK_TURRET_AMMO = builder.define("Ammo", "minecraft:brick");
            BRICK_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, Double.MAX_VALUE);
            BRICK_TURRET_RANGE = builder.defineInRange("Range", 32d, 8d, 100d);
            BRICK_TURRET_ARMOR = builder.defineInRange("Armor", 3, 0d, 100d);
            BRICK_TURRET_RATE = builder.comment("In ticks").defineInRange("Fire rate", 20, 1, 60);
            BRICK_DAMAGE = builder.defineInRange("Brick damage", 9, 1, 100);
            NETHERBRICK_DAMAGE = builder.defineInRange("Nether brick damage", 10, 1, 100);
            builder.pop();
            builder.push("Gauss turret");
            GAUSS_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, Double.MAX_VALUE);
            GAUSS_TURRET_RANGE = builder.defineInRange("Range", 32d, 8, 100d);
            GAUSS_TURRET_ARMOR = builder.defineInRange("Armor", 3, 0, 100d);
            GAUSS_TURRET_RATE = builder.comment("In ticks").defineInRange("Fire rate", 20, 1, 60);
            GAUSS_TURRET_DAMAGE = builder.defineInRange("Damage", 12, 1, 100);
            GAUSS_TURRET_AMMO = builder.define("Ammo", "k_turrets:gauss_bullet");
            builder.pop();
            builder.push("Cobble turret");
            COBBLE_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, Double.MAX_VALUE);
            COBBLE_TURRET_RANGE = builder.defineInRange("Range", 32d, 0, 100d);
            COBBLE_TURRET_ARMOR = builder.defineInRange("Armor", 3, 0, 100d);
            COBBLE_TURRET_RATE = builder.comment("In ticks").defineInRange("Fire rate", 20, 1, 60);
            COBBLE_TURRET_DAMAGE = builder.defineInRange("Damage", 3, 1, 100);
            USE_CUSTOM_COBBLE_TURRET_AMMO = builder.define("Use custom ammo", false);
            COBBLE_TURRET_AMMO = builder.define("Ammo", "minecraft:cobblestone");
            builder.pop();
            builder.pop();
            return builder.build();
        });
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, pair.getRight());
        loadConfig(pair.getRight(), FMLPaths.CONFIGDIR.get().resolve("k_turrets-common.toml").toString());

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
            ServerLevel serverWorld = sender.serverLevel();
            Entity entity = serverWorld.getEntity(turretTargets.turretID);
            if (entity instanceof Turret turret) {
                turret.setTargets(turretTargets.targets);
                context.setPacketHandled(true);
            }
        });
        channel.registerMessage(packetIndex++, DismantleTurret.class, (dismantleTurret, packetBuffer) -> packetBuffer.writeInt(dismantleTurret.id),
                packetBuffer -> new DismantleTurret(packetBuffer.readInt()),
                (dismantleTurret, contextSupplier) -> {
                    ServerLevel serverWorld = contextSupplier.get().getSender().serverLevel();
                    Entity entity = serverWorld.getEntity(dismantleTurret.id);
                    if (entity instanceof Turret turret) {
                        turret.discard();

                        ItemStack egg = new ItemStack(Objects.requireNonNull(ForgeSpawnEggItem.fromEntityType(turret.getType())));
                        egg.getOrCreateTag().put("Contained", turret.serializeNBT());
                        egg.getTag().putUUID("UUID", turret.getUUID());
                        serverWorld.addFreshEntity(new ItemEntity(serverWorld, turret.getX(), turret.getY(), turret.getZ(), egg));
                        UnitLimitCapability limitCapability = contextSupplier.get().getSender().getCapability(RegisterCapability.unitCapability, null).orElse(null);
                        if (entity instanceof Drone)
                            limitCapability.setDroneCount(limitCapability.getDroneCount() - 1);
                        else
                            limitCapability.setTurretCount(limitCapability.getTurretCount() - 1);
                        contextSupplier.get().setPacketHandled(true);
                    }
                });
        channel.registerMessage(packetIndex++, ClaimTurret.class, (claimTurret, packetBuffer) -> {
                    packetBuffer.writeInt(claimTurret.id);
                    packetBuffer.writeUUID(claimTurret.person);
                }, packetBuffer -> new ClaimTurret(packetBuffer.readInt(), packetBuffer.readUUID()),
                (claimTurret, contextSupplier) -> {
                    ServerLevel serverWorld = contextSupplier.get().getSender().serverLevel();
                    Entity entity = serverWorld.getEntity(claimTurret.id);
                    if (entity instanceof Turret turret) {
                        turret.setOwner(claimTurret.person);
                        turret.setOwnerName(contextSupplier.get().getSender().getName().getString());
                        if (turret instanceof Drone)
                            contextSupplier.get().getSender().displayClientMessage(Component.translatable("k_turrets.drone_claimed"), true);
                        else
                            contextSupplier.get().getSender().displayClientMessage(Component.translatable("k_turrets.turret_claimed"), true);
                        contextSupplier.get().setPacketHandled(true);
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
            ServerLevel serverWorld = contextSupplier.get().getSender().serverLevel();
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
                    ServerLevel serverWorld = contextSupplier.get().getSender().serverLevel();
                    Entity entity = serverWorld.getEntity(togglePlayerProtection.id);
                    if (entity instanceof Turret turret) {
                        turret.setProtectionFromPlayers(togglePlayerProtection.protect);
                        contextSupplier.get().setPacketHandled(true);
                    }
                });
        channel.registerMessage(packetIndex++, ToggleDroneFollow.class, (toggleDroneFollow, friendlyByteBuf) -> {
            friendlyByteBuf.writeInt(toggleDroneFollow.id);
            friendlyByteBuf.writeBoolean(toggleDroneFollow.follow);
        }, friendlyByteBuf -> {
            int id = friendlyByteBuf.readInt();
            return new ToggleDroneFollow(friendlyByteBuf.readBoolean(), id);
        }, (toggleDroneFollow, contextSupplier) -> {
            ServerLevel serverLevel = contextSupplier.get().getSender().serverLevel();
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
            ServerLevel serverLevel = contextSupplier.get().getSender().serverLevel();
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
                    ServerLevel serverLevel = contextSupplier.get().getSender().serverLevel();
                    Entity entity = serverLevel.getEntity(e.turretId);
                    if (entity instanceof Turret turret) {
                        turret.removePlayerFromExceptions(e.playerName);
                        contextSupplier.get().getSender().displayClientMessage(Component.translatable("k_turrets.removed.player.from.exceptions", e.playerName), false);
                        contextSupplier.get().setPacketHandled(true);
                    }
                });
        channel.registerMessage(packetIndex++, ToggleGuardingArea.class, (toggleGuardingArea, friendlyByteBuf) -> {
            friendlyByteBuf.writeInt(toggleGuardingArea.droneId);
            friendlyByteBuf.writeBoolean(toggleGuardingArea.guard);
        }, friendlyByteBuf -> new ToggleGuardingArea(friendlyByteBuf.readInt(), friendlyByteBuf.readBoolean()), (toggleGuardingArea, contextSupplier) -> {
            ServerLevel serverLevel = contextSupplier.get().getSender().serverLevel();
            Entity entity = serverLevel.getEntity(toggleGuardingArea.droneId);
            if (entity instanceof Drone drone) {
                drone.setGuardArea(toggleGuardingArea.guard);
                contextSupplier.get().setPacketHandled(true);
            }
        });
        channel.registerMessage(packetIndex++, SetRefillInventory.class, (setRefillInventory, friendlyByteBuf) -> {
            friendlyByteBuf.writeBoolean(setRefillInventory.refill);
            friendlyByteBuf.writeInt(setRefillInventory.turretId);
        }, friendlyByteBuf -> new SetRefillInventory(friendlyByteBuf.readBoolean(), friendlyByteBuf.readInt()), (setRefillInventory, contextSupplier) -> {
            ServerLevel serverLevel = contextSupplier.get().getSender().serverLevel();
            Entity entity = serverLevel.getEntity(setRefillInventory.turretId);
            if (entity instanceof Turret turret) {
                turret.setRefillInventory(setRefillInventory.refill);
                contextSupplier.get().setPacketHandled(true);
            }
        });

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, new ForgeConfigSpec.Builder().configure(builder -> {
            ENABLE_DRONE_SOUND = builder.define("Enable drone flying sound", false);
            SHOW_INTEGRITY = builder.define("Show turret and drone integrity", true);
            return builder.build();
        }).getRight());

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, new ForgeConfigSpec.Builder().configure(builder -> {
            TURRET_LIMIT_PER_PLAYER = builder.defineInRange("Turret limit per player", () -> 10, 1, 300);
            DRONE_LIMIT_PER_PLAYER = builder.defineInRange("Drone limit per player", () -> 10, 1, 300);
            return builder.build();
        }).getRight());
    }

    public static void loadConfig(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }
}
