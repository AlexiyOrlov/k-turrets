package dev.buildtool.kturrets;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.buildtool.kturrets.packets.*;
import dev.buildtool.kturrets.registers.*;
import dev.buildtool.kturrets.storage.StorageDrone;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.JsonUtils;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

@Mod(KTurrets.ID)
public class KTurrets {
    public static final String ID = "k_turrets";
    public static final ResourceLocation TITANIUM_INGOT = new ResourceLocation("forge", "ingots/titanium");
    static private final String NP = "1.0";
    public static final Type TYPE = new TypeToken<ArrayListMultimap<UUID, String>>() {
    }.getType();
    public static final Gson GSON = new GsonBuilder().registerTypeAdapter(TYPE, new MultimapAdapter()).create();
    public static final String FILTER = "Filter";
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
    public static ForgeConfigSpec.IntValue OWNER_FOLLOW_DISTANCE;
    public static ForgeConfigSpec.DoubleValue STORAGE_DRONE_HEALTH;
    public static DeferredRegister<CreativeModeTab> TAB_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ID);
    public static TagKey<Item> COBBLE_UNIT_AMMO_TAG = ForgeRegistries.ITEMS.tags().createTagKey(new ResourceLocation(ID, "cobble_unit_ammo"));
    public static TagKey<Item> ARROW_UNIT_AMMO_TAG = ForgeRegistries.ITEMS.tags().createTagKey(new ResourceLocation(ID, "arrow_unit_ammo"));
    public static TagKey<Item> BRICK_UNIT_AMMO_TAG1 = ForgeRegistries.ITEMS.tags().createTagKey(new ResourceLocation(ID, "brick_unit_ammo1"));
    public static TagKey<Item> BRICK_UNIT_AMMO_TAG2 = ForgeRegistries.ITEMS.tags().createTagKey(new ResourceLocation(ID, "brick_unit_ammo2"));
    public static TagKey<Item> BULLET_UNIT_AMMO_TAG1 = ForgeRegistries.ITEMS.tags().createTagKey(new ResourceLocation(ID, "bullet_unit_ammo1"));
    public static TagKey<Item> BULLET_UNIT_AMMO_TAG2 = ForgeRegistries.ITEMS.tags().createTagKey(new ResourceLocation(ID, "bullet_unit_ammo2"));
    public static TagKey<Item> FIREBALL_UNIT_AMMO = ForgeRegistries.ITEMS.tags().createTagKey(new ResourceLocation(ID, "fireball_unit_ammo"));
    public static TagKey<Item> GAUSS_UNIT_AMMO_TAG = ForgeRegistries.ITEMS.tags().createTagKey(new ResourceLocation(ID, "gauss_unit_ammo"));

    public static Logger logger= LogManager.getLogger("K-Turrets");

    public static ArrayListMultimap<String,String> serverUnitDeaths=ArrayListMultimap.create();
    public KTurrets() {
        CreativeModeTab creativeModeTab = CreativeModeTab.builder().title(Component.translatable(ID)).icon(() -> new ItemStack(KItems.ARROW_TURRET.get())).displayItems((p_270258_, items) -> {
            items.accept(KItems.COBBLE_TURRET.get());
            items.accept(KItems.ARROW_TURRET.get());
            items.accept(KItems.FIRECHARGE_TURRET.get());
            items.accept(KItems.BRICK_TURRET.get());
            items.accept(KItems.BULLET_TURRET.get());
            items.accept(KItems.GAUSS_TURRET.get());

            items.accept(KItems.EXPLOSIVE_POWDER.get());
            items.accept(KItems.GAUSS_BULLET.get());
            items.accept(KItems.TITANIUM_ORE.get());
            items.accept(KItems.DEEPSLATE_TITANIUM_ORE.get());
            items.accept(KItems.RAW_TITANIUM.get());
            items.accept(KItems.TITANIUM_INGOT.get());
            items.accept(KItems.TARGET_COPIER.get());

            items.accept(KItems.COBBLE_DRONE.get());
            items.accept(KItems.ARROW_DRONE.get());
            items.accept(KItems.FIREBALL_DRONE.get());
            items.accept(KItems.BRICK_DRONE.get());
            items.accept(KItems.BULLET_DRONE.get());
            items.accept(KItems.GAUSS_DRONE.get());

            items.accept(KItems.RELOADER.get());

            items.accept(KItems.STORAGE_DRONE.get());

            items.accept(KItems.LIGHT_UPGRADE.get());
            items.accept(KItems.MAGNET_UPGRADE.get());
        }).build();
        TAB_REGISTER.register("only", () -> creativeModeTab);

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        KEntities.ENTITIES.register(eventBus);
        KItems.ITEMS.register(eventBus);
        KContainers.CONTAINERS.register(eventBus);
        Sounds.SOUNDS.register(eventBus);
        KBlocks.BLOCKS.register(eventBus);
        TAB_REGISTER.register(eventBus);
        KBlockEntities.BLOCK_ENTITIES.register(eventBus);

        Pair<ForgeConfigSpec, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(builder -> {
            builder.push("Common");
            PROJECTILE_SPEED = builder.defineInRange("Turret and drone projectile speed", 50, 0.1, 50);
            TARGET_EXCEPTIONS = builder.comment("List of mob ids to be excluded from default targets").defineList("Target list exceptions", Collections.singletonList("minecraft:zombified_piglin"), o -> o instanceof String && ((String) o).contains(":"));
            SET_OWNER_AUTO = builder.define("Set ownership automatically", true);
            OWNER_FOLLOW_DISTANCE = builder.defineInRange("Drones will keep to the owner at such distance", 30, 1, 128);
            builder.pop();

            builder.push("Turret stats");
            builder.push("Arrow turret");
            ARROW_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, Double.MAX_VALUE);
            ARROW_TURRET_RANGE = builder.defineInRange("Range", 32d, 8d, 100d);
            ARROW_TURRET_ARMOR = builder.defineInRange("Armor", 3d, 0d, 100d);
            ARROW_TURRET_RATE = builder.comment("In ticks").defineInRange("Fire rate", 20, 1, 60);
            ARROW_TURRET_DAMAGE = builder.defineInRange("Base damage", 6, 1, 100);
            builder.pop();
            builder.push("Bullet turret");
            BULLET_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, Double.MAX_VALUE);
            BULLET_TURRET_RANGE = builder.defineInRange("Range", 32d, 8d, 100d);
            BULLET_TURRET_ARMOR = builder.defineInRange("Armor", 3d, 0d, 100d);
            BULLET_TURRET_RATE = builder.comment("In ticks").defineInRange("Fire rate", 20, 1, 60);
            IRON_BULLET_DAMAGE = builder.defineInRange("Iron bullet damage", 8, 1, 100);
            GOLD_BULLET_DAMAGE = builder.defineInRange("Gold bullet damage", 7, 1, 100);
            builder.pop();
            builder.push("Fire charge turret");
            CHARGE_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, Double.MAX_VALUE);
            CHARGE_TURRET_RANGE = builder.defineInRange("Range", 32d, 8d, 100d);
            CHARGE_TURRET_ARMOR = builder.defineInRange("Armor", 3d, 0d, 100d);
            CHARGE_TURRET_RATE = builder.comment("In ticks").defineInRange("Fire rate", 20, 1, 60);
            CHARGE_TURRET_DAMAGE = builder.defineInRange("Damage", 6, 1, 100);
            builder.pop();
            builder.push("Brick turret");
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
            builder.pop();
            builder.push("Cobble turret");
            COBBLE_TURRET_HEALTH = builder.defineInRange("Health", 60d, 10d, Double.MAX_VALUE);
            COBBLE_TURRET_RANGE = builder.defineInRange("Range", 32d, 0, 100d);
            COBBLE_TURRET_ARMOR = builder.defineInRange("Armor", 3, 0, 100d);
            COBBLE_TURRET_RATE = builder.comment("In ticks").defineInRange("Fire rate", 20, 1, 60);
            COBBLE_TURRET_DAMAGE = builder.defineInRange("Damage", 3, 1, 100);
            builder.pop();
            builder.push("Storage drone");
            STORAGE_DRONE_HEALTH=builder.defineInRange("Health",60d,10,Double.MAX_VALUE);
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
                    ServerPlayer serverPlayer = contextSupplier.get().getSender();
                    ServerLevel serverWorld = serverPlayer.serverLevel();
                    Entity entity = serverWorld.getEntity(dismantleTurret.id);
                    if (entity instanceof Turret turret) {
                        turret.discard();
                        UUID uuid=serverPlayer.getUUID();
                        ItemStack egg = new ItemStack(Objects.requireNonNull(ForgeSpawnEggItem.fromEntityType(turret.getType())));
                        egg.getOrCreateTag().put("Contained", turret.serializeNBT());
                        egg.getTag().putUUID("UUID", turret.getUUID());
                        serverWorld.addFreshEntity(new ItemEntity(serverWorld, turret.getX(), turret.getY(), turret.getZ(), egg));
                        if(FMLEnvironment.dist.isDedicatedServer()) {
                            UnitLimitCapability limitCapability = serverWorld.getCapability(RegisterCapability.unitCapability, null).orElse(null);
                            if (entity instanceof Drone)
                                limitCapability.setDroneCount(uuid, limitCapability.getDroneCount(uuid) - 1);
                            else
                                limitCapability.setTurretCount(uuid, limitCapability.getTurretCount(uuid) - 1);
                        }
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
        channel.registerMessage(packetIndex++, AmmoCheck.class, (ammoCheck, byteBuf) -> {
                    byteBuf.writeInt(ammoCheck.unit);
                    byteBuf.writeBoolean(ammoCheck.noAmmo);
                }, byteBuf -> {
                    int ammo = byteBuf.readInt();
                    return new AmmoCheck(byteBuf.readBoolean(), ammo);
                },
                (ammoCheck, contextSupplier) -> {
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> {
                        contextSupplier.get().setPacketHandled(true);
                        return new ClientProxy().syncAmmoStatus(ammoCheck);
                    });
                });
        channel.registerMessage(packetIndex++,SetMagnetState.class,(setMagnetState, byteBuf) -> {
            byteBuf.writeInt(setMagnetState.droneId);
            byteBuf.writeBoolean(setMagnetState.state);
        },byteBuf -> new SetMagnetState(byteBuf.readInt(),byteBuf.readBoolean()),(setMagnetState, contextSupplier) -> {
            contextSupplier.get().enqueueWork(() -> {
                ServerLevel serverLevel=contextSupplier.get().getSender().serverLevel();
                Entity drone= serverLevel.getEntity(setMagnetState.droneId);
                if(drone instanceof StorageDrone storageDrone)
                {
                    storageDrone.setMagnetActive(setMagnetState.state);
                    contextSupplier.get().setPacketHandled(true);
                }
            });
        });
        channel.registerMessage(packetIndex++, MagnetFilterState.class,(magnetFilterState, byteBuf) -> byteBuf.writeBoolean(magnetFilterState.state),
                byteBuf -> new MagnetFilterState(byteBuf.readBoolean()),
                (magnetFilterState, contextSupplier) -> {
                    ServerPlayer serverPlayer=contextSupplier.get().getSender();
                    ItemStack held=serverPlayer.getInventory().getSelected();
                    if(held.is(KItems.MAGNET_UPGRADE.get()))
                    {
                        held.getOrCreateTag().putBoolean(FILTER, magnetFilterState.state);
                        contextSupplier.get().setPacketHandled(true);
                    }
                });
        channel.registerMessage(packetIndex++, SetTarget.class,(setTarget, byteBuf) -> {
            byteBuf.writeInt(setTarget.unit);
            byteBuf.writeBoolean(setTarget.state);
            byteBuf.writeUtf(setTarget.id);
        },byteBuf -> {
            int unit=byteBuf.readInt();
            boolean state=byteBuf.readBoolean();
            String id=byteBuf.readUtf();
            return new SetTarget(state,id,unit);
        },(setTarget, contextSupplier) -> {
            ServerLevel serverLevel=contextSupplier.get().getSender().serverLevel();
            Entity entity=serverLevel.getEntity(setTarget.unit);
            if(entity instanceof Turret turret)
            {
               List<EntityType<?>> targets= Turret.decodeTargets(turret.getTargets());
               if(setTarget.state)
               {
                   targets.add(ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(setTarget.id)));
               }
               else {
                   targets.remove(ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(setTarget.id)));
               }
               turret.setTargets(Turret.encodeTargets(targets));
               contextSupplier.get().setPacketHandled(true);
            }
        });

        channel.registerMessage(packetIndex++, SetBehavior.class,(setBehavior, byteBuf) -> {
            byteBuf.writeInt(setBehavior.drone);
            byteBuf.writeEnum(setBehavior.behavior);
        },byteBuf -> new SetBehavior(byteBuf.readInt(),byteBuf.readEnum(Drone.Behavior.class)),
                (setBehavior, contextSupplier) -> {
            ServerLevel serverLevel=contextSupplier.get().getSender().serverLevel();
            Entity entity=serverLevel.getEntity(setBehavior.drone);
            if(entity instanceof Drone drone)
            {
                drone.setBehavior(setBehavior.behavior);
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

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void loadConfig(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

    @SubscribeEvent
    public void showAmmo(ServerStartedEvent serverStartedEvent)
    {
        logger.info("Cobble unit ammo:");
        ITagManager<Item> tags = ForgeRegistries.ITEMS.tags();
        tags.getTag(COBBLE_UNIT_AMMO_TAG).stream().forEach(item -> logger.info(ForgeRegistries.ITEMS.getKey(item)));
        logger.info("");
        logger.info("Bullet unit ammo 1:");
        tags.getTag(BULLET_UNIT_AMMO_TAG1).stream().forEach(item -> logger.info(ForgeRegistries.ITEMS.getKey(item)));
        logger.info("Bullet unit ammo 2:");
        tags.getTag(BULLET_UNIT_AMMO_TAG2).stream().forEach(item -> logger.info(ForgeRegistries.ITEMS.getKey(item)));
        logger.info("");
        logger.info("Brick unit ammo 1:");
        tags.getTag(BRICK_UNIT_AMMO_TAG1).stream().forEach(item -> logger.info(ForgeRegistries.ITEMS.getKey(item)));
        logger.info("Brick unit ammo 2:");
        tags.getTag(BRICK_UNIT_AMMO_TAG2).stream().forEach(item -> logger.info(ForgeRegistries.ITEMS.getKey(item)));
        logger.info("");
        logger.info("Gauss unit ammo:");
        tags.getTag(GAUSS_UNIT_AMMO_TAG).stream().forEach(item -> logger.info(ForgeRegistries.ITEMS.getKey(item)));
        logger.info("");
        logger.info("Fireball unit ammo:");
        tags.getTag(FIREBALL_UNIT_AMMO).stream().forEach(item -> logger.info(ForgeRegistries.ITEMS.getKey(item)));
        logger.info("");
        logger.info("Arrow unit ammo:");
        tags.getTag(ARROW_UNIT_AMMO_TAG).stream().forEach(item -> logger.info(ForgeRegistries.ITEMS.getKey(item)));
        logger.info("");

        if(serverStartedEvent.getServer().isDedicatedServer()) {
            File dir = serverStartedEvent.getServer().getServerDirectory();
            File file = new File(dir, ID + ".json");
            if(file.exists()) {
                try {
                    serverUnitDeaths = GSON.fromJson(new FileReader(file), TYPE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SubscribeEvent
    public void onServerStop(ServerStoppingEvent serverStoppingEvent)
    {
        MinecraftServer server=serverStoppingEvent.getServer();
        if(server.isDedicatedServer())
        {
            String s= GSON.toJson(serverUnitDeaths, TYPE);
            try {
                Path path = server.getServerDirectory().toPath().resolve(Path.of(ID + ".json"));
                if(!Files.exists(path)) {
                    Files.createFile(path);
                }
                Files.writeString(path,s, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public void notifyPlayer(PlayerEvent.PlayerLoggedInEvent playerLoggedInEvent)
    {
        Player player= playerLoggedInEvent.getEntity();
        if(player.level().getServer().isDedicatedServer())
        {
            //use string instead of raw UUID
            String uuid = player.getUUID().toString();
            if(serverUnitDeaths.containsKey(uuid)) {
                player.displayClientMessage(Component.literal("[K-Turrets] Some of your units were destroyed while you were offline"),false);
                serverUnitDeaths.get(uuid).forEach(s -> player.displayClientMessage(Component.literal(s), false));
                serverUnitDeaths.removeAll(uuid);
            }
        }
    }
}
