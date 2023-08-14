package dev.buildtool.kturrets.gauss;

import dev.buildtool.kturrets.registers.TContainers;
import dev.buildtool.kturrets.registers.TItems;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class GaussDroneContainer extends Container2 {
    public GaussDroneContainer(int i, PlayerInventory inventory, PacketBuffer friendlyByteBuf) {
        super(TContainers.GAUSS_DRONE, i);
        GaussDrone gaussDrone = (GaussDrone) inventory.player.level.getEntity(friendlyByteBuf.readInt());
        int index = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(gaussDrone.ammo, index++, k * 18, j * 18));
            }
        }

        addPlayerInventory(0, 4 * 18, inventory);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack stack = getSlot(index).getItem();
        ItemStack stack1 = ItemStack.EMPTY;
        if (stack.getItem() == TItems.GAUSS_BULLET.get() && index > 17) {
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
