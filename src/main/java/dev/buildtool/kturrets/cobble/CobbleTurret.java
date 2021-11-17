package dev.buildtool.kturrets.cobble;

import dev.buildtool.kturrets.Turret;
import dev.buildtool.satako.ItemHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CobbleTurret extends Turret {
    protected ItemHandler cobblestone = new ItemHandler(27) {
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (stack.getItem() == Items.COBBLESTONE)
                return super.insertItem(slot, stack, simulate);
            return stack;
        }
    };

    public CobbleTurret(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void registerGoals() {

    }

    @Override
    protected List<ItemHandler> getContainedItems() {
        return Collections.singletonList(cobblestone);
    }

    @Override
    public Item getSpawnItem() {
        return null;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float p_82196_2_) {

    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return null;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.put("Ammo", cobblestone.serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        cobblestone.deserializeNBT(compoundNBT.getCompound("Ammo"));
    }
}
