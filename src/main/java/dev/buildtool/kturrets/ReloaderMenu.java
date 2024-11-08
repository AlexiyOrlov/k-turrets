package dev.buildtool.kturrets;

import dev.buildtool.kturrets.registers.KContainers;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.IntegerColor;
import dev.buildtool.satako.ItemHandlerSlot;
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
        for (int j = 0; j < 7; j++) {
            for (int k = 0; k < 18; k++) {
                addSlot(new ItemHandlerSlot(reloaderBlockEntity.ammo, index++, k * 18, j * 18).setColor(new IntegerColor(0xFFF08A89)));
            }
        }
        addPlayerInventory(18 * 4 + 9, 18 * 7, player);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 107) {
            if (!moveItemStackTo(itemStack, 0, 108, false))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(itemStack, 108, slots.size(), false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
