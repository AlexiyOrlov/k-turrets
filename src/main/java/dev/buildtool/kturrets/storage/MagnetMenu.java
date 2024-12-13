package dev.buildtool.kturrets.storage;

import dev.buildtool.kturrets.registers.KTContainers;
import dev.buildtool.kturrets.registers.KItems;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class MagnetMenu extends Container2 {
    public MagnetMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        super(KTContainers.MAGNET.get(), i);
        ItemStack magnet=inventory.getSelected();
        magnet.getCapability(ForgeCapabilities.ITEM_HANDLER,null).ifPresent(iItemHandler -> {
            int index=0;
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 9; k++) {
                    addSlot(new ItemHandlerSlot(iItemHandler,index++,k*18,j*18));
                }
            }
        });
        addPlayerInventoryWithLockedItem(0,4*18,inventory.player, KItems.MAGNET_UPGRADE.get());
    }

    @Override
    public void clicked(int pSlotId, int pButton, ClickType pClickType, Player pPlayer) {
        if (pSlotId < 27 && pSlotId >= 0) {
            ItemStack itemStack = getCarried();
            Slot slot1 = getSlot(pSlotId);
            if (pButton == 0) {
                ItemStack copy = itemStack.copy();
                copy.setCount(1);
                slot1.set(copy);
            } else {
                slot1.set(ItemStack.EMPTY);
            }
            broadcastFullState();
        } else super.clicked(pSlotId, pButton,pClickType,pPlayer);
    }
}
