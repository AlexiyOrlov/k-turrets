package dev.buildtool.kturrets;

import dev.buildtool.kturrets.registers.KBlocks;
import dev.buildtool.kturrets.registers.KItems;
import dev.buildtool.kturrets.registers.Sounds;
import dev.buildtool.kturrets.storage.StorageDrone;
import dev.buildtool.kturrets.tasks.*;
import dev.buildtool.satako.Functions;
import dev.buildtool.satako.ItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Drone must carry less ammo than a turret; has lower range, health and armor
 */
public abstract class Drone extends Turret {
    private static final EntityDataAccessor<BlockPos> GUARD_POSITION = SynchedEntityData.defineId(Drone.class, EntityDataSerializers.BLOCK_POS);

    private static final EntityDataAccessor<Byte> BEHAVIOR=SynchedEntityData.defineId(Drone.class,EntityDataSerializers.BYTE);
    private BlockPos previousPosition=BlockPos.ZERO;

    public ItemHandler upgrades=new ItemHandler(KTurrets.droneUpgradeCount)
    {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if(stack.is(KItems.RECALL_UPGRADE.get()))
                return !Functions.contains(KItems.RECALL_UPGRADE.get(),this);
            else if(stack.is(KItems.MAGNET_UPGRADE.get()))
                return Drone.this instanceof StorageDrone && !Functions.contains(KItems.MAGNET_UPGRADE.get(), this);
            else if(stack.is(KItems.LIGHT_UPGRADE.get()))
                return !Functions.contains(KItems.LIGHT_UPGRADE.get(), this);
            else if (stack.is(KItems.EXP_LINK.get())) {
                return (!(Drone.this instanceof StorageDrone)) && !Functions.contains(KItems.EXP_LINK.get(), this);
            } else if (stack.is(KItems.FIRE_SHIELD.get())) {
                return !Functions.contains(KItems.FIRE_SHIELD.get(), this);
            } else if (stack.is(KItems.LOOTING_LINK.get())) {
                return !Functions.contains(KItems.LOOTING_LINK.get(), this);
            }
            return false;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    public Drone(EntityType<? extends PathfinderMob> entityType, Level world) {
        super(entityType, world);
        moveControl = new DroneMovementControl(this, 20, true);
        setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1);
        setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1);
    }

    public enum Behavior{
        FOLLOW_AND_ATTACK,
        FOLLOW_ONLY,
        GUARD,
        STAY
    }

    public boolean causeFallDamage(float p_147105_, float p_147106_, DamageSource p_147107_) {
        return false;
    }

    protected void checkFallDamage(double p_20809_, boolean p_20810_, BlockState p_20811_, BlockPos p_20812_) {

    }

    public void travel(Vec3 vector) {
        float flyingSpeed = getSpeed();
        if (this.isInWater()) {
            this.moveRelative(flyingSpeed / 2, vector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
        } else if (this.isInLava()) {
            this.moveRelative(flyingSpeed / 2, vector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
        } else {
            BlockPos ground = new BlockPos((int) this.getX(), (int) this.getY() - 1, (int) this.getZ());
            float f = 0.91F;
            if (this.onGround()) {
                f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
            }

            float f1 = (float) (getAttributeValue(Attributes.MOVEMENT_SPEED) / (f * f * f));
            f = 0.91F;
            if (this.onGround()) {
                f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
            }

            this.moveRelative(this.onGround() ? 0.1F * f1 : flyingSpeed, vector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(f));
        }

        this.calculateEntityAnimation(false);
    }

    public boolean onClimbable() {
        return false;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.set(MOVEABLE, true);
        entityData.define(GUARD_POSITION, BlockPos.ZERO);
        entityData.set(REFILL_INVENTORY, false);
        entityData.define(BEHAVIOR,(byte)0);
    }

    public void setBehavior(Behavior behavior)
    {
        entityData.set(BEHAVIOR,(byte)behavior.ordinal());
    }

    public Behavior getBehavior()
    {
        return Behavior.values()[entityData.get(BEHAVIOR)];
    }

    public BlockPos getGuardPosition() {
        return entityData.get(GUARD_POSITION);
    }

    public void setGuardPosition(BlockPos pos) {
        entityData.set(GUARD_POSITION, pos);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putLong("Guard position", getGuardPosition().asLong());
        compoundNBT.putByte("Behavior",(byte)getBehavior().ordinal());
        compoundNBT.put("Upgrades",upgrades.serializeNBT());
        compoundNBT.putLong("Previous light position",previousPosition.asLong());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        setGuardPosition(BlockPos.of(compoundNBT.getLong("Guard position")));
        setBehavior(Behavior.values()[compoundNBT.getByte("Behavior")]);
        upgrades.deserializeNBT(compoundNBT.getCompound("Upgrades"));
        upgrades.setSize(KTurrets.droneUpgradeCount);
        previousPosition=BlockPos.of(compoundNBT.getLong("Previous light position"));
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(4, new FollowOwnerGoal(this));
        goalSelector.addGoal(5, new MoveOutOfLava(this));
        goalSelector.addGoal(6, new AvoidAggressors(this));
        goalSelector.addGoal(7, new StrafeAroundTarget(this));
        goalSelector.addGoal(8, new GuardArea(this));
    }

    @Override
    protected PathNavigation createNavigation(Level p_21480_) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_21480_);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    @Override
    protected float getStandingEyeHeight(Pose p_21131_, EntityDimensions p_21132_) {
        return getBbHeight() * 0.4f;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (FMLEnvironment.dist.isClient()) {
            return KTurrets.ENABLE_DRONE_SOUND.get() ? Sounds.DRONE_FLY.get() : null;
        }
        return null;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 330;
    }

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {

    }

    @Override
    protected float getHealthRecovered() {
        return getMaxHealth() / 4;
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

    @Override
    protected abstract List<ItemHandler> getContainedItems();

    @Override
    public void tick() {
        super.tick();
        if(!level().isClientSide)
        {
            if(level().getGameTime()%40==0 && !Functions.findItem(KItems.RECALL_UPGRADE.get(), upgrades).isEmpty()) {
                getOwner().ifPresent(uuid1 -> {
                    Player player = level().getPlayerByUUID(uuid1);
                    if (player != null) {
                        if ((getBehavior() == Behavior.FOLLOW_AND_ATTACK || getBehavior()==Behavior.FOLLOW_ONLY) && distanceTo(player) > 128) {
                            teleportTo(player.getX(), player.getY() + 2, player.getZ());
                        }
                    }
                });
            }

            if (!Functions.findItem(KItems.LIGHT_UPGRADE.get(), upgrades).isEmpty()) {
                BlockPos currentPos = getOnPos();
                if (level().isEmptyBlock(currentPos)) {
                    if (level().getBlockState(previousPosition).is(KBlocks.LIGHT_BLOCK.get()))
                        level().removeBlock(previousPosition, false);
                    level().setBlock(currentPos, KBlocks.LIGHT_BLOCK.get().defaultBlockState(), 2);
                    previousPosition = currentPos;
                }

            } else if (level().getBlockState(previousPosition).is(KBlocks.LIGHT_BLOCK.get()))
                level().removeBlock(previousPosition, false);
        }
    }

    @Override
    public boolean fireImmune() {
        return Functions.contains(KItems.FIRE_SHIELD.get(), upgrades);
    }
}
