package dev.buildtool.kturrets.gauss;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.registers.KContainers;
import dev.buildtool.kturrets.registers.KItems;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class GaussDroneContainer extends Container2 {
    public GaussDroneContainer(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        super(KContainers.GAUSS_DRONE.get(), i);
        GaussDrone gaussDrone = (GaussDrone) inventory.player.level().getEntity(friendlyByteBuf.readInt());
        int index = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(gaussDrone.ammo, index++, k * 18, j * 18));
            }
        }

        addSlot(new ItemHandlerSlot(gaussDrone.upgrades,0,3*18,2*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));
        addSlot(new ItemHandlerSlot(gaussDrone.upgrades,1,4*18,2*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));
        addSlot(new ItemHandlerSlot(gaussDrone.upgrades,2,5*18,2*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));
        addSlot(new ItemHandlerSlot(gaussDrone.upgrades,3,6*18,2*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));

        addPlayerInventory(0, 4 * 18, inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > KTurrets.droneSlotCount) {
            if (itemStack.is(KTurrets.GAUSS_UNIT_AMMO_TAG) && !moveItemStackTo(itemStack, 0, KTurrets.droneSlotCount-KTurrets.droneUpgradeCount, false))
                return ItemStack.EMPTY;
            else if ((itemStack.is(KItems.LIGHT_UPGRADE.get()) || itemStack.is(KItems.RECALL_UPGRADE.get()) || itemStack.is(KItems.EXP_LINK.get()) || itemStack.is(KItems.FIRE_SHIELD.get())) && !moveItemStackTo(itemStack, KTurrets.droneSlotCount-KTurrets.droneUpgradeCount,KTurrets.droneSlotCount, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!moveItemStackTo(itemStack, KTurrets.droneSlotCount+KTurrets.droneUpgradeCount, slots.size(), false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
