package dev.buildtool.kturrets.cobble;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.registers.Sounds;
import dev.buildtool.kturrets.registers.TEntities;
import dev.buildtool.satako.Functions;
import dev.buildtool.satako.ItemHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
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
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CobbleTurret extends Turret {
    protected ItemHandler cobblestone = new ItemHandler(27) {
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (ForgeRegistries.ITEMS.tags().getTag(ItemTags.STONE_TOOL_MATERIALS).contains(stack.getItem()))
                return super.insertItem(slot, stack, simulate);
            return stack;
        }
    };

    public CobbleTurret(Level world) {
        super(TEntities.COBBLE_TURRET.get(), world);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(5, new RangedAttackGoal(this, 0, KTurrets.COBBLE_TURRET_RATE.get(), (float) getRange()));
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
                return !cobblestone.isEmpty() && super.canUse();
            }
        });
    }

    @Override
    protected List<ItemHandler> getContainedItems() {
        return Collections.singletonList(cobblestone);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float p_82196_2_) {
        if (target.isAlive()) {
            for (ItemStack cobblestoneItem : cobblestone.getItems()) {
                if (!cobblestoneItem.isEmpty()) {
                    double xa = target.getX() - getX();
                    double ya = target.getEyeY() - getEyeY();
                    double za = target.getZ() - getZ();
                    Cobblestone cobblestone = new Cobblestone(this, xa, ya, za, level);
                    cobblestone.setDamage(KTurrets.COBBLE_TURRET_DAMAGE.get());
                    level.addFreshEntity(cobblestone);
                    level.playSound(null, blockPosition(), Sounds.COBBLE_SHOT.get(), SoundSource.NEUTRAL, 1, 1f);
                    cobblestoneItem.shrink(1);
                    break;
                }
            }
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_createMenu_1_, Inventory p_createMenu_2_, Player p_createMenu_3_) {
        FriendlyByteBuf packetBuffer = Functions.emptyBuffer();
        packetBuffer.writeInt(getId());
        return new CobbleTurretContainer(p_createMenu_1_, p_createMenu_2_, packetBuffer);
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
    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.put("Ammo", cobblestone.serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        cobblestone.deserializeNBT(compoundNBT.getCompound("Ammo"));
    }
}
