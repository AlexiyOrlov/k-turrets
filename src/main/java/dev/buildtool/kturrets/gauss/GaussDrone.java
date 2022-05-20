package dev.buildtool.kturrets.gauss;

import dev.buildtool.kturrets.Drone;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.registers.Sounds;
import dev.buildtool.kturrets.registers.TEntities;
import dev.buildtool.kturrets.registers.TItems;
import dev.buildtool.satako.Functions;
import dev.buildtool.satako.ItemHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class GaussDrone extends Drone {
    protected ItemHandler ammo = new ItemHandler(27) {
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (stack.getItem() == TItems.GAUSS_BULLET.get())
                return super.insertItem(slot, stack, simulate);
            return stack;
        }
    };

    public GaussDrone(Level world) {
        super(TEntities.GAUSS_DRONE.get(), world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(5, new RangedAttackGoal(this, 1, KTurrets.GAUSS_TURRET_RATE.get(), (float) getRange()));
        targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, LivingEntity.class, 0, true, true,
                livingEntity -> {
                    if (isProtectingFromPlayers() && livingEntity instanceof Player)
                        return alienPlayers.test((LivingEntity) livingEntity);
                    if (livingEntity instanceof LivingEntity mobEntity) {
                        return decodeTargets(getTargets()).contains(mobEntity.getType());
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
    public void performRangedAttack(LivingEntity target, float p_33318_) {
        if (target.isAlive()) {
            for (ItemStack item : ammo.getItems()) {
                if (item.getItem() == TItems.GAUSS_BULLET.get()) {
                    level.playSound(null, blockPosition(), Sounds.GAUSS_SHOT.get(), SoundSource.NEUTRAL, 1.5f, 1);
                    item.shrink(1);
                    double xa = target.getX() - getX();
                    double ya = target.getEyeY() - getEyeY();
                    double za = target.getZ() - getZ();
                    GaussBullet gaussBullet = new GaussBullet(this, xa, ya, za, level);
                    gaussBullet.setDamage(KTurrets.GAUSS_TURRET_DAMAGE.get());
                    gaussBullet.setPos(getX(), getEyeY(), getZ());
                    level.addFreshEntity(gaussBullet);
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
    protected InteractionResult mobInteract(Player playerEntity, InteractionHand p_230254_2_) {
        if (canUse(playerEntity) && !playerEntity.isShiftKeyDown()) {
            if (playerEntity instanceof ServerPlayer) {
                NetworkHooks.openGui((ServerPlayer) playerEntity, this, packetBuffer -> packetBuffer.writeInt(getId()));
            }
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(playerEntity, p_230254_2_);
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
