package dev.buildtool.kturrets.arrow;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.registers.KContainers;
import dev.buildtool.kturrets.registers.KItems;
import dev.buildtool.satako.Constants;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ArrowDroneContainer extends Container2 {
    public ArrowDroneContainer(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        super(KContainers.ARROW_DRONE.get(), i);
        ArrowDrone arrowDrone = (ArrowDrone) inventory.player.level().getEntity(friendlyByteBuf.readInt());
        addSlot(new ItemHandlerSlot(arrowDrone.weapon, 0, 4 * 18, 0).setTooltip(List.of(Component.translatable("k_turrets.bow.or.crossbow"))).setColor(Constants.GREEN));
        int slot = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(arrowDrone.ammo, slot++, k * 18, j * 18 + 18 * 2));
            }
        }

        addSlot(new ItemHandlerSlot(arrowDrone.upgrades,0,3*18,4*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));
        addSlot(new ItemHandlerSlot(arrowDrone.upgrades,1,4*18,4*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));
        addSlot(new ItemHandlerSlot(arrowDrone.upgrades,2,5*18,4*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));
        addPlayerInventory(0, 6 * 18, inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 21) {
            if ((itemStack.getItem() instanceof BowItem || itemStack.getItem() instanceof CrossbowItem) && !moveItemStackTo(itemStack, 0, 1, false))
                return ItemStack.EMPTY;
            else if (itemStack.is(KTurrets.ARROW_UNIT_AMMO_TAG) && !moveItemStackTo(itemStack, 1, 19, false))
                return ItemStack.EMPTY;
            else if ((itemStack.is(KItems.LIGHT_UPGRADE.get()) || itemStack.is(KItems.EXP_LINK.get()) || itemStack.is(KItems.RECALL_UPGRADE.get())) &&!moveItemStackTo(itemStack,19,22,false)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!moveItemStackTo(itemStack, 22, slots.size(), false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
