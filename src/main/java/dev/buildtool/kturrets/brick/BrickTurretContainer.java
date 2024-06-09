package dev.buildtool.kturrets.brick;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.registers.KContainers;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class BrickTurretContainer extends Container2 {
    public BrickTurretContainer(int i, PlayerInventory playerInventory, PacketBuffer buffer) {
        super(KContainers.BRICK_TURRET, i);
        BrickTurret turret = (BrickTurret) playerInventory.player.level.getEntity(buffer.readInt());
        int index = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(turret.bricks, index++, k * 18, j * 18));
            }
        }

        addPlayerInventory(0, 4 * 18, playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 26) {
            if (KTurrets.USE_CUSTOM_BRICK_TURRET_AMMO.get()) {
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(KTurrets.BRICK_TURRET_AMMO.get()));
                if (itemStack.getItem() == item && !moveItemStackTo(itemStack, 0, 27, false))
                    return ItemStack.EMPTY;
            } else if ((itemStack.getItem() == Items.BRICK || itemStack.getItem() == Items.NETHER_BRICK) && !moveItemStackTo(itemStack, 0, 18, false))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(itemStack, 27, 63, false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
