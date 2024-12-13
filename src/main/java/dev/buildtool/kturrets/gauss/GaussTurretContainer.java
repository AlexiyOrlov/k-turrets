package dev.buildtool.kturrets.gauss;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.registers.KTContainers;
import dev.buildtool.kturrets.registers.KItems;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class GaussTurretContainer extends Container2 {
    public GaussTurretContainer(int i, Inventory inventory, FriendlyByteBuf buffer) {
        super(KTContainers.GAUSS_TURRET.get(), i);
        GaussTurret turret = (GaussTurret) inventory.player.level().getEntity(buffer.readInt());
        int ind = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(turret.ammo, ind++, k * 18, j * 18));
            }
        }
        addSlot(new ItemHandlerSlot(turret.upgrades, 0,3*18,3*18).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))).setColor(KTurrets.upgradeSlotColor));
        addSlot(new ItemHandlerSlot(turret.upgrades, 1,4*18,3*18).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))).setColor(KTurrets.upgradeSlotColor));
        addSlot(new ItemHandlerSlot(turret.upgrades, 2,5*18,3*18).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))).setColor(KTurrets.upgradeSlotColor));

        addPlayerInventory(0, 5 * 18, inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index > KTurrets.turretSlotCount) {
            if (itemStack.is(KTurrets.GAUSS_UNIT_AMMO_TAG) && !moveItemStackTo(itemStack, 0, KTurrets.turretSlotCount -1, false))
                return ItemStack.EMPTY;
            if((itemStack.is(KItems.EXP_LINK.get()) || itemStack.is(KItems.FIRE_SHIELD.get()) || itemStack.is(KItems.LOOTING_LINK.get())) &&!moveItemStackTo(itemStack,KTurrets.turretSlotCount -KTurrets.turretUpgradeCount+1,KTurrets.turretSlotCount-1+KTurrets.turretUpgradeCount,false))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(itemStack, KTurrets.turretSlotCount+1, slots.size(), false))
                return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
