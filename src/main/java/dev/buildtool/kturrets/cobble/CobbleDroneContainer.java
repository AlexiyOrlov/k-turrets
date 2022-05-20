package dev.buildtool.kturrets.cobble;

import dev.buildtool.kturrets.registers.TContainers;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class CobbleDroneContainer extends Container2 {
    public CobbleDroneContainer(int i, Inventory playerInventory, FriendlyByteBuf buffer) {
        super(TContainers.COBBLE_DRONE.get(), i);
        CobbleDrone cobbleDrone = (CobbleDrone) playerInventory.player.level.getEntity(buffer.readInt());
        int index = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(cobbleDrone.stone, index++, k * 18, j * 18));
            }
        }

        addPlayerInventory(0, 4 * 18, playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 17) {
            if (ForgeRegistries.ITEMS.tags().getTag(ItemTags.STONE_TOOL_MATERIALS).contains(itemStack.getItem()) && !moveItemStackTo(itemStack, 0, 18, false))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(itemStack, 18, 54, false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
