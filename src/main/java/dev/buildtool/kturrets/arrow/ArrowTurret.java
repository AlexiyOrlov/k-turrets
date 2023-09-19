package dev.buildtool.kturrets.arrow;

import dev.buildtool.kturrets.AttackTargetGoal;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.KEntities;
import dev.buildtool.satako.ItemHandler;
import io.netty.buffer.Unpooled;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
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
        super(KEntities.ARROW_TURRET, world);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        if (target.isAlive()) {
            ItemStack weapon = this.weapon.getStackInSlot(0);
            if (!weapon.isEmpty()) {
                for (ItemStack arrows : ammo.getItems()) {
                    if (arrows.getItem() instanceof ArrowItem) {
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
    protected List<ItemHandler> getContainedItems() {
        return Arrays.asList(weapon, ammo);
    }

    @Override
    public boolean isArmed() {
        return !ammo.isEmpty() && !weapon.isEmpty();
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
        goalSelector.addGoal(5, new RangedAttackGoal(this, 0, KTurrets.ARROW_TURRET_RATE.get(), (float) getRange()));
        targetSelector.addGoal(5, new AttackTargetGoal(this));
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlotType p_184582_1_) {
        return weapon.getStackInSlot(0);
    }
}
