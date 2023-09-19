package dev.buildtool.kturrets.arrow;

import dev.buildtool.kturrets.AttackTargetGoal;
import dev.buildtool.kturrets.Drone;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.registers.KEntities;
import dev.buildtool.satako.Functions;
import dev.buildtool.satako.ItemHandler;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class ArrowDrone extends Drone {
    protected final ItemHandler weapon = new ItemHandler(1) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.getItem() instanceof CrossbowItem || stack.getItem() instanceof BowItem;
        }
    };

    protected final ItemHandler ammo = new ItemHandler(18) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.getItem() instanceof ArrowItem;
        }
    };

    public ArrowDrone(World world) {
        super(KEntities.ARROW_DRONE, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(5, new RangedAttackGoal(this, 1, KTurrets.ARROW_TURRET_RATE.get(), (float) getRange()));
        targetSelector.addGoal(5, new AttackTargetGoal(this));
    }

    @Override
    protected List<ItemHandler> getContainedItems() {
        return Arrays.asList(ammo, weapon);
    }

    @Override
    public boolean isArmed() {
        return !weapon.isEmpty() && !ammo.isEmpty();
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        if (target.isAlive()) {
            ItemStack weapon = this.weapon.getStackInSlot(0);
            if (!weapon.isEmpty()) {
                for (ItemStack arrows : ammo.getItems()) {
                    if (!arrows.isEmpty()) {
                        AbstractArrowEntity arrowEntity = ProjectileHelper.getMobArrow(this, arrows, distanceFactor);
                        double xAcceler = (target.getX() - this.getX()) * Arrow2.SPEED;
                        double yAcceler = (target.getEyeY() - getEyeY()) * Arrow2.SPEED;
                        double zAcceler = (target.getZ() - this.getZ()) * Arrow2.SPEED;
                        arrowEntity.shoot(xAcceler, yAcceler, zAcceler, 1.8F, 0);
                        double damage = KTurrets.ARROW_TURRET_DAMAGE.get();
                        arrowEntity.setBaseDamage(damage);
                        Arrow2 arrow2 = new Arrow2(level, arrowEntity, this, distanceFactor);
                        if (weapon.getItem() instanceof BowItem) {
                            arrow2.setBaseDamage(arrowEntity.getBaseDamage());
                        } else if (weapon.getItem() instanceof CrossbowItem) {
                            arrow2.setBaseDamage(arrowEntity.getBaseDamage() * 1.2);
                            arrow2.setShotFromCrossbow(true);
                            int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, weapon);
                            if (i > 0)
                                arrow2.setPierceLevel((byte) i);
                        }
                        arrow2.setEnchantmentEffectsFromEntity(this, distanceFactor);
                        arrow2.setNoGravity(true);
                        arrow2.shoot(xAcceler, yAcceler, zAcceler, 1.8f, 0);
                        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
                        this.level.addFreshEntity(arrow2);
                        arrows.shrink(1);
                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY_ARROWS, this) == 0 && EnchantmentHelper.getEnchantmentLevel(Enchantments.MULTISHOT, this) == 0)
                            weapon.hurtAndBreak(1, this, turret -> turret.broadcastBreakEvent(Hand.MAIN_HAND));
                        break;
                    }
                }
            }
        }

    }

    @Nullable
    @Override
    public Container createMenu(int p_39954_, PlayerInventory inventory, PlayerEntity p_39956_) {
        PacketBuffer byteBuf = Functions.emptyBuffer();
        byteBuf.writeInt(getId());
        return new ArrowDroneContainer(p_39954_, inventory, byteBuf);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.put("Ammo", ammo.serializeNBT());
        compoundNBT.put("Weapon", weapon.serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        weapon.deserializeNBT(compoundNBT.getCompound("Weapon"));
        ammo.deserializeNBT(compoundNBT.getCompound("Ammo"));
    }
}
