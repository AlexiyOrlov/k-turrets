package dev.buildtool.kturrets.arrow;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.TEntities;
import dev.buildtool.satako.Functions;
import dev.buildtool.satako.ItemHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

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

    public ArrowTurret(Level world) {
        super(TEntities.ARROW_TURRET.get(), world);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        if (target.isAlive()) {
            ItemStack weapon = this.weapon.getStackInSlot(0);
            if (!weapon.isEmpty()) {
                for (ItemStack arrows : ammo.getItems()) {
                    if (arrows.getItem() instanceof ArrowItem) {
                        AbstractArrow arrowEntity = ProjectileUtil.getMobArrow(this, arrows, distanceFactor);
                        double d0 = target.getX() - this.getX();
                        double d1 = target.getEyeY() - getEyeY();
                        double d2 = target.getZ() - this.getZ();
                        arrowEntity.shoot(d0, d1, d2, 1.8F, 0);
                        double damage = KTurrets.ARROW_TURRET_DAMAGE.get() / 2d;

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
                        arrow2.setNoGravity(true);
                        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
                        this.level.addFreshEntity(arrow2);
                        arrows.shrink(1);
                        weapon.hurtAndBreak(1, this, turret -> turret.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        weapon.deserializeNBT(compoundNBT.getCompound("Weapon"));
        ammo.deserializeNBT(compoundNBT.getCompound("Ammo"));
    }

    @Override
    protected List<ItemHandler> getContainedItems() {
        return Arrays.asList(weapon, ammo);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.put("Ammo", ammo.serializeNBT());
        compoundNBT.put("Weapon", weapon.serializeNBT());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_createMenu_1_, Inventory p_createMenu_2_, Player p_createMenu_3_) {
        FriendlyByteBuf packetBuffer = Functions.emptyBuffer();
        packetBuffer.writeInt(getId());
        return new ArrowTurretContainer(p_createMenu_1_, p_createMenu_2_, packetBuffer);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(5, new RangedAttackGoal(this, 0, KTurrets.ARROW_TURRET_RATE.get(), (float) getRange()));
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
                return !weapon.getStackInSlot(0).isEmpty() && !ammo.isEmpty() && super.canUse();
            }
        });
    }

    @Override
    protected InteractionResult mobInteract(Player playerEntity, InteractionHand p_230254_2_) {
        if (canUse(playerEntity) && playerEntity.isCrouching()) {
            if (playerEntity instanceof ServerPlayer) {
                NetworkHooks.openGui((ServerPlayer) playerEntity, this, packetBuffer -> packetBuffer.writeInt(getId()));
            }
            return InteractionResult.SUCCESS;
        } else
            return super.mobInteract(playerEntity, p_230254_2_);
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot p_184582_1_) {
        return weapon.getStackInSlot(0);
    }
}
