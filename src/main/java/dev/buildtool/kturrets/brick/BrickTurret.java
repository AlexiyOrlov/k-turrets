package dev.buildtool.kturrets.brick;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.KEntities;
import dev.buildtool.kturrets.tasks.AttackTargetGoal;
import dev.buildtool.satako.Functions;
import dev.buildtool.satako.ItemHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class BrickTurret extends Turret {
    protected ItemHandler bricks = new ItemHandler(27) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (KTurrets.USE_CUSTOM_BRICK_TURRET_AMMO.get()) {
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(KTurrets.BRICK_TURRET_AMMO.get()));
                return stack.getItem() == item;
            } else
                return stack.getItem() == Items.BRICK || stack.getItem() == Items.NETHER_BRICK;
        }
    };

    public BrickTurret(World world) {
        super(KEntities.BRICK_TURRET, world);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(5, new RangedAttackGoal(this, 0, KTurrets.BRICK_TURRET_RATE.get(), (float) getRange()));
        targetSelector.addGoal(5, new AttackTargetGoal(this));
    }

    @Override
    protected List<ItemHandler> getContainedItems() {
        return Collections.singletonList(bricks);
    }

    @Override
    public boolean isArmed() {
        return !bricks.isEmpty();
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distFactor) {
        if (target.isAlive()) {
            for (ItemStack bricksItem : bricks.getItems()) {
                if (bricksItem.getItem() == Items.NETHER_BRICK || bricksItem.getItem() == Items.BRICK) {
                    double xa = target.getX() - getX();
                    double ya = target.getEyeY() - getEyeY();
                    double za = target.getZ() - getZ();
                    Brick brick = new Brick(this, xa, ya, za, level);
                    brick.setDamage(bricksItem.getItem() == Items.BRICK ? KTurrets.BRICK_DAMAGE.get() : KTurrets.NETHERBRICK_DAMAGE.get());
                    level.addFreshEntity(brick);
                    level.playSound(null, blockPosition(), SoundEvents.WITCH_THROW, SoundCategory.NEUTRAL, 1, 0.5f);
                    bricksItem.shrink(1);
                    break;
                }
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        PacketBuffer buffer = Functions.emptyBuffer();
        buffer.writeInt(getId());
        return new BrickTurretContainer(p_createMenu_1_, p_createMenu_2_, buffer);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.put("Ammo", bricks.serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        bricks.deserializeNBT(compoundNBT.getCompound("Ammo"));
    }
}
