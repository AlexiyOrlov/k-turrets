package dev.buildtool.kturrets.gauss;

import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.TItems;
import dev.buildtool.satako.Functions;
import dev.buildtool.satako.ItemHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class GaussTurret extends Turret {
    protected ItemHandler ammo = new ItemHandler(27) {
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (stack.getItem() == TItems.GAUSS_BULLET.get())
                return super.insertItem(slot, stack, simulate);
            return stack;
        }
    };

    public GaussTurret(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(5, new RangedAttackGoal(this, 0, 20, (float) getRange()));
        targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, LivingEntity.class, 0, true, true,
                livingEntity -> {
                    if (isProtectingFromPlayers() && livingEntity instanceof PlayerEntity)
                        return alienPlayers.test((LivingEntity) livingEntity);
                    if (livingEntity instanceof LivingEntity) {
                        LivingEntity entity = (LivingEntity) livingEntity;
                        return decodeTargets(getTargets()).contains(entity.getType());
                    }
                    return false;
                }) {
            @Override
            public boolean canUse() {
                return !ammo.isEmpty() && super.canUse();
            }
        });
    }

    @Override
    protected List<ItemHandler> getContainedItems() {
        return Collections.singletonList(ammo);
    }

    @Override
    public Item getSpawnItem() {
        return null;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {

    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        PacketBuffer buffer = Functions.emptyBuffer();
        buffer.writeInt(getId());
        return null;
    }
}
