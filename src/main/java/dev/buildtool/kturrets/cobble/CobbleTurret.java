package dev.buildtool.kturrets.cobble;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.TEntities;
import dev.buildtool.satako.ItemHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
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

    public CobbleTurret(World world) {
        super(TEntities.COBBLE_TURRET, world);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(5, new RangedAttackGoal(this, 0, KTurrets.COBBLE_TURRET_RATE.get(), (float) getRange()));
        targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, LivingEntity.class, 0, true, true,
                livingEntity -> {
                    if (isProtectingFromPlayers() && livingEntity instanceof PlayerEntity)
                        return alienPlayers.test((LivingEntity) livingEntity);
                    if (livingEntity instanceof LivingEntity) {
                        LivingEntity mobEntity = (LivingEntity) livingEntity;
                        return decodeTargets(getTargets()).contains(mobEntity.getType());
                    }
                    return false;
                }) {
            @Override
            public boolean canUse() {
                return !cobblestone.isEmpty() && super.canUse();
            }
        });
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
        if (target.isAlive()) {
            for (ItemStack cobblestoneItem : cobblestone.getItems()) {
                if (cobblestoneItem.getItem() == Items.COBBLESTONE) {
                    double xa = target.getX() - getX();
                    double ya = target.getEyeY() - getEyeY();
                    double za = target.getZ() - getZ();
                    Cobblestone cobblestone = new Cobblestone(this, xa, ya, za, level);
                    cobblestone.setDamage(KTurrets.COBBLE_TURRET_DAMAGE.get());
                    level.addFreshEntity(cobblestone);
                    cobblestoneItem.shrink(1);
                    break;
                }
            }
        }
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
