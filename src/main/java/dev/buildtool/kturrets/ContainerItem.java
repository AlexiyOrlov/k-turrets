package dev.buildtool.kturrets;

import dev.buildtool.kturrets.registers.RegisterCapability;
import dev.buildtool.kturrets.registers.UnitLimitCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Works the same as spawn egg, except it reads the saved entity data from NBT
 */
public class ContainerItem extends ForgeSpawnEggItem {
    public enum Unit {
        TURRET, DRONE
    }

    private final Unit unit;

    public ContainerItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props, Unit kind) {
        super(type, backgroundColor, highlightColor, props);
        unit = kind;
    }

    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) {
            return InteractionResult.FAIL;
        } else {

            Player player = context.getPlayer();
            UUID playerUUID = player.getUUID();
            if (FMLEnvironment.dist.isDedicatedServer()) {
                UnitLimitCapability unitLimitCapability = level.getCapability(RegisterCapability.unitCapability, null).orElse(null);
                if (unit == Unit.TURRET) {
                    if (unitLimitCapability.getTurretCount(playerUUID) >= KTurrets.TURRET_LIMIT_PER_PLAYER.get()) {
                        player.displayClientMessage(Component.translatable("k_turrets.reached.turret.limit",KTurrets.TURRET_LIMIT_PER_PLAYER.get()), false);
                        return InteractionResult.CONSUME;
                    } else {
                        unitLimitCapability.setTurretCount(playerUUID, unitLimitCapability.getTurretCount(playerUUID) + 1);
                        player.displayClientMessage(Component.translatable("k_turrets.turrets.remain",KTurrets.TURRET_LIMIT_PER_PLAYER.get()-unitLimitCapability.getTurretCount(playerUUID)),false);
                    }
                } else if (unit == Unit.DRONE) {
                    if (unitLimitCapability.getDroneCount(playerUUID) >= KTurrets.DRONE_LIMIT_PER_PLAYER.get()) {
                        player.displayClientMessage(Component.translatable("k_turrets.reached.drone.limit",KTurrets.DRONE_LIMIT_PER_PLAYER.get()), false);
                        return InteractionResult.CONSUME;
                    } else {
                        unitLimitCapability.setDroneCount(playerUUID, unitLimitCapability.getDroneCount(playerUUID) + 1);
                        player.displayClientMessage(Component.translatable("k_turrets.drones.remain",KTurrets.DRONE_LIMIT_PER_PLAYER.get()-unitLimitCapability.getDroneCount(playerUUID)),false);
                    }
                }
            }

            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = level.getBlockState(blockpos);

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(direction);
            }

            EntityType<?> entitytype = this.getType(itemstack.getTag());
            Entity entity = entitytype.spawn((ServerLevel) level, itemstack, context.getPlayer(), blockpos1, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP);
            if (entity != null) {
                //the difference
                if (itemstack.hasTag()) {
                    entity.deserializeNBT(itemstack.getTag().getCompound("Contained"));
                    entity.absMoveTo(blockpos1.getX() + 0.5, blockpos.getY() + 1, blockpos.getZ() + 0.5);
                } else if (KTurrets.SET_OWNER_AUTO.get()) {
                    Turret turret = (Turret) entity;
                    turret.setOwner(playerUUID);
                }
                itemstack.shrink(1);
            }

            return InteractionResult.CONSUME;
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level pLevel, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, pLevel, components, tooltipFlag);
        if (itemStack.hasTag()) {
            components.add(Component.translatable("k_turrets.deployed"));
        }
    }
}
