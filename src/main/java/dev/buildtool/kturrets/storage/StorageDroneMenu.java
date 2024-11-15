package dev.buildtool.kturrets.storage;

import dev.buildtool.kturrets.registers.KContainers;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class StorageDroneMenu extends Container2 {
    public StorageDroneMenu(int i, Inventory inventory, FriendlyByteBuf byteBuf) {
        super(KContainers.STORAGE_DRONE.get(), i);
        StorageDrone storageDrone = (StorageDrone) inventory.player.level().getEntity(byteBuf.readInt());
        int index = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(storageDrone.itemHandler, index++, 18 * k, 18 * j));
            }
        }

        addPlayerInventory(inventory.player, 0, 4 * 18);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack stack = getSlot(index).getItem();
        if (index < 27) {
            if (!moveItemStackTo(stack, 27, slots.size(), false))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(stack, 0, 27, false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
