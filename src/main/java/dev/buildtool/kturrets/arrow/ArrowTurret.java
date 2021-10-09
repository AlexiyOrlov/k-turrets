package dev.buildtool.kturrets.arrow;

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
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class ArrowTurret extends Turret {
    protected final ItemHandler weapon = new ItemHandler(1) {
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem)
                return super.insertItem(slot, stack, simulate);
            return stack;
        }
    };

    protected final ItemHandler ammo = new ItemHandler(27) {
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
        if (target.isAlive()) {
            ItemStack weapon = this.weapon.getStackInSlot(0);
            if (!weapon.isEmpty()) {
                for (ItemStack arrows : ammo.getItems()) {
                    if (arrows.getItem() instanceof ArrowItem) {
                        AbstractArrowEntity arrowEntity = ProjectileHelper.getMobArrow(this, arrows, distanceFactor);
                        double d0 = target.getX() - this.getX();
                        double d1 = target.getEyeY() - getEyeY();
                        double d2 = target.getZ() - this.getZ();
                        arrowEntity.shoot(d0, d1, d2, 1.8F, 0);
                        double damage = getDamage();
                        if (weapon.getItem() instanceof BowItem) {
                            arrowEntity.setBaseDamage(damage);
                        } else if (weapon.getItem() instanceof CrossbowItem)
                            arrowEntity.setBaseDamage(damage * 1.2);
                        Arrow2 arrow2 = new Arrow2(level, arrowEntity, this, distanceFactor);
                        arrow2.setNoGravity(true);
                        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
                        this.level.addFreshEntity(arrow2);
                        arrows.shrink(1);
                        weapon.hurtAndBreak(1, this, turret -> turret.broadcastBreakEvent(Hand.MAIN_HAND));
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        weapon.deserializeNBT(compoundNBT.getCompound("Weapon"));
        ammo.deserializeNBT(compoundNBT.getCompound("Ammo"));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.put("Ammo", ammo.serializeNBT());
        compoundNBT.put("Weapon", weapon.serializeNBT());
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeInt(getId());
        return new ArrowTurretContainer(p_createMenu_1_, p_createMenu_2_, packetBuffer);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(5, new RangedAttackGoal(this, 0, 13, (float) getRange()));
        targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, LivingEntity.class, 0, true, true,
                livingEntity -> {
                    if (livingEntity instanceof LivingEntity) {
                        LivingEntity mobEntity = (LivingEntity) livingEntity;
                        return decodeTargets(getTargets()).contains(mobEntity.getType());
                    }
                    return false;
                }) {
            @Override
            public boolean canUse() {
                return !weapon.getStackInSlot(0).isEmpty() && !ammo.isEmpty() && super.canUse();
            }
        });
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity playerEntity, Hand p_230254_2_) {
        if (playerEntity.isCrouching()) {
            if (playerEntity instanceof ServerPlayerEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) playerEntity, this, packetBuffer -> packetBuffer.writeInt(getId()));
            }
            return ActionResultType.SUCCESS;
        } else
            return super.mobInteract(playerEntity, p_230254_2_);
    }
}
