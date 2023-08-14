package dev.buildtool.kturrets.bullet;

import dev.buildtool.kturrets.registers.KContainers;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;

public class BulletTurretContainer extends Container2 {
    public BulletTurretContainer(int i, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(KContainers.BULLET_TURRET, i);
        BulletTurret bulletTurret = (BulletTurret) playerInventory.player.level.getEntity(packetBuffer.readInt());
        int slot = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(bulletTurret.ammo, slot++, k * 18, j * 18 + 18));
            }
        }
        addPlayerInventory(0, 5 * 18, playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 26) {
            if ((itemStack.getItem() == Items.IRON_NUGGET || itemStack.getItem() == Items.GOLD_NUGGET) && !moveItemStackTo(itemStack, 0, 27, false))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(itemStack, 27, 63, false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
