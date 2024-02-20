package dev.buildtool.kturrets;

import dev.buildtool.kturrets.tasks.RevengeTask;
import dev.buildtool.satako.ItemHandler;
import dev.buildtool.satako.UniqueList;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.scoreboard.Team;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Extends Mob entity because of goals
 */
public abstract class Turret extends MobEntity implements IRangedAttackMob, INamedContainerProvider {
    private static final DataParameter<CompoundNBT> TARGETS = EntityDataManager.defineId(Turret.class, DataSerializers.COMPOUND_TAG);
    private static final DataParameter<Optional<UUID>> OWNER = EntityDataManager.defineId(Turret.class, DataSerializers.OPTIONAL_UUID);
    protected static final DataParameter<Boolean> MOVEABLE = EntityDataManager.defineId(Turret.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> PROTECTION_FROM_PLAYERS = EntityDataManager.defineId(Turret.class, DataSerializers.BOOLEAN);
    private static final DataParameter<CompoundNBT> IGNORED_PLAYERS = EntityDataManager.defineId(Turret.class, DataSerializers.COMPOUND_TAG);
    private static final DataParameter<String> TEAM = EntityDataManager.defineId(Turret.class, DataSerializers.STRING);
    private static final DataParameter<String> OWNER_NAME = EntityDataManager.defineId(Turret.class, DataSerializers.STRING);
    /**
     * Players that are not allied to the owner
     */
    public Predicate<LivingEntity> alienPlayers = livingEntity -> {
        if (getOwner().isPresent()) {
            if (livingEntity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) livingEntity;
                CompoundNBT compoundTag = entityData.get(IGNORED_PLAYERS);
                for (String key : compoundTag.getAllKeys()) {
                    if (compoundTag.getString(key).equals(player.getName().getString())) {
                        return false;
                    }
                }
                return !player.getUUID().equals(getOwner().get()) && !isAlliedTo(player);
            }
        }
        return false;
    };

    public Turret(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    public static AttributeModifierMap.MutableAttribute createDefaultAttributes() {
        return createLivingAttributes().add(Attributes.FOLLOW_RANGE, 32).add(Attributes.MOVEMENT_SPEED, 0).add(Attributes.MAX_HEALTH, 60).add(Attributes.ATTACK_DAMAGE, 4).add(Attributes.ARMOR, 3).add(Attributes.FLYING_SPEED, 0.27);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        CompoundNBT compoundNBT = new CompoundNBT();
        List<EntityType<?>> targets = ForgeRegistries.ENTITIES.getValues().stream().filter(entityType1 -> !entityType1.getCategory().isFriendly()).collect(Collectors.toList());
        List<String> exceptions = (List<String>) KTurrets.TARGET_EXCEPTIONS.get();
        targets.removeIf(entityType -> exceptions.contains(ForgeRegistries.ENTITIES.getKey(entityType).toString()));
        for (int i = 0; i < targets.size(); i++) {
            compoundNBT.putString("Target#" + i, targets.get(i).getRegistryName().toString());
        }
        compoundNBT.putInt("Count", targets.size());
        entityData.define(TARGETS, compoundNBT);
        entityData.define(OWNER, Optional.empty());
        entityData.define(MOVEABLE, false);
        entityData.define(PROTECTION_FROM_PLAYERS, false);
        entityData.define(IGNORED_PLAYERS, new CompoundNBT());
        entityData.define(TEAM, "");
        entityData.define(OWNER_NAME, "");
    }

    public void setTargets(CompoundNBT compoundNBT) {
        entityData.set(TARGETS, compoundNBT);
    }

    public CompoundNBT getTargets() {
        return entityData.get(TARGETS);
    }

    public Optional<UUID> getOwner() {
        return entityData.get(OWNER);
    }

    public void setOwner(UUID owner) {
        entityData.set(OWNER, Optional.of(owner));
    }

    public void setMovable(boolean moveable) {
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
    protected void registerGoals() {
        targetSelector.addGoal(1, new RevengeTask(this));
    }

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
    public ItemStack getItemBySlot(EquipmentSlotType p_184582_1_) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlotType p_184201_1_, ItemStack p_184201_2_) {

    }

    @Override
    public HandSide getMainArm() {
        return HandSide.RIGHT;
    }

    protected double getRange() {
        return getAttributeValue(Attributes.FOLLOW_RANGE);
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity playerEntity, Hand interactionHand) {
        ItemStack itemInHand = playerEntity.getItemInHand(interactionHand);
        if (getHealth() < getMaxHealth() && playerEntity.isCrouching() && ItemTags.getAllTags().getTag(KTurrets.TITANIUM_INGOT).contains(itemInHand.getItem())) {
            heal(getHealthRecovered());
            itemInHand.shrink(1);
            return ActionResultType.SUCCESS;
        }

        if (canUse(playerEntity)) {
            if (playerEntity.getTeam() != null) {
                setAutomaticTeam(playerEntity.getTeam().getName());
            } else {
                setAutomaticTeam("");
            }
            if (getOwnerName().isEmpty())
                setOwnerName(playerEntity.getName().getString());
            if (level.isClientSide && playerEntity.isShiftKeyDown()) {
                openTargetScreen();
                return ActionResultType.PASS;
            } else if (!level.isClientSide && !playerEntity.isShiftKeyDown()) {
                NetworkHooks.openGui((ServerPlayerEntity) playerEntity, this, packetBuffer -> packetBuffer.writeInt(getId()));
                return ActionResultType.PASS;
            }
        } else if (level.isClientSide) {
            if (this instanceof Drone) {
                if (getOwnerName().isEmpty())
                    playerEntity.displayClientMessage(new TranslationTextComponent("k_turrets.turret.belongs.to").append(" " + getOwner()), true);
            }
        }
        return ActionResultType.FAIL;
    }

    protected boolean canUse(PlayerEntity playerEntity) {
        return !getOwner().isPresent() || getOwner().get().equals(playerEntity.getUUID());
    }

    @OnlyIn(Dist.CLIENT)
    private void openTargetScreen() {
        Minecraft.getInstance().setScreen(new TurretOptionsScreen(this));
    }

    //don't forget to save the inventory
    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.put("Targets", getTargets());
        getOwner().ifPresent(uuid1 -> compoundNBT.putUUID("Owner", uuid1));
        compoundNBT.putBoolean("Mobile", isMoveable());
        compoundNBT.putBoolean("Player protection", isProtectingFromPlayers());
        compoundNBT.putString("Team", getAutomaticTeam());
        compoundNBT.put("Exceptions", entityData.get(IGNORED_PLAYERS));
        compoundNBT.putString("Owner name", getOwnerName());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        setTargets(compoundNBT.getCompound("Targets"));
        if (compoundNBT.contains("Owner")) {
            UUID uuid = compoundNBT.getUUID("Owner");
            if (!uuid.equals(Util.NIL_UUID))
                setOwner(uuid);
        }
        setMovable(compoundNBT.getBoolean("Mobile"));
        setProtectionFromPlayers(compoundNBT.getBoolean("Player protection"));
        setAutomaticTeam(compoundNBT.getString("Team"));
        entityData.set(IGNORED_PLAYERS, compoundNBT.getCompound("Exceptions"));
        if (compoundNBT.contains("Owner name")) {
            setOwnerName(compoundNBT.getString("Owner name"));
        }
    }

    public static List<EntityType<?>> decodeTargets(CompoundNBT compoundNBT) {
        int count = compoundNBT.getInt("Count");
        List<EntityType<?>> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String next = compoundNBT.getString("Target#" + i);
            list.add(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(next)));
        }
        return list;
    }

    public static CompoundNBT encodeTargets(List<EntityType<?>> list) {
        CompoundNBT compoundNBT = new CompoundNBT();
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
        getContainedItems().forEach(itemHandler -> InventoryHelper.dropContents(level, blockPosition(), itemHandler.getItems()));
    }

    protected abstract List<ItemHandler> getContainedItems();

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.SHIELD_BLOCK;
    }

    @Override
    public boolean canBeAffected(EffectInstance effectInstance) {
        Effect effect = effectInstance.getEffect();
        if (effect == Effects.POISON || effect == Effects.HEAL || effect == Effects.HEALTH_BOOST || effect == Effects.REGENERATION || effect == Effects.WITHER || effect == Effects.HUNGER)
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
    public void knockback(float p_233627_1_, double p_233627_2_, double p_233627_4_) {
        if (isMoveable())
            super.knockback(p_233627_1_, p_233627_2_, p_233627_4_);
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
     * Can't use {@link net.minecraft.item.SpawnEggItem#byId(EntityType)}
     *
     * @return appropriate spawn egg
     */
    public Item getSpawnItem() {
        return ForgeSpawnEggItem.fromEntityType(getType());
    }

    public abstract boolean isArmed();

    @Override
    public boolean isInvulnerableTo(DamageSource p_180431_1_) {
        Entity source = p_180431_1_.getEntity();
        if (source instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) source;
            return getOwner().isPresent() && getOwner().get().equals(player.getUUID());
        }
        return super.isInvulnerableTo(p_180431_1_);
    }

    @Override
    public boolean isAlliedTo(Entity target) {
        if (target instanceof Turret) {
            Turret turret = (Turret) target;
            return turret.getOwner().isPresent() && turret.getOwner().equals(getOwner());
        }
        if (getOwner().isPresent() && getOwner().get().equals(target.getUUID()))
            return true;
        return super.isAlliedTo(target);
    }

    public void setAutomaticTeam(String team) {
        entityData.set(TEAM, team);
    }

    public String getAutomaticTeam() {
        return entityData.get(TEAM);
    }

    @Nullable
    @Override
    public Team getTeam() {
        if (getOwner().isPresent()) {
            PlayerEntity owner = level.getPlayerByUUID(getOwner().get());
            if (owner != null) {
                return owner.getTeam();
            } else return getAutomaticTeam().isEmpty() ? null : level.getScoreboard().getPlayerTeam(getAutomaticTeam());
        }
        return super.getTeam();
    }

    public void addPlayerToExceptions(String name) {
        CompoundNBT compoundTag = entityData.get(IGNORED_PLAYERS);
        for (String key : compoundTag.getAllKeys()) {
            String nickname = compoundTag.getString(key);
            if (nickname.equals(name))
                return;
        }
        compoundTag.putString("IgnoredPlayer#" + compoundTag.size(), name);
    }

    public void removePlayerFromExceptions(String name) {
        CompoundNBT compoundTag = entityData.get(IGNORED_PLAYERS);
        for (String key : compoundTag.getAllKeys()) {
            String nickname = compoundTag.getString(key);
            if (nickname.equals(name)) {
                compoundTag.remove(key);
                break;
            }
        }
    }

    public List<String> getExceptions() {
        List<String> exceptions = new UniqueList<>(1);
        CompoundNBT compoundTag = entityData.get(IGNORED_PLAYERS);
        compoundTag.getAllKeys().forEach(s -> exceptions.add(compoundTag.getString(s)));
        return exceptions;
    }

    protected float getHealthRecovered() {
        return getMaxHealth() / 6;
    }

    public String getOwnerName() {
        return entityData.get(OWNER_NAME);
    }

    public void setOwnerName(String owner) {
        entityData.set(OWNER_NAME, owner);
    }
}
