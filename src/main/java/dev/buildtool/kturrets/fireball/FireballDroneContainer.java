package dev.buildtool.kturrets.fireball;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.registers.KContainers;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class FireballDroneContainer extends Container2 {
    public FireballDroneContainer(int i, PlayerInventory inventory, PacketBuffer friendlyByteBuf) {
        super(KContainers.FIRECHARGE_DRONE, i);
        FireballDrone fireballDrone = (FireballDrone) inventory.player.level.getEntity(friendlyByteBuf.readInt());
        int index = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(fireballDrone.ammo, index++, k * 18, j * 18));
            }
        }

        addPlayerInventory(0, 4 * 18, inventory);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 17) {
            Item ammo = ForgeRegistries.ITEMS.getValue(new ResourceLocation(KTurrets.FIREBALL_TURRET_AMMO.get()));
            if (itemStack.getItem() == ammo && !moveItemStackTo(itemStack, 0, 18, false))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(itemStack, 18, 54, false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
