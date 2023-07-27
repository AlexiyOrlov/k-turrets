package dev.buildtool.kturrets.gauss;

import dev.buildtool.kturrets.Drone;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.registers.KEntities;
import dev.buildtool.kturrets.registers.KItems;
import dev.buildtool.kturrets.registers.Sounds;
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
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class GaussDrone extends Drone {
    protected ItemHandler ammo = new ItemHandler(18) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return stack.is(KItems.GAUSS_BULLET.get());
        }
    };

    public GaussDrone(Level world) {
        super(KEntities.GAUSS_DRONE.get(), world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(5, new RangedAttackGoal(this, 1, KTurrets.GAUSS_TURRET_RATE.get(), (float) getRange()));
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
    public void performRangedAttack(LivingEntity target, float p_33318_) {
        if (target.isAlive()) {
            for (ItemStack item : ammo.getItems()) {
                if (!item.isEmpty()) {
                    level().playSound(null, blockPosition(), Sounds.GAUSS_SHOT.get(), SoundSource.NEUTRAL, 1.5f, 1);
                    item.shrink(1);
                    double xa = target.getX() - getX();
                    double ya = target.getEyeY() - getEyeY();
                    double za = target.getZ() - getZ();
                    GaussBullet gaussBullet = new GaussBullet(this, xa, ya, za, level());
                    gaussBullet.setDamage(KTurrets.GAUSS_TURRET_DAMAGE.get());
                    gaussBullet.setPos(getX(), getEyeY(), getZ());
                    level().addFreshEntity(gaussBullet);
                    break;
                }
            }
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory inventory, Player p_39956_) {
        FriendlyByteBuf friendlyByteBuf = Functions.emptyBuffer();
        friendlyByteBuf.writeInt(getId());
        return new GaussDroneContainer(p_39954_, inventory, friendlyByteBuf);
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
