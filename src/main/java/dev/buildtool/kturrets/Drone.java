package dev.buildtool.kturrets;

import dev.buildtool.kturrets.registers.Sounds;
import dev.buildtool.kturrets.tasks.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.loading.FMLEnvironment;

import javax.annotation.Nullable;

public abstract class Drone extends Turret {
    private static final DataParameter<Boolean> FOLLOWING_OWNER = EntityDataManager.defineId(Drone.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GUARDING_AREA = EntityDataManager.defineId(Drone.class, DataSerializers.BOOLEAN);
    private static final DataParameter<BlockPos> GUARD_POSITION = EntityDataManager.defineId(Drone.class, DataSerializers.BLOCK_POS);

    public Drone(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        moveControl = new DroneMovementControl(this, 20, true);
        setPathfindingMalus(PathNodeType.DANGER_CACTUS, -1);
        setPathfindingMalus(PathNodeType.DAMAGE_FIRE, -1);
        setPathfindingMalus(PathNodeType.DANGER_FIRE, -1);
    }

//    public boolean causeFallDamage(float p_147105_, float p_147106_, DamageSource p_147107_) {
//        return false;
//    }

    protected void checkFallDamage(double p_20809_, boolean p_20810_, BlockState p_20811_, BlockPos p_20812_) {

    }

    public void travel(Vector3d vector) {
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
            if (this.onGround) {
                f = this.level.getBlockState(ground).getSlipperiness(this.level, ground, this) * 0.91F;
            }

            float f1 = (float) (getAttributeValue(Attributes.MOVEMENT_SPEED) / (f * f * f));
            f = 0.91F;
            if (this.onGround) {
                f = this.level.getBlockState(ground).getSlipperiness(this.level, ground, this) * 0.91F;
            }

            this.moveRelative(this.onGround ? 0.1F * f1 : flyingSpeed, vector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(f));
        }

        this.calculateEntityAnimation(this, false);
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
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putBoolean("Following", isFollowingOwner());
        compoundNBT.putBoolean("Guarding", isGuardingArea());
        compoundNBT.putLong("Guard position", getGuardPosition().asLong());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
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
        goalSelector.addGoal(7, new Strafe(this));
        goalSelector.addGoal(8, new GuardArea(this));
    }

    @Override
    protected PathNavigator createNavigation(World p_21480_) {
        FlyingPathNavigator flyingpathnavigation = new FlyingPathNavigator(this, p_21480_);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    @Override
    public float getEyeHeight(Pose p_213307_1_) {
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
