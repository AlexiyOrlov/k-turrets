package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KTurrets.ID);
    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab("k_turrets") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(GAUSS_BULLET.get());
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
        ARROW_TURRET = ITEMS.register("arrow_turret_item", () -> new ForgeSpawnEggItem(TEntities.ARROW_TURRET, 0x0CA207, 0xA2A009, defaults()));
        BULLET_TURRET = ITEMS.register("bullet_turret_item", () -> new ForgeSpawnEggItem(TEntities.BULLET_TURRET, 0xA2A1A0, 0x009EA2, defaults()));
        FIRECHARGE_TURRET = ITEMS.register("firecharge_turret_item", () -> new ForgeSpawnEggItem(TEntities.FIRE_CHARGE_TURRET, 0x0, 0xA20005, defaults()));
        SEMI_STEEL = ITEMS.register("semi_steel", () -> new Item(defaults()));
        STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(defaults()));
        BRICK_TURRET = ITEMS.register("brick_turret_item", () -> new ForgeSpawnEggItem(TEntities.BRICK_TURRET, 0x0B00FF, 0xFF6C02, defaults()));
        GAUSS_BULLET = ITEMS.register("gauss_bullet", () -> new Item(defaults()));
        GAUSS_TURRET = ITEMS.register("gauss_turret_item", () -> new ForgeSpawnEggItem(TEntities.GAUSS_TURRET, 0xA0A0A0, 0x505050, defaults()));
        COBBLE_TURRET = ITEMS.register("cobble_turret_item", () -> new ForgeSpawnEggItem(TEntities.COBBLE_TURRET, 0x46778b, 0x2d4c59, defaults()));
    }

    private static Item.Properties defaults() {
        return new Item.Properties().tab(ITEM_GROUP);
    }
}
