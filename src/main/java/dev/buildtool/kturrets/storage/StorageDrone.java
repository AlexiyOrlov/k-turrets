package dev.buildtool.kturrets.storage;

import dev.buildtool.kturrets.Drone;
import dev.buildtool.kturrets.registers.KBlocks;
import dev.buildtool.kturrets.registers.KEntities;
import dev.buildtool.kturrets.registers.KItems;
import dev.buildtool.satako.ItemHandler;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StorageDrone extends Drone {
    private BlockPos previousPosition=BlockPos.ZERO;
    public ItemHandler itemHandler = new ItemHandler(27){
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return !stack.is(KItems.STORAGE_DRONE.get());
        }
    };
    public ItemHandler upgrades=new ItemHandler(2)
    {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if(!getStackInSlot(0).is(KItems.LIGHT_UPGRADE.get()) && !upgrades.getStackInSlot(1).is(KItems.LIGHT_UPGRADE.get()))
                return stack.is(KItems.LIGHT_UPGRADE.get());
            return false;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    public StorageDrone(Level world) {
        super(KEntities.STORAGE_DRONE.get(), world);
    }

    @Override
    protected List<ItemHandler> getContainedItems() {
        return List.of(itemHandler,upgrades);
    }

    @Override
    public boolean isArmed() {
        return false;
    }

    @Override
    public void performRangedAttack(LivingEntity p_33317_, float p_33318_) {

    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());
        byteBuf.writeInt(getId());
        return new StorageDroneMenu(p_39954_, p_39955_, byteBuf);
    }

    @Override
    protected InteractionResult mobInteract(Player playerEntity, InteractionHand interactionHand) {
        return super.mobInteract(playerEntity, interactionHand);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.put("Items", itemHandler.serializeNBT());
        compoundNBT.putLong("Previous light position",previousPosition.asLong());
        compoundNBT.put("Upgrades",upgrades.serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        itemHandler.deserializeNBT(compoundNBT.getCompound("Items"));
        previousPosition=BlockPos.of(compoundNBT.getLong("Previous light position"));
        upgrades.deserializeNBT(compoundNBT.getCompound("Upgrades"));
    }

    @Override
    public void tick() {
        super.tick();
        if(upgrades.getStackInSlot(0).is(KItems.LIGHT_UPGRADE.get()) || upgrades.getStackInSlot(1).is(KItems.LIGHT_UPGRADE.get())) {
            BlockPos currentPos = getOnPos();
            if (level().getBlockState(previousPosition).is(KBlocks.LIGHT_BLOCK.get()))
                level().removeBlock(previousPosition, false);
            if (level().isEmptyBlock(currentPos)) {
                level().setBlock(currentPos, KBlocks.LIGHT_BLOCK.get().defaultBlockState(), 2);
            }
            previousPosition = currentPos;

        }
        else if(level().getBlockState(previousPosition).is(KBlocks.LIGHT_BLOCK.get()))
            level().removeBlock(previousPosition,false);
    }
}
