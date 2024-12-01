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

public class BulletTurretContainer extends Container2 {
    public BulletTurretContainer(int i, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        super(KContainers.BULLET_TURRET.get(), i);
        BulletTurret bulletTurret = (BulletTurret) playerInventory.player.level().getEntity(packetBuffer.readInt());
        int slot = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(bulletTurret.ammo, slot++, k * 18, j * 18));
            }
        }
        addSlot(new ItemHandlerSlot(bulletTurret.upgrades, 0,4*18,3*18).setTooltip(List.of(Component.translatable("k_turrets.exp.link.slot"))).setColor(KTurrets.upgradeSlotColor));
        addPlayerInventory(0, 5 * 18, playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 27) {
            if ((itemStack.is(KTurrets.BULLET_UNIT_AMMO_TAG1) || itemStack.is(KTurrets.BULLET_UNIT_AMMO_TAG2)) && !moveItemStackTo(itemStack, 0, 27, false))
                return ItemStack.EMPTY;
            if(itemStack.is(KItems.EXP_LINK.get()) &&!moveItemStackTo(itemStack,27,28,false))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(itemStack, 28, slots.size(), false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
