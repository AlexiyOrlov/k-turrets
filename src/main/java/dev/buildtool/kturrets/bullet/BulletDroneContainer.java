package dev.buildtool.kturrets.bullet;

import dev.buildtool.kturrets.registers.KContainers;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;

public class BulletDroneContainer extends Container2 {
    public BulletDroneContainer(int i, PlayerInventory playerInventory, PacketBuffer buffer) {
        super(KContainers.BULLET_DRONE, i);
        BulletDrone bulletDrone = (BulletDrone) playerInventory.player.level.getEntity(buffer.readInt());
        int index = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(bulletDrone.ammo, index++, k * 18, j * 18));
            }
        }

        addPlayerInventory(0, 4 * 18, playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 17) {
            if ((itemStack.getItem() == Items.IRON_NUGGET || itemStack.getItem() == Items.GOLD_NUGGET) && !moveItemStackTo(itemStack, 0, 18, false))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(itemStack, 18, 54, false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
