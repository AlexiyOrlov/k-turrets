package dev.buildtool.kturrets;

import dev.buildtool.kturrets.registers.TEntities;
import dev.buildtool.satako.ItemHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ArrowTurret extends Turret {
    private ItemHandler weapon = new ItemHandler(1) {
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem)
                return super.insertItem(slot, stack, simulate);
            return stack;
        }
    };

    private ItemHandler ammo = new ItemHandler(36) {
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (stack.getItem() instanceof ArrowItem)
                return super.insertItem(slot, stack, simulate);
            return stack;
        }
    };

    public ArrowTurret(World world) {
        super(TEntities.ARROW_TURRET, world);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {

    }

    @Override
    public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        weapon.deserializeNBT(p_70037_1_.getCompound("Weapon"));
        ammo.deserializeNBT(p_70037_1_.getCompound("Ammo"));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);
        p_213281_1_.put("Ammo", ammo.serializeNBT());
        p_213281_1_.put("Weapon", weapon.serializeNBT());
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return null;
    }
}
