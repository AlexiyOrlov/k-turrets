package dev.buildtool.kturrets.fireball;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.KEntities;
import dev.buildtool.satako.ItemHandler;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class FireballTurret extends Turret {
    protected ItemHandler ammo = new ItemHandler(27) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(KTurrets.FIREBALL_TURRET_AMMO.get()));
            return stack.getItem() == item;
        }
    };

    public FireballTurret(World world) {
        super(KEntities.FIRE_CHARGE_TURRET, world);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(5, new RangedAttackGoal(this, 0, KTurrets.FIREBALL_TURRET_RATE.get(), (float) getRange()));
        targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, LivingEntity.class, 0, true, true,
                livingEntity -> {
                    if (livingEntity instanceof PlayerEntity) {
                        PlayerEntity player = (PlayerEntity) livingEntity;
                        if (isProtectingFromPlayers())
                            return alienPlayers.test(player);
                        else return false;
                    }
                    if (livingEntity instanceof LivingEntity) {
                        LivingEntity entity = (LivingEntity) livingEntity;
                        return !entity.fireImmune() && decodeTargets(getTargets()).contains(entity.getType());
                    }
                    return false;
                }) {
            @Override
            public boolean canUse() {
                return !ammo.isEmpty() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return isArmed() && super.canContinueToUse();
            }
        });
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distFactor) {
        if (target.isAlive()) {
            for (ItemStack ammoItem : ammo.getItems()) {
                ammoItem.shrink(1);
                double d0 = target.getX() - this.getX();
                double d1 = target.getEyeY() - getEyeY();
                double d2 = target.getZ() - this.getZ();
                SmallFireball smallFireball = new SmallFireball(this, d0, d1, d2);
                smallFireball.setPos(getX(), getEyeY(), getZ());
                level.addFreshEntity(smallFireball);
                level.playSound(null, blockPosition(), SoundEvents.FIRECHARGE_USE, SoundCategory.NEUTRAL, 1, 1);
                break;
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeInt(getId());
        return new FireballTurretContainer(p_createMenu_1_, p_createMenu_2_, packetBuffer);
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

    @Override
    protected List<ItemHandler> getContainedItems() {
        return Collections.singletonList(ammo);
    }

    @Override
    public boolean isArmed() {
        return !ammo.isEmpty();
    }
}
