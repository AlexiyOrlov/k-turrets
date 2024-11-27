package dev.buildtool.kturrets.fireball;

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

public class FireballDroneContainer extends Container2 {
    public FireballDroneContainer(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        super(KContainers.FIRECHARGE_DRONE.get(), i);
        FireballDrone fireballDrone = (FireballDrone) inventory.player.level().getEntity(friendlyByteBuf.readInt());
        int index = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(fireballDrone.ammo, index++, k * 18, j * 18));
            }
        }

        addSlot(new ItemHandlerSlot(fireballDrone.upgrades,0,4*18-9,3*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.recall.upgrade.slot"))));
        addSlot(new ItemHandlerSlot(fireballDrone.upgrades,1,5*18-9,3*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.recall.upgrade.slot"))));

        addPlayerInventory(0, 4 * 18, inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 18) {
            if (itemStack.is(KTurrets.FIREBALL_UNIT_AMMO) && !moveItemStackTo(itemStack, 0, 18, false))
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
