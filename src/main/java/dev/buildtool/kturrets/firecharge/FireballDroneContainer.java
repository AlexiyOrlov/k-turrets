package dev.buildtool.kturrets.firecharge;

import dev.buildtool.kturrets.registers.TContainers;
import dev.buildtool.kturrets.registers.TItems;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class FireballDroneContainer extends Container2 {
    public FireballDroneContainer(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        super(TContainers.FIRECHARGE_DRONE.get(), i);
        FireballDrone fireballDrone = (FireballDrone) inventory.player.level.getEntity(friendlyByteBuf.readInt());
        int index = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(fireballDrone.ammo, index++, k * 18, j * 18));
            }
        }

        addPlayerInventory(0, 4 * 18, inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 17) {
            if (itemStack.getItem() == TItems.EXPLOSIVE_POWDER.get() && !moveItemStackTo(itemStack, 0, 18, false))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(itemStack, 18, 54, false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
