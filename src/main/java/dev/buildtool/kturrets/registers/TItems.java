package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.ContainerItem;
import dev.buildtool.kturrets.KTurrets;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KTurrets.ID);
    public static RegistryObject<Item> SEMI_STEEL;
    public static RegistryObject<Item> STEEL_INGOT;
    public static RegistryObject<Item> EXPLOSIVE_POWDER;
    public static RegistryObject<Item> ARROW_TURRET;
    public static RegistryObject<Item> BRICK_TURRET;
    public static RegistryObject<Item> BULLET_TURRET;
    public static RegistryObject<Item> FIRECHARGE_TURRET;
    public static RegistryObject<Item> GAUSS_BULLET;
    public static RegistryObject<Item> GAUSS_TURRET;
    public static RegistryObject<Item> COBBLE_TURRET;
    public static RegistryObject<Item> COBBLE_DRONE;
    public static RegistryObject<Item> ARROW_DRONE;
    public static RegistryObject<Item> FIRE_CHARGE_DRONE;
    public static RegistryObject<Item> BULLET_DRONE;
    public static RegistryObject<Item> BRICK_DRONE;
    public static RegistryObject<Item> GAUSS_DRONE;

    static {
        ARROW_TURRET = ITEMS.register("arrow_turret_item", () -> new ContainerItem(TEntities.ARROW_TURRET, 0x0CA207, 0xA2A009, defaults(), ContainerItem.Unit.TURRET));
        BULLET_TURRET = ITEMS.register("bullet_turret_item", () -> new ContainerItem(TEntities.BULLET_TURRET, 0xA2A1A0, 0x009EA2, defaults(), ContainerItem.Unit.TURRET));
        FIRECHARGE_TURRET = ITEMS.register("firecharge_turret_item", () -> new ContainerItem(TEntities.FIRE_CHARGE_TURRET, 0x0, 0xA20005, defaults(), ContainerItem.Unit.TURRET));
        SEMI_STEEL = ITEMS.register("semi_steel", () -> new Item(defaults()));
        STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(defaults()));
        BRICK_TURRET = ITEMS.register("brick_turret_item", () -> new ContainerItem(TEntities.BRICK_TURRET, 0x0B00FF, 0xFF6C02, defaults(), ContainerItem.Unit.TURRET));
        GAUSS_BULLET = ITEMS.register("gauss_bullet", () -> new Item(defaults()));
        GAUSS_TURRET = ITEMS.register("gauss_turret_item", () -> new ContainerItem(TEntities.GAUSS_TURRET, 0xA0A0A0, 0x505050, defaults(), ContainerItem.Unit.TURRET));
        COBBLE_TURRET = ITEMS.register("cobble_turret_item", () -> new ContainerItem(TEntities.COBBLE_TURRET, 0x46778b, 0x2d4c59, defaults(), ContainerItem.Unit.TURRET));

        BRICK_DRONE = ITEMS.register("brick_drone_item", () -> new ContainerItem(TEntities.BRICK_DRONE, 0xFF6C02, 0x0B00FF, defaults(), ContainerItem.Unit.DRONE));
        BULLET_DRONE = ITEMS.register("bullet_drone_item", () -> new ContainerItem(TEntities.BULLET_DRONE, 0x009EA2, 0xA2A1A0, defaults(), ContainerItem.Unit.DRONE));
        COBBLE_DRONE = ITEMS.register("cobble_drone_item", () -> new ContainerItem(TEntities.COBBLE_DRONE, 0x2d4c59, 0x46778b, defaults(), ContainerItem.Unit.DRONE));
        ARROW_DRONE = ITEMS.register("arrow_drone_item", () -> new ContainerItem(TEntities.ARROW_DRONE, 0xA2A009, 0x0CA207, defaults(), ContainerItem.Unit.DRONE));
        GAUSS_DRONE = ITEMS.register("gauss_drone_item", () -> new ContainerItem(TEntities.GAUSS_DRONE, 0x505050, 0xA0A0A0, defaults(), ContainerItem.Unit.DRONE));
        FIRE_CHARGE_DRONE = ITEMS.register("firecharge_drone_item", () -> new ContainerItem(TEntities.FIRECHARGE_DRONE, 0xA20005, 0x0, defaults(), ContainerItem.Unit.DRONE));

        EXPLOSIVE_POWDER = ITEMS.register("explosive_powder", () -> new Item(defaults()));
    }

    private static Item.Properties defaults() {
        return new Item.Properties();
    }
}
