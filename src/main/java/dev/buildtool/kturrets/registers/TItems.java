package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class TItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, KTurrets.ID);
    public static final ItemGroup ITEM_GROUP = new ItemGroup("k-turrets") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(SEMI_STEEL.get());
        }
    };
    public static RegistryObject<Item> SEMI_STEEL;
    public static RegistryObject<Item> STEEL_INGOT;
    public static RegistryObject<Item> ARROW_TURRET;
    public static RegistryObject<Item> BRICK_TURRET;
    public static RegistryObject<Item> BULLET_TURRET;
    public static RegistryObject<Item> FIRECHARGE_TURRET;
    public static RegistryObject<Item> GAUSS_BULLET;
    public static RegistryObject<Item> GAUSS_TURRET;
    public static RegistryObject<Item> COBBLE_TURRET;

    static {
        ARROW_TURRET = ITEMS.register("arrow_turret_item", () -> new SpawnEggItem(TEntities.ARROW_TURRET, 0x0CA207, 0xA2A009, defaults()));
        BULLET_TURRET = ITEMS.register("bullet_turret_item", () -> new SpawnEggItem(TEntities.BULLET_TURRET, 0xA2A1A0, 0x009EA2, defaults()));
        FIRECHARGE_TURRET = ITEMS.register("firecharge_turret_item", () -> new SpawnEggItem(TEntities.FIRE_CHARGE_TURRET, 0x0, 0xA20005, defaults()));
        SEMI_STEEL = ITEMS.register("semi_steel", () -> new Item(defaults()));
        STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(defaults()));
        BRICK_TURRET = ITEMS.register("brick_turret_item", () -> new SpawnEggItem(TEntities.BRICK_TURRET, 0x0B00FF, 0xFF6C02, defaults()));
        GAUSS_BULLET = ITEMS.register("gauss_bullet", () -> new Item(defaults()));
        GAUSS_TURRET = ITEMS.register("gauss_turret_item", () -> new SpawnEggItem(TEntities.GAUSS_TURRET, 0xA0A0A0, 0x505050, defaults()));
        COBBLE_TURRET = ITEMS.register("cobble_turret_item", () -> new SpawnEggItem(TEntities.COBBLE_TURRET, 0x46778b, 0x2d4c59, defaults()));
    }

    private static Item.Properties defaults() {
        return new Item.Properties().tab(ITEM_GROUP);
    }
}
