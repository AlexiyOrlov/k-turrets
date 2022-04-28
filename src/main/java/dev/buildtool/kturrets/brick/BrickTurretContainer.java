package dev.buildtool.kturrets.brick;

import dev.buildtool.kturrets.registers.TContainers;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BrickTurretContainer extends Container2 {
    public BrickTurretContainer(int i, Inventory playerInventory, FriendlyByteBuf buffer) {
        super(TContainers.BRICK_TURRET, i);
        BrickTurret turret = (BrickTurret) playerInventory.player.level.getEntity(buffer.readInt());
        int index = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(turret.bricks, index++, k * 18, j * 18));
            }
        }

        addPlayerInventory(0, 4 * 18, playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 26) {
            if ((itemStack.getItem() == Items.BRICK || itemStack.getItem() == Items.NETHER_BRICK) && !moveItemStackTo(itemStack, 0, 27, false))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(itemStack, 27, 63, false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
