package dev.buildtool.kturrets.brick;

import dev.buildtool.kturrets.Drone;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.registers.KEntities;
import dev.buildtool.kturrets.registers.Sounds;
import dev.buildtool.kturrets.tasks.AttackTargetGoal;
import dev.buildtool.kturrets.tasks.RestrictedRangedAttackGoal;
import dev.buildtool.satako.Functions;
import dev.buildtool.satako.ItemHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class BrickDrone extends Drone {
    public BrickDrone(Level world) {
        super(KEntities.BRICK_DRONE.get(), world);
    }

    protected ItemHandler ammo = new ItemHandler(18) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return stack.is(KTurrets.BRICK_UNIT_AMMO_TAG1) || stack.is(KTurrets.BRICK_UNIT_AMMO_TAG2);
        }
    };

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(5, new RestrictedRangedAttackGoal(this, 1, KTurrets.BRICK_TURRET_RATE.get(), (float) getRange()));
        targetSelector.addGoal(5, new AttackTargetGoal(this));
    }

    @Override
    protected List<ItemHandler> getContainedItems() {
        return List.of(ammo,upgrades);
    }

    @Override
    public boolean isArmed() {
        return !ammo.isEmpty();
    }

    @Override
    public void performRangedAttack(LivingEntity target, float p_33318_) {
        if (target.isAlive()) {
            for (ItemStack ammoItem : ammo.getItems()) {
                if (!ammoItem.isEmpty()) {
                    double xa = target.getX() - getX();
                    double ya = target.getEyeY() - getEyeY();
                    double za = target.getZ() - getZ();
                    Brick brick = new Brick(this, xa, ya, za, level());
                    brick.setDamage(ammoItem.is(KTurrets.BRICK_UNIT_AMMO_TAG1) ? KTurrets.BRICK_DAMAGE.get() : KTurrets.NETHERBRICK_DAMAGE.get());
                    level().addFreshEntity(brick);
                    playSound(Sounds.BRICK_SHOT.get(), 0.4f, 1);
                    ammoItem.shrink(1);
                    break;
                }
            }
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        FriendlyByteBuf buffer = Functions.emptyBuffer();
        buffer.writeInt(getId());
        return new BrickDroneContainer(p_39954_, p_39955_, buffer);
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

    @Override
    public int getDamage() {
        return KTurrets.BRICK_DAMAGE.get();
    }

    @Override
    public int getSecondaryDamage() {
        return KTurrets.NETHERBRICK_DAMAGE.get();
    }
}
