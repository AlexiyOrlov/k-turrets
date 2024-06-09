package dev.buildtool.kturrets.cobble;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.registers.KContainers;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class CobbleTurretContainer extends Container2 {
    public CobbleTurretContainer(int i, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(KContainers.COBBLE_TURRET, i);
        CobbleTurret cobbleTurret = (CobbleTurret) playerInventory.player.level.getEntity(packetBuffer.readInt());
        int index = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(cobbleTurret.ammo, index++, k * 18, j * 18));
            }
        }

        addPlayerInventory(0, 4 * 18, playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 26) {
            if (KTurrets.USE_CUSTOM_COBBLE_TURRET_AMMO.get()) {
                Item customAmmo = ForgeRegistries.ITEMS.getValue(new ResourceLocation(KTurrets.COBBLE_TURRET_AMMO.get()));
                if (itemStack.getItem() == customAmmo && !moveItemStackTo(itemStack, 0, 27, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (ItemTags.STONE_TOOL_MATERIALS.contains(itemStack.getItem()) && !moveItemStackTo(itemStack, 0, 27, false))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(itemStack, 27, 63, false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
