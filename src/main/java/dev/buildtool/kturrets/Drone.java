package dev.buildtool.kturrets;

import dev.buildtool.kturrets.registers.Sounds;
import dev.buildtool.kturrets.tasks.*;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.Nullable;

/**
 * Drone must carry less ammo than a turret; has lower range, health and armor
 */
public abstract class Drone extends Turret {
    private static final EntityDataAccessor<Boolean> FOLLOWING_OWNER = SynchedEntityData.defineId(Drone.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> GUARDING_AREA = SynchedEntityData.defineId(Drone.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<BlockPos> GUARD_POSITION = SynchedEntityData.defineId(Drone.class, EntityDataSerializers.BLOCK_POS);

    public Drone(EntityType<? extends Mob> entityType, Level world) {
        super(entityType, world);
        moveControl = new DroneMovementControl(this, 20, true);
        setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1);
        setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1);
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
        entityData.define(FOLLOWING_OWNER, true);
        entityData.set(MOVEABLE, true);
        entityData.define(GUARDING_AREA, false);
        entityData.define(GUARD_POSITION, BlockPos.ZERO);
    }

    public boolean isFollowingOwner() {
        return entityData.get(FOLLOWING_OWNER);
    }

    public void followOwner(boolean follow) {
        entityData.set(FOLLOWING_OWNER, follow);
    }

    public boolean isGuardingArea() {
        return entityData.get(GUARDING_AREA);
    }

    public void setGuardArea(boolean guard) {
        entityData.set(GUARDING_AREA, guard);
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
        compoundNBT.putBoolean("Following", isFollowingOwner());
        compoundNBT.putBoolean("Guarding", isGuardingArea());
        compoundNBT.putLong("Guard position", getGuardPosition().asLong());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        followOwner(compoundNBT.getBoolean("Following"));
        setGuardArea(compoundNBT.getBoolean("Guarding"));
        setGuardPosition(BlockPos.of(compoundNBT.getLong("Guard position")));
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
}
