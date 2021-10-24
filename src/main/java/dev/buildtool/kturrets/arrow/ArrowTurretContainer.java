package dev.buildtool.kturrets.arrow;

import dev.buildtool.kturrets.registers.TContainers;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class ArrowTurretContainer extends Container2 {
    public ArrowTurretContainer(int i, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(TContainers.ARROW_TURRET, i);
        ArrowTurret turret = (ArrowTurret) playerInventory.player.level.getEntity(packetBuffer.readInt());
        addSlot(new ItemHandlerSlot(turret.weapon, 0, 4 * 18, 0));
        int slot = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(turret.ammo, slot++, k * 18, j * 18 + 18 * 2));
            }
        }

        addPlayerInventory(0, 5 * 18, playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 27) {
            if ((itemStack.getItem() instanceof BowItem || itemStack.getItem() instanceof CrossbowItem) && !moveItemStackTo(itemStack, 0, 1, false))
                return ItemStack.EMPTY;
            if (itemStack.getItem() instanceof ArrowItem && !moveItemStackTo(itemStack, 1, 28, false))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(itemStack, 28, 64, false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
