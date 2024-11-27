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
        addSlot(new ItemHandlerSlot(bulletDrone.upgrades,0,4*18,3*18).setColor(KTurrets.upgradeSlotColor).setTooltip(List.of(Component.translatable("k_turrets.recall.upgrade.slot"))));

        addPlayerInventory(0, 4 * 18, playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 18) {
            if ((itemStack.is(KTurrets.BULLET_UNIT_AMMO_TAG1) || itemStack.is(KTurrets.BULLET_UNIT_AMMO_TAG2)) && !moveItemStackTo(itemStack, 0, 18, false))
                return ItemStack.EMPTY;
            else if (itemStack.is(KItems.RECALL_UPGRADE.get()) && !moveItemStackTo(itemStack, 28, 29, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!moveItemStackTo(itemStack, 19, slots.size(), false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
