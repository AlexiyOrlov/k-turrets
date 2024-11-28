package dev.buildtool.kturrets.storage;

import dev.buildtool.kturrets.Drone;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.packets.PickupParticles;
import dev.buildtool.kturrets.registers.KBlocks;
import dev.buildtool.kturrets.registers.KEntities;
import dev.buildtool.kturrets.registers.KItems;
import dev.buildtool.satako.Functions;
import dev.buildtool.satako.ItemHandler;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StorageDrone extends Drone {
    private static final EntityDataAccessor<Boolean> MAGNET_ACTIVE= SynchedEntityData.defineId(StorageDrone.class, EntityDataSerializers.BOOLEAN);
    public ItemHandler itemHandler = new ItemHandler(27){
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return !stack.is(KItems.STORAGE_DRONE.get());
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

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(MAGNET_ACTIVE,true);
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
        compoundNBT.putBoolean("Magnet on",isMagnetActive());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        itemHandler.deserializeNBT(compoundNBT.getCompound("Items"));
        setMagnetActive(compoundNBT.getBoolean("Magnet on"));
    }

    @Override
    public void tick() {
        super.tick();
        if(!level().isClientSide) {
            ItemStack magnet = upgrades.getStackInSlot(2);
            if (magnet.is(KItems.MAGNET_UPGRADE.get()) && isMagnetActive()) {
                List<ItemEntity> itemEntities = level().getEntitiesOfClass(ItemEntity.class, getBoundingBox().inflate(32));
                itemEntities.forEach(itemEntity -> {
                    ItemStack entityItem = itemEntity.getItem();
                    if (!entityItem.is(KItems.STORAGE_DRONE.get())) {
                        magnet.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler ->{
                            if(magnet.getOrCreateTag().getBoolean(KTurrets.FILTER))
                            {
                                for (int i = 0; i < iItemHandler.getSlots(); i++) {
                                    ItemStack item=iItemHandler.getStackInSlot(i);
                                    if(Functions.areItemTypesEqual(item,entityItem))
                                    {
                                        ItemStack tryInsert = ItemHandlerHelper.insertItemStacked(itemHandler, entityItem, true);
                                        if (tryInsert.isEmpty()) {
                                            itemEntity.setPickUpDelay(Functions.secondsToTicks(5));
                                            itemEntity.setDeltaMovement(getPosition(1).subtract(itemEntity.position()).normalize().multiply(new Vec3(0.5, 0.5, 0.5)));
                                            KTurrets.channel.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(getX(),getY(),getZ(),22,level().dimension())), new PickupParticles(itemEntity.getX(),itemEntity.getY(),itemEntity.getZ()));
                                            if (distanceTo(itemEntity) < 1) {
                                                if (Functions.tryInsertItem(itemHandler, entityItem))
                                                    itemEntity.discard();
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                            else {
                                boolean isBlacklisted=false;
                                for (int i = 0; i < iItemHandler.getSlots(); i++) {
                                    ItemStack itemStack=iItemHandler.getStackInSlot(i);
                                    if(Functions.areItemTypesEqual(itemStack,entityItem))
                                    {
                                        isBlacklisted=true;
                                        break;
                                    }
                                }
                                if(!isBlacklisted)
                                {
                                    ItemStack tryInsert = ItemHandlerHelper.insertItemStacked(itemHandler, entityItem, true);
                                    if (tryInsert.isEmpty()) {
                                        itemEntity.setPickUpDelay(Functions.secondsToTicks(5));
                                        itemEntity.setDeltaMovement(getPosition(1).subtract(itemEntity.position()).normalize().multiply(0.25,0.25,0.25));
                                        KTurrets.channel.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(getX(),getY(),getZ(),22,level().dimension())), new PickupParticles(itemEntity.getX(),itemEntity.getY(),itemEntity.getZ()));
                                        if (distanceTo(itemEntity) < 1) {
                                            if (Functions.tryInsertItem(itemHandler, entityItem))
                                                itemEntity.discard();
                                        }
                                    }
                                }
                            }
                        });
                    }
                });
            }
        }
    }

    public void setMagnetActive(boolean b)
    {
        entityData.set(MAGNET_ACTIVE,b);
    }

    public boolean isMagnetActive()
    {
        return entityData.get(MAGNET_ACTIVE);
    }
}
