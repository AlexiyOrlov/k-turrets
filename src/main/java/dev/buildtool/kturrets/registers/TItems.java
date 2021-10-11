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

    static {
        ITEMS.register("arrow_turret_item", () -> new SpawnEggItem(TEntities.ARROW_TURRET, 0x0, 0x0, defaults()));
        ITEMS.register("bullet_turret_item", () -> new SpawnEggItem(TEntities.BULLET_TURRET, 0x0, 0x0, defaults()));
        ITEMS.register("firecharge_turret_item", () -> new SpawnEggItem(TEntities.FIRE_CHARGE_TURRET, 0x0, 0x0, defaults()));
        SEMI_STEEL = ITEMS.register("semi_steel", () -> new Item(defaults()));
        STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(defaults()));
    }

    private static Item.Properties defaults() {
        return new Item.Properties().tab(ITEM_GROUP);
    }
}
