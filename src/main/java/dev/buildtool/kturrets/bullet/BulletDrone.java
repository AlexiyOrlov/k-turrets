package dev.buildtool.kturrets.bullet;

import dev.buildtool.kturrets.Drone;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.registers.Sounds;
import dev.buildtool.kturrets.registers.TEntities;
import dev.buildtool.kturrets.tasks.AttackTargetGoal;
import dev.buildtool.satako.Functions;
import dev.buildtool.satako.ItemHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class BulletDrone extends Drone {
    protected final ItemHandler ammo = new ItemHandler(18) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return stack.is(Items.GOLD_NUGGET) || stack.is(Items.IRON_NUGGET);
        }
    };

    public BulletDrone(Level world) {
        super(TEntities.BULLET_DRONE.get(), world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(5, new RangedAttackGoal(this, 1, KTurrets.BULLET_TURRET_RATE.get(), (float) getRange()));
        targetSelector.addGoal(5, new AttackTargetGoal(this));
    }

    @Override
    protected List<ItemHandler> getContainedItems() {
        return Collections.singletonList(ammo);
    }

    @Override
    public boolean isArmed() {
        return !ammo.isEmpty();
    }

    @Override
    public void performRangedAttack(LivingEntity livingEntity, float p_33318_) {
        if (livingEntity.isAlive()) {
            for (ItemStack item : ammo.getItems()) {
                if (!item.isEmpty()) {
                    double d0 = livingEntity.getX() - this.getX();
                    double d1 = livingEntity.getEyeY() - getEyeY();
                    double d2 = livingEntity.getZ() - this.getZ();
                    Bullet bullet = new Bullet(this, d0, d1, d2, level, item.getItem() == Items.GOLD_NUGGET ? KTurrets.GOLD_BULLET_DAMAGE.get() : KTurrets.IRON_BULLET_DAMAGE.get());
                    level.addFreshEntity(bullet);
                    level.playSound(null, blockPosition(), Sounds.BULLET_FIRE.get(), SoundSource.NEUTRAL, 1, 1);
                    item.shrink(1);
                    break;
                }
            }
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory inventory, Player player) {
        FriendlyByteBuf friendlyByteBuf = Functions.emptyBuffer();
        friendlyByteBuf.writeInt(getId());
        return new BulletDroneContainer(p_39954_, inventory, friendlyByteBuf);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.put("Ammo", ammo.serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        ammo.deserializeNBT(compoundNBT.getCompound("Ammo"));
    }
}
