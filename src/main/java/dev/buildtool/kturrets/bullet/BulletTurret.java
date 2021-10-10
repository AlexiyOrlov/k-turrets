package dev.buildtool.kturrets.bullet;

import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.TEntities;
import dev.buildtool.satako.ItemHandler;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BulletTurret extends Turret {
    protected final ItemHandler ammo = new ItemHandler(27) {
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (stack.getItem() == Items.IRON_NUGGET || stack.getItem() == Items.GOLD_NUGGET)
                return super.insertItem(slot, stack, simulate);
            return stack;
        }
    };

    public BulletTurret(World world) {
        super(TEntities.BULLET_TURRET, world);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(5, new RangedAttackGoal(this, 0, 13, (float) getRange()));
        targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, LivingEntity.class, 0, true, true,
                livingEntity -> {
                    if (livingEntity instanceof LivingEntity) {
                        LivingEntity livingEntity1 = (LivingEntity) livingEntity;
                        return decodeTargets(getTargets()).contains(livingEntity1.getType());
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
    public void performRangedAttack(LivingEntity livingEntity, float v) {
        if (livingEntity.isAlive()) {
            for (ItemStack item : ammo.getItems()) {
                if (item.getItem() == Items.GOLD_NUGGET || item.getItem() == Items.IRON_NUGGET) {
                    double d0 = livingEntity.getX() - this.getX();
                    double d1 = livingEntity.getEyeY() - getEyeY();
                    double d2 = livingEntity.getZ() - this.getZ();
                    Bullet bullet = new Bullet(this, d0, d1, d2, level, item.getItem() == Items.GOLD_NUGGET ? 6 : 7);
                    level.addFreshEntity(bullet);
                    item.shrink(1);
                    break;
                }
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeInt(getId());
        return new BulletTurretContainer(i, playerInventory, packetBuffer);
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity playerEntity, Hand p_230254_2_) {
        if (playerEntity.isCrouching()) {
            if (playerEntity instanceof ServerPlayerEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) playerEntity, this, packetBuffer -> packetBuffer.writeInt(getId()));
            }
            return ActionResultType.SUCCESS;
        }
        return super.mobInteract(playerEntity, p_230254_2_);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.put("Ammo", ammo.serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        ammo.deserializeNBT(compoundNBT.getCompound("Ammo"));
    }
}
