package dev.buildtool.kturrets.brick;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.TEntities;
import dev.buildtool.kturrets.tasks.AttackTargetGoal;
import dev.buildtool.satako.Functions;
import dev.buildtool.satako.ItemHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class BrickTurret extends Turret {
    protected ItemHandler bricks = new ItemHandler(27) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return Functions.isItemIn(stack.getItem(), Tags.Items.INGOTS_BRICK) || stack.is(Items.NETHER_BRICK);
        }
    };

    public BrickTurret(Level world) {
        super(TEntities.BRICK_TURRET.get(), world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
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
                if (!bricksItem.isEmpty()) {
                    double xa = target.getX() - getX();
                    double ya = target.getEyeY() - getEyeY();
                    double za = target.getZ() - getZ();
                    Brick brick = new Brick(this, xa, ya, za, level);
                    brick.setDamage(bricksItem.getItem() == Items.BRICK ? KTurrets.BRICK_DAMAGE.get() : KTurrets.NETHERBRICK_DAMAGE.get());
                    level.addFreshEntity(brick);
                    level.playSound(null, blockPosition(), SoundEvents.WITCH_THROW, SoundSource.NEUTRAL, 1, 0.5f);
                    bricksItem.shrink(1);
                    break;
                }
            }
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_createMenu_1_, Inventory p_createMenu_2_, Player p_createMenu_3_) {
        FriendlyByteBuf buffer = Functions.emptyBuffer();
        buffer.writeInt(getId());
        return new BrickTurretContainer(p_createMenu_1_, p_createMenu_2_, buffer);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.put("Ammo", bricks.serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        bricks.deserializeNBT(compoundNBT.getCompound("Ammo"));
    }
}
