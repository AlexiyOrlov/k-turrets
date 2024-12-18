package dev.buildtool.kturrets.storage;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.registers.KTContainers;
import dev.buildtool.kturrets.registers.KTItems;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class StorageDroneMenu extends Container2 {
    StorageDrone storageDrone;
    public StorageDroneMenu(int i, Inventory inventory, FriendlyByteBuf byteBuf) {
        super(KTContainers.STORAGE_DRONE.get(), i);
        storageDrone = (StorageDrone) inventory.player.level().getEntity(byteBuf.readInt());
        int index = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(storageDrone.itemHandler, index++, 18 * k, 18 * j));
            }
        }

        addSlot(new ItemHandlerSlot(storageDrone.upgrades, 0,3*18,3*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));
        addSlot(new ItemHandlerSlot(storageDrone.upgrades, 1,4*18,3*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));
        addSlot(new ItemHandlerSlot(storageDrone.upgrades,2,5*18,3*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));
        addSlot(new ItemHandlerSlot(storageDrone.upgrades,3,6*18,3*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));

        addPlayerInventory(inventory.player, 0, 5 * 18);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack stack = getSlot(index).getItem();
        if (index < 30) {
            if (!moveItemStackTo(stack, 31, slots.size(), false))
                return ItemStack.EMPTY;
        }
        else {
            if((stack.is(KTItems.LIGHT_UPGRADE.get()) || stack.is(KTItems.RECALL_UPGRADE.get()) || stack.is(KTItems.MAGNET_UPGRADE.get()) || stack.is(KTItems.FIRE_SHIELD.get())) && !moveItemStackTo(stack,27,30,false))
                return ItemStack.EMPTY;
            else if(!moveItemStackTo(stack,0,27,false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
