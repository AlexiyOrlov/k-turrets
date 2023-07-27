package dev.buildtool.kturrets.gauss;

import dev.buildtool.kturrets.registers.KContainers;
import dev.buildtool.kturrets.registers.KItems;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GaussDroneContainer extends Container2 {
    public GaussDroneContainer(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        super(KContainers.GAUSS_DRONE.get(), i);
        GaussDrone gaussDrone = (GaussDrone) inventory.player.level().getEntity(friendlyByteBuf.readInt());
        int index = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(gaussDrone.ammo, index++, k * 18, j * 18));
            }
        }

        addPlayerInventory(0, 4 * 18, inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack stack = getSlot(index).getItem();
        ItemStack stack1 = ItemStack.EMPTY;
        if (stack.getItem() == KItems.GAUSS_BULLET.get() && index > 17) {
            stack1 = stack.copy();
            if (!moveItemStackTo(stack, 0, 18, false))
                return ItemStack.EMPTY;
        } else if (index < 18) {
            if (!moveItemStackTo(stack, 18, 54, false))
                return ItemStack.EMPTY;
        }
        return stack1;
    }
}
