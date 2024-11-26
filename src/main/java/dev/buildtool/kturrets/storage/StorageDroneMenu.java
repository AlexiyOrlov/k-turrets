package dev.buildtool.kturrets.storage;

import dev.buildtool.kturrets.registers.KContainers;
import dev.buildtool.kturrets.registers.KItems;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.IntegerColor;
import dev.buildtool.satako.gui.ItemHandlerSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

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
        IntegerColor upgradeSlotColor=new IntegerColor(0xfffa5c82);
        addSlot(new ItemHandlerSlot(storageDrone.upgrades, 0,3*18+9,3*18).setColor(upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.lantern.slot"))));
        addSlot(new ItemHandlerSlot(storageDrone.upgrades, 1,4*18+9,3*18).setColor(upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.magnet.slot"))));

        addPlayerInventory(inventory.player, 0, 5 * 18);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack stack = getSlot(index).getItem();
        if (index < 29) {
            if (!moveItemStackTo(stack, 29, slots.size(), false))
                return ItemStack.EMPTY;
        }
        else {
            if(stack.is(KItems.LIGHT_UPGRADE.get()))
            {
                if(!moveItemStackTo(stack,27,28,false))
                    return ItemStack.EMPTY;
                else if (!moveItemStackTo(stack, 0, 27, false))
                    return ItemStack.EMPTY;
            } else if (stack.is(KItems.MAGNET_UPGRADE.get())) {
                if(!moveItemStackTo(stack,28,29,false))
                    return ItemStack.EMPTY;
                else if (!moveItemStackTo(stack, 0, 27, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(stack,0,27,false)) {
                return ItemStack.EMPTY;
            }
        }
        return super.quickMoveStack(playerIn, index);
    }
}
