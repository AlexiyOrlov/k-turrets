package dev.buildtool.kturrets;

import dev.buildtool.kturrets.registers.KBlockEntities;
import dev.buildtool.kturrets.storage.StorageDrone;
import dev.buildtool.satako.BlockEntity2;
import dev.buildtool.satako.Functions;
import dev.buildtool.satako.ItemHandler;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReloaderBlockEntity extends BlockEntity2 implements MenuProvider {
    public ItemHandler ammo = new ItemHandler(108, this) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (slot <= 17)
                return stack.is(KTurrets.ARROW_UNIT_AMMO_TAG);
            if (slot <= 35)
                return stack.is(KTurrets.BULLET_UNIT_AMMO_TAG2) || stack.is(KTurrets.BULLET_UNIT_AMMO_TAG1);
            if (slot <= 53)
                return stack.is(KTurrets.BRICK_UNIT_AMMO_TAG1) || stack.is(KTurrets.BRICK_UNIT_AMMO_TAG2);
            if (slot <= 71)
                return stack.is(KTurrets.FIREBALL_UNIT_AMMO);
            if (slot <= 89)
                return stack.is(KTurrets.COBBLE_UNIT_AMMO_TAG);
            if (slot <= 107)
                return stack.is(KTurrets.GAUSS_UNIT_AMMO_TAG);
            return false;
        }
    };

    public ReloaderBlockEntity(BlockPos position, BlockState blockState) {
        super(KBlockEntities.RELOADER.get(), position, blockState);
    }

    static <T extends BlockEntity> void work(Level level, BlockPos pos, BlockState blockState, T t) {
        ReloaderBlockEntity reloaderBlockEntity = (ReloaderBlockEntity) t;
        List<Drone> drones = level.getEntitiesOfClass(Drone.class, new AABB(pos).inflate(6));
        drones.forEach(drone -> {
            if (!(drone instanceof StorageDrone)) {
                IItemHandler itemHandler = drone.getContainedItems().get(0);
                loop:
                for (int i = 0; i < reloaderBlockEntity.ammo.getSlots(); i++) {
                    ItemStack ammo = reloaderBlockEntity.ammo.extractItem(i, 64, true);
                    if (!ammo.isEmpty()) {
                        for (int j = 0; j < itemHandler.getSlots(); j++) {
                            if (itemHandler.isItemValid(j, ammo)) {
                                ItemStack out = ItemHandlerHelper.insertItemStacked(itemHandler, ammo.copy(), false);
                                ammo.setCount(out.getCount());
                                if (ammo.isEmpty()) {
                                    reloaderBlockEntity.ammo.extractItem(i, 64, false);
                                }
                                break loop;
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void saveAdditional(CompoundTag p_187471_) {
        super.saveAdditional(p_187471_);
        p_187471_.put("Ammo", ammo.serializeNBT());
    }

    @Override
    public void load(CompoundTag p_155245_) {
        super.load(p_155245_);
        CompoundTag c = p_155245_.getCompound("Ammo");
        ammo.deserializeNBT(c);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("k_turrets.reloader");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        FriendlyByteBuf friendlyByteBuf = new FriendlyByteBuf(Unpooled.buffer());
        friendlyByteBuf.writeBlockPos(getBlockPos());
        return new ReloaderMenu(i, inventory, friendlyByteBuf);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER)
            return LazyOptional.of(() -> ammo).cast();
        return super.getCapability(cap, side);
    }
}
