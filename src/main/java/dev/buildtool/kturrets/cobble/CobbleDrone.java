package dev.buildtool.kturrets.cobble;

import dev.buildtool.kturrets.Drone;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.registers.KEntities;
import dev.buildtool.kturrets.registers.Sounds;
import dev.buildtool.kturrets.tasks.AttackTargetGoal;
import dev.buildtool.satako.Functions;
import dev.buildtool.satako.ItemHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class CobbleDrone extends Drone {

    protected ItemHandler stone = new ItemHandler(18) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (KTurrets.USE_CUSTOM_COBBLE_TURRET_AMMO.get()) {
                Item customAmmo = ForgeRegistries.ITEMS.getValue(new ResourceLocation(KTurrets.COBBLE_TURRET_AMMO.get()));
                return stack.is(customAmmo);
            } else
                return Functions.isItemIn(stack.getItem(), ItemTags.STONE_TOOL_MATERIALS);
        }
    };

    public CobbleDrone(Level world) {
        super(KEntities.COBBLE_DRONE.get(), world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(5, new RangedAttackGoal(this, 1, KTurrets.COBBLE_TURRET_RATE.get(), (float) getRange()));
        targetSelector.addGoal(5, new AttackTargetGoal(this));
    }

    @Override
    protected List<ItemHandler> getContainedItems() {
        return Collections.singletonList(stone);
    }

    @Override
    public boolean isArmed() {
        return !stone.isEmpty();
    }

    @Override
    public void performRangedAttack(LivingEntity target, float p_33318_) {
        if (target.isAlive()) {
            for (ItemStack cobblestoneItem : stone.getItems()) {
                if (!cobblestoneItem.isEmpty()) {
                    double xa = target.getX() - getX();
                    double ya = target.getEyeY() - getEyeY();
                    double za = target.getZ() - getZ();
                    Cobblestone cobblestone = new Cobblestone(this, xa, ya, za, level());
                    cobblestone.setDamage(KTurrets.COBBLE_TURRET_DAMAGE.get());
                    level().addFreshEntity(cobblestone);
                    level().playSound(null, blockPosition(), Sounds.COBBLE_SHOT.get(), SoundSource.NEUTRAL, 1, 1f);
                    cobblestoneItem.shrink(1);
                    break;
                }
            }
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        FriendlyByteBuf friendlyByteBuf = Functions.emptyBuffer();
        friendlyByteBuf.writeInt(getId());
        return new CobbleDroneContainer(p_39954_, p_39955_, friendlyByteBuf);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.put("Ammo", stone.serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        stone.deserializeNBT(compoundNBT.getCompound("Ammo"));
    }
}
