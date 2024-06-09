package dev.buildtool.kturrets.bullet;

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

public class BulletTurretContainer extends Container2 {
    public BulletTurretContainer(int i, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(KContainers.BULLET_TURRET, i);
        BulletTurret bulletTurret = (BulletTurret) playerInventory.player.level.getEntity(packetBuffer.readInt());
        int slot = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(bulletTurret.ammo, slot++, k * 18, j * 18));
            }
        }
        addPlayerInventory(0, 5 * 18, playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 26) {
            if (KTurrets.USE_CUSTOM_BULLET_TURRET_AMMO.get()) {
                Item ammo = ForgeRegistries.ITEMS.getValue(new ResourceLocation(KTurrets.BULLET_TURRET_AMMO.get()));
                if (itemStack.getItem() == ammo && !moveItemStackTo(itemStack, 0, 27, false))
                    return ItemStack.EMPTY;
            } else if ((itemStack.getItem() == Items.IRON_NUGGET || itemStack.getItem() == Items.GOLD_NUGGET) && !moveItemStackTo(itemStack, 0, 27, false))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(itemStack, 27, 63, false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
