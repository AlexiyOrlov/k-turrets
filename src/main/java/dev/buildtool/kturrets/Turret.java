package dev.buildtool.kturrets;

import dev.buildtool.satako.ItemHandler;
import dev.buildtool.satako.Ownable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Team;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Extends Mob entity because of goals
 */
public abstract class Turret extends Mob implements RangedAttackMob, MenuProvider, Ownable, Container {
    private static final EntityDataAccessor<CompoundTag> TARGETS = SynchedEntityData.defineId(Turret.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<Optional<UUID>> OWNER = SynchedEntityData.defineId(Turret.class, EntityDataSerializers.OPTIONAL_UUID);
    protected static final EntityDataAccessor<Boolean> MOVEABLE = SynchedEntityData.defineId(Turret.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> PROTECTION_FROM_PLAYERS = SynchedEntityData.defineId(Turret.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<String> TEAM = SynchedEntityData.defineId(Turret.class, EntityDataSerializers.STRING);

    /**
     * Players that are not allied to the owner
     */
    protected Predicate<LivingEntity> alienPlayers = livingEntity -> {
        if (getOwner().isPresent()) {
            return livingEntity instanceof Player && !livingEntity.getUUID().equals(getOwner().get()) && !isAlliedTo(livingEntity);
        }
        return false;
    };

    public Turret(EntityType<? extends Mob> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createDefaultAttributes() {
        return createLivingAttributes().add(Attributes.FLYING_SPEED, 0.2).add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.FOLLOW_RANGE, 32).add(Attributes.MOVEMENT_SPEED, 0).add(Attributes.MAX_HEALTH, 60).add(Attributes.ATTACK_DAMAGE, 4).add(Attributes.ARMOR, 3);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        CompoundTag compoundNBT = new CompoundTag();
        List<EntityType<?>> targets = ForgeRegistries.ENTITIES.getValues().stream().filter(entityType1 -> !entityType1.getCategory().isFriendly()).collect(Collectors.toList());
        for (int i = 0; i < targets.size(); i++) {
            compoundNBT.putString("Target#" + i, targets.get(i).getRegistryName().toString());
        }
        compoundNBT.putInt("Count", targets.size());
        entityData.define(TARGETS, compoundNBT);
        entityData.define(OWNER, Optional.empty());
        entityData.define(MOVEABLE, false);
        entityData.define(PROTECTION_FROM_PLAYERS, false);
        entityData.define(TEAM, "");
    }

    public String getManualTeam() {
        return entityData.get(TEAM);
    }

    public void setManualTeam(String team) {
        entityData.set(TEAM, team);
    }

    public void setTargets(CompoundTag compoundNBT) {
        entityData.set(TARGETS, compoundNBT);
    }

    public CompoundTag getTargets() {
        return entityData.get(TARGETS);
    }

    public Optional<UUID> getOwner() {
        return entityData.get(OWNER);
    }

    public void setOwner(UUID owner) {
        entityData.set(OWNER, Optional.of(owner));
    }

    public void setMoveable(boolean moveable) {
        entityData.set(MOVEABLE, moveable);
    }

    public boolean isMoveable() {
        return entityData.get(MOVEABLE);
    }

    public void setProtectionFromPlayers(boolean protect) {
        entityData.set(PROTECTION_FROM_PLAYERS, protect);
    }

    public boolean isProtectingFromPlayers() {
        return entityData.get(PROTECTION_FROM_PLAYERS);
    }


    @Override
    protected abstract void registerGoals();

    @Override
    public boolean attackable() {
        return false;
    }

    /**
     * By player
     */
    @Override
    public boolean isAttackable() {
        return true;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot p_184582_1_) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot p_184201_1_, ItemStack p_184201_2_) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    protected double getRange() {
        return getAttributeValue(Attributes.FOLLOW_RANGE);
    }

    protected double getDamage() {
        return getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    protected InteractionResult mobInteract(Player playerEntity, InteractionHand interactionHand) {
        ItemStack itemInHand = playerEntity.getItemInHand(interactionHand);
        if (getHealth() < getMaxHealth() && itemInHand.getTags().anyMatch(itemTagKey -> itemTagKey.location().equals(KTurrets.STEEL_INGOT))) {
            heal(getMaxHealth() / 6);
            itemInHand.shrink(1);
            return InteractionResult.SUCCESS;
        }
        if (canUse(playerEntity)) {
            if (level.isClientSide) {
                openTargetScreen();
            }
            return InteractionResult.SUCCESS;
        } else if (level.isClientSide)
            playerEntity.sendMessage(new TranslatableComponent("k_turrets.turret.not.yours"), Util.NIL_UUID);
        return InteractionResult.PASS;
    }

    protected boolean canUse(Player playerEntity) {
        return !getOwner().isPresent() || getOwner().get().equals(playerEntity.getUUID());
    }

    @OnlyIn(Dist.CLIENT)
    void openTargetScreen() {
        Minecraft.getInstance().setScreen(new TurretOptionsScreen(this));
    }

    //don't forget to save the inventory
    @Override
    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.put("Targets", getTargets());
        getOwner().ifPresent(uuid1 -> compoundNBT.putUUID("Owner", uuid1));
        compoundNBT.putBoolean("Mobile", isMoveable());
        compoundNBT.putBoolean("Player protection", isProtectingFromPlayers());
        compoundNBT.putString("Team", getManualTeam());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        setTargets(compoundNBT.getCompound("Targets"));
        if (compoundNBT.contains("Owner")) {
            UUID uuid = compoundNBT.getUUID("Owner");
            if (!uuid.equals(Util.NIL_UUID))
                setOwner(uuid);
        }
        setMoveable(compoundNBT.getBoolean("Mobile"));
        setProtectionFromPlayers(compoundNBT.getBoolean("Player protection"));
        setManualTeam(compoundNBT.getString("Team"));
    }

    public List<EntityType<?>> decodeTargets(CompoundTag compoundNBT) {
        int count = compoundNBT.getInt("Count");
        List<EntityType<?>> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String next = compoundNBT.getString("Target#" + i);
            list.add(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(next)));
        }
        return list;
    }

    public CompoundTag encodeTargets(List<EntityType<?>> list) {
        CompoundTag compoundNBT = new CompoundTag();
        for (int i = 0; i < list.size(); i++) {
            EntityType<?> entityType = list.get(i);
            compoundNBT.putString("Target#" + i, entityType.getRegistryName().toString());
        }
        compoundNBT.putInt("Count", list.size());
        return compoundNBT;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        getContainedItems().forEach(itemHandler -> Containers.dropContents(level, blockPosition(), itemHandler.getItems()));
    }

    protected abstract List<ItemHandler> getContainedItems();

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.SHIELD_BLOCK;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        MobEffect effect = effectInstance.getEffect();
        if (effect == MobEffects.POISON || effect == MobEffects.HEAL || effect == MobEffects.HEALTH_BOOST || effect == MobEffects.REGENERATION || effect == MobEffects.WITHER || effect == MobEffects.HUNGER)
            return false;
        return super.canBeAffected(effectInstance);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !isMoveable();
    }

    @Override
    public boolean isPushable() {
        return isMoveable();
    }

    @Override
    public void knockback(double p_147241_, double p_147242_, double p_147243_) {
        if (isMoveable())
            super.knockback(p_147241_, p_147242_, p_147243_);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.DRAGON_FIREBALL_EXPLODE;
    }

    @Override
    public boolean removeWhenFarAway(double p_213397_1_) {
        return false;
    }

    /**
     * @return appropriate spawn egg
     */
    public Item getSpawnItem() {
        return ForgeSpawnEggItem.fromEntityType(getType());
    }

    @Override
    public boolean isAlliedTo(Entity target) {
        return super.isAlliedTo(target) || (getOwner().isPresent() && (target.getUUID().equals(getOwner().get())) || target instanceof Turret turret && turret.getOwner().isPresent() && turret.getOwner().equals(getOwner())) || target instanceof Ownable ownable && ownable.isAlly(this);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource p_20122_) {
        Entity source = p_20122_.getEntity();
        if (source instanceof Player player && getOwner().isPresent() && player.getUUID().equals(getOwner().get()))
            return true;
        return super.isInvulnerableTo(p_20122_);
    }

    /**
     * @return ready to shoot
     */
    public abstract boolean isArmed();

    @Override
    public UUID getOwnerUUID() {
        return getOwner().isPresent() ? getOwner().get() : null;
    }

    //assume below that the ammo items is contained in the first item handler
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction direction) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> getContainedItems().get(0)).cast();
        }
        return super.getCapability(cap, direction);
    }

    @Override
    public int getContainerSize() {
        return getContainedItems().get(0).getSlots();
    }

    @Override
    public boolean isEmpty() {
        return getContainedItems().get(0).isEmpty();
    }

    @Override
    public ItemStack getItem(int p_18941_) {
        return getContainedItems().get(0).getStackInSlot(p_18941_);
    }

    @Override
    public ItemStack removeItem(int p_18942_, int p_18943_) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_18951_) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int p_18944_, ItemStack p_18945_) {
        getContainedItems().get(0).setStackInSlot(p_18944_, p_18945_);
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player p_18946_) {
        return true;
    }

    @Override
    public void clearContent() {
        getContainedItems().get(0).getItems().clear();
    }

    @Override
    public boolean canPlaceItem(int p_18952_, ItemStack p_18953_) {
        return getContainedItems().get(0).isItemValid(p_18952_, p_18953_);
    }

    /**
     * Will belong to owner team
     */
    @Nullable
    @Override
    public Team getTeam() {
        if (getOwner().isPresent()) {
            Player owner = level.getPlayerByUUID(getOwner().get());
            if (owner != null) {
                return owner.getTeam();
            } else {
                return getManualTeam().isEmpty() ? null : level.getScoreboard().getPlayerTeam(getManualTeam());
            }
        }
        return super.getTeam();
    }
}
