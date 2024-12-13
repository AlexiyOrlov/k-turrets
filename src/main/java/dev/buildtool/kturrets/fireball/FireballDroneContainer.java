package dev.buildtool.kturrets.fireball;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.registers.KTContainers;
import dev.buildtool.kturrets.registers.KItems;
import dev.buildtool.satako.Container2;;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class FireballDroneContainer extends Container2 {
    public FireballDroneContainer(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        super(KTContainers.FIRECHARGE_DRONE.get(), i);
        FireballDrone fireballDrone = (FireballDrone) inventory.player.level().getEntity(friendlyByteBuf.readInt());
        int index = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(fireballDrone.ammo, index++, k * 18, j * 18));
            }
        }

        addSlot(new ItemHandlerSlot(fireballDrone.upgrades,0,2*18,2*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));
        addSlot(new ItemHandlerSlot(fireballDrone.upgrades,1,3*18,2*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));
        addSlot(new ItemHandlerSlot(fireballDrone.upgrades,2,4*18,2*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));
        addSlot(new ItemHandlerSlot(fireballDrone.upgrades,3,5*18,2*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));
        addSlot(new ItemHandlerSlot(fireballDrone.upgrades,4,6*18,2*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));

        addPlayerInventory(0, 4 * 18, inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > KTurrets.droneSlotCount-1) {
            if (itemStack.is(KTurrets.FIREBALL_UNIT_AMMO) && !moveItemStackTo(itemStack, 0, KTurrets.droneSlotCount-KTurrets.droneUpgradeCount, false))
                return ItemStack.EMPTY;
            else if ((itemStack.is(KItems.LIGHT_UPGRADE.get()) || itemStack.is(KItems.RECALL_UPGRADE.get()) || itemStack.is(KItems.EXP_LINK.get()) || itemStack.is(KItems.FIRE_SHIELD.get()) || itemStack.is(KItems.LOOTING_LINK.get())) && !moveItemStackTo(itemStack, KTurrets.droneSlotCount-KTurrets.droneUpgradeCount,KTurrets.droneSlotCount, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!moveItemStackTo(itemStack, KTurrets.droneSlotCount, slots.size(), false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
