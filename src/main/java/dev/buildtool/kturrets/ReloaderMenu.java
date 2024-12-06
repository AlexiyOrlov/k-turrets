package dev.buildtool.kturrets;

import dev.buildtool.kturrets.registers.KContainers;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.IntegerColor;
import dev.buildtool.satako.gui.ItemHandlerSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ReloaderMenu extends Container2 {
    private ReloaderBlockEntity reloaderBlockEntity;

    public ReloaderMenu(int i, Inventory inventory, FriendlyByteBuf byteBuf) {
        super(KContainers.RELOADER.get(), i);
        Player player = inventory.player;
        reloaderBlockEntity = (ReloaderBlockEntity) player.level().getBlockEntity(byteBuf.readBlockPos());
        int index = 0;
        for (int j = 0; j < 6; j++) {
            for (int k = 0; k < 18; k++) {
                ItemHandlerSlot slot = new ItemHandlerSlot(reloaderBlockEntity.ammo, index++, k * 18, j * 18);
                if (index <= 18)
                    slot.setColor(new IntegerColor(0xFFF08A89));
                else if (index <= 36) {
                    slot.setColor(new IntegerColor(0xffa48812));
                } else if (index <= 54) {
                    slot.setColor(new IntegerColor(0xffa6bc90));
                } else if (index <= 72) {
                    slot.setColor(new IntegerColor(0xff7cc9e3));
                } else if (index <= 90) {
                    slot.setColor(new IntegerColor(0xff29E034));
                } else
                    slot.setColor(new IntegerColor(0xff9812a5));
                addSlot(slot);
            }
        }
        addPlayerInventory(18 * 4 + 9, 18 * 7, player);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 107) {
            if (itemStack.is(KTurrets.GAUSS_UNIT_AMMO_TAG) && !moveItemStackTo(itemStack, 90, 108, false))
                return ItemStack.EMPTY;
            if (itemStack.is(KTurrets.COBBLE_UNIT_AMMO_TAG) && !moveItemStackTo(itemStack, 72, 90, false))
                return ItemStack.EMPTY;
            if (itemStack.is( KTurrets.BRICK_UNIT_AMMO_TAG) && !moveItemStackTo(itemStack, 36, 54, false))
                return ItemStack.EMPTY;
            if (itemStack.is( KTurrets.BULLET_UNIT_AMMO_TAG) && !moveItemStackTo(itemStack, 18, 36, false))
                return ItemStack.EMPTY;
            if (itemStack.is( KTurrets.FIREBALL_UNIT_AMMO) && !moveItemStackTo(itemStack, 54, 72, false))
                return ItemStack.EMPTY;
            if (itemStack.is( KTurrets.ARROW_UNIT_AMMO_TAG) && !moveItemStackTo(itemStack, 0, 18, false))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(itemStack, 109, slots.size(), false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
