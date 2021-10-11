package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import net.minecraft.item.*;
import net.minecraftforge.registries.DeferredRegister;

public class TItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, KTurrets.ID);
    public static final ItemGroup ITEM_GROUP = new ItemGroup("k-turrets") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.AIR);
        }
    };

    static {
        ITEMS.register("arrow_turret_item", () -> new SpawnEggItem(TEntities.ARROW_TURRET, 0x0, 0x0, new Item.Properties().tab(ITEM_GROUP)));
        ITEMS.register("bullet_turret_item", () -> new SpawnEggItem(TEntities.BULLET_TURRET, 0x0, 0x0, new Item.Properties().tab(ITEM_GROUP)));
        ITEMS.register("firecharge_turret_item", () -> new SpawnEggItem(TEntities.FIRE_CHARGE_TURRET, 0x0, 0x0, new Item.Properties().tab(ITEM_GROUP)));
    }
}
