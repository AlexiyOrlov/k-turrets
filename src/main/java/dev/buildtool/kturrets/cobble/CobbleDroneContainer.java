package dev.buildtool.kturrets.cobble;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.registers.KContainers;
import dev.buildtool.kturrets.registers.KItems;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.Functions;
import dev.buildtool.satako.gui.ItemHandlerSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CobbleDroneContainer extends Container2 {
    public CobbleDroneContainer(int i, Inventory playerInventory, FriendlyByteBuf buffer) {
        super(KContainers.COBBLE_DRONE.get(), i);
        CobbleDrone cobbleDrone = (CobbleDrone) playerInventory.player.level().getEntity(buffer.readInt());
        int index = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(cobbleDrone.stone, index++, k * 18, j * 18));
            }
        }

        addSlot(new ItemHandlerSlot(cobbleDrone.upgrades,0,4*18,3*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.recall.upgrade.slot"))));

        addPlayerInventory(0, 4 * 18, playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 17) {
            if (itemStack.is(KTurrets.COBBLE_UNIT_AMMO_TAG) && !moveItemStackTo(itemStack, 0, 18, false))
                return ItemStack.EMPTY;
            else if (itemStack.is(KItems.LIGHT_UPGRADE.get()) && !moveItemStackTo(itemStack, 18, 19, false)) {
                return ItemStack.EMPTY;
            } else if (itemStack.is(KItems.RECALL_UPGRADE.get()) && !moveItemStackTo(itemStack, 19, 20, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!moveItemStackTo(itemStack, 20, slots.size(), false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
