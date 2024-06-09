package dev.buildtool.kturrets.gauss;

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

public class GaussTurretContainer extends Container2 {
    public GaussTurretContainer(int i, PlayerInventory inventory, PacketBuffer buffer) {
        super(KContainers.GAUSS_TURRET, i);
        GaussTurret turret = (GaussTurret) inventory.player.level.getEntity(buffer.readInt());
        int ind = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(turret.ammo, ind++, k * 18, j * 18));
            }
        }

        addPlayerInventory(0, 4 * 18, inventory);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack stack = getSlot(index).getItem();
        ItemStack stack1 = ItemStack.EMPTY;
        Item ammo = ForgeRegistries.ITEMS.getValue(new ResourceLocation(KTurrets.GAUSS_TURRET_AMMO.get()));
        if (stack.getItem() == ammo && index > 26) {
            stack1 = stack.copy();
            if (!moveItemStackTo(stack, 0, 27, false))
                return ItemStack.EMPTY;
        } else if (index < 27) {
            if (!moveItemStackTo(stack, 27, 63, false))
                return ItemStack.EMPTY;
        }
        return stack1;
    }
}
