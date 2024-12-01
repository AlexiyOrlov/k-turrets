package dev.buildtool.kturrets.gauss;

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

public class GaussTurretContainer extends Container2 {
    public GaussTurretContainer(int i, Inventory inventory, FriendlyByteBuf buffer) {
        super(KContainers.GAUSS_TURRET.get(), i);
        GaussTurret turret = (GaussTurret) inventory.player.level().getEntity(buffer.readInt());
        int ind = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(turret.ammo, ind++, k * 18, j * 18));
            }
        }
        addSlot(new ItemHandlerSlot(turret.upgrades, 0,4*18,3*18).setTooltip(List.of(Component.translatable("k_turrets.exp.link.slot"))).setColor(KTurrets.upgradeSlotColor));

        addPlayerInventory(0, 5 * 18, inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > 27) {
            if (itemStack.is(KTurrets.GAUSS_UNIT_AMMO_TAG) && !moveItemStackTo(itemStack, 0, 27, false))
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
