package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;

public class TItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Item.class, KTurrets.ID);

    static {
        ITEMS.register("arrow_turret_item", () -> new SpawnEggItem(TEntities.ARROW_TURRET, 0x0, 0x0, new Item.Properties()));
    }
}
