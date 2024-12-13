package dev.buildtool.kturrets.arrow;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.registers.KContainers;
import dev.buildtool.kturrets.registers.KItems;
import dev.buildtool.satako.Constants;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.ElementsModel;

import java.util.List;

public class ArrowTurretContainer extends Container2 {
    public ArrowTurretContainer(int i, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        super(KContainers.ARROW_TURRET.get(), i);
        ArrowTurret turret = (ArrowTurret) playerInventory.player.level().getEntity(packetBuffer.readInt());
        addSlot(new ItemHandlerSlot(turret.weapon, 0, 4 * 18, 0).setColor(Constants.GREEN).setTooltip(List.of(Component.translatable("k_turrets.bow.or.crossbow"))));
        int slot = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(turret.ammo, slot++, k * 18, j * 18 + 18));
            }
        }
        addSlot(new ItemHandlerSlot(turret.upgrades,0,3*18,4*18).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))).setColor(KTurrets.upgradeSlotColor));
        addSlot(new ItemHandlerSlot(turret.upgrades,1,4*18,4*18).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))).setColor(KTurrets.upgradeSlotColor));
        addSlot(new ItemHandlerSlot(turret.upgrades,2,5*18,4*18).setTooltip(List.of(Component.translatable("k_turrets.upgrade.slot"))).setColor(KTurrets.upgradeSlotColor));

        addPlayerInventory(0, 6* 18, playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = getSlot(index).getItem();
        if (index >KTurrets.turretSlotCount+1)
        {
            if(itemStack.getItem() instanceof BowItem || itemStack.getItem() instanceof CrossbowItem) {
                if (!moveItemStackTo(itemStack, 0, 1, false))
                    return ItemStack.EMPTY;
            } else if (itemStack.is(KTurrets.ARROW_UNIT_AMMO_TAG)) {
                if(!moveItemStackTo(itemStack,1,KTurrets.turretSlotCount-KTurrets.turretUpgradeCount,false))
                    return ItemStack.EMPTY;
            } else if (itemStack.is(KItems.EXP_LINK.get()) || itemStack.is(KItems.FIRE_SHIELD.get()) || itemStack.is(KItems.LOOTING_LINK.get())) {
                if(!moveItemStackTo(itemStack,KTurrets.turretSlotCount-KTurrets.turretUpgradeCount+2,KTurrets.turretSlotCount,false))
                    return ItemStack.EMPTY;
            }
        } else if (!moveItemStackTo(itemStack, KTurrets.turretSlotCount + 2, slots.size(),false)) {
            return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
