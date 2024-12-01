package dev.buildtool.kturrets.bullet;

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
import net.minecraft.world.item.LeadItem;

import java.util.List;

public class BulletDroneContainer extends Container2 {
    public BulletDroneContainer(int i, Inventory playerInventory, FriendlyByteBuf buffer) {
        super(KContainers.BULLET_DRONE.get(), i);
        BulletDrone bulletDrone = (BulletDrone) playerInventory.player.level().getEntity(buffer.readInt());
        int index = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(bulletDrone.ammo, index++, k * 18, j * 18));
            }
        }
        addSlot(new ItemHandlerSlot(bulletDrone.upgrades,0,3*18,2*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));
        addSlot(new ItemHandlerSlot(bulletDrone.upgrades,1,4*18,2*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));
        addSlot(new ItemHandlerSlot(bulletDrone.upgrades,2,5*18,2*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))));

        addPlayerInventory(0, 4 * 18, playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 20) {
            if ((itemStack.is(KTurrets.BULLET_UNIT_AMMO_TAG1) || itemStack.is(KTurrets.BULLET_UNIT_AMMO_TAG2)) && !moveItemStackTo(itemStack, 0, 18, false))
                return ItemStack.EMPTY;
            else if ((itemStack.is(KItems.LIGHT_UPGRADE.get())|| itemStack.is(KItems.RECALL_UPGRADE.get()) || itemStack.is(KItems.EXP_LINK.get())) && !moveItemStackTo(itemStack, 18, 21, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!moveItemStackTo(itemStack, 21, slots.size(), false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
