package dev.buildtool.kturrets.arrow;

import dev.buildtool.kturrets.registers.KContainers;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class ArrowDroneContainer extends Container2 {
    public ArrowDroneContainer(int i, PlayerInventory inventory, PacketBuffer friendlyByteBuf) {
        super(KContainers.ARROW_DRONE, i);
        ArrowDrone arrowDrone = (ArrowDrone) inventory.player.level.getEntity(friendlyByteBuf.readInt());
        addSlot(new ItemHandlerSlot(arrowDrone.weapon, 0, 4 * 18, 0));
        int slot = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(arrowDrone.ammo, slot++, k * 18, j * 18 + 18 * 2));
            }
        }

        addPlayerInventory(0, 5 * 18, inventory);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 18) {
            if ((itemStack.getItem() instanceof BowItem || itemStack.getItem() instanceof CrossbowItem) && !moveItemStackTo(itemStack, 0, 1, false))
                return ItemStack.EMPTY;
            if (itemStack.getItem() instanceof ArrowItem && !moveItemStackTo(itemStack, 1, 19, false))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(itemStack, 19, 55, false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
