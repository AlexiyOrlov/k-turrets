package dev.buildtool.kturrets;

import dev.buildtool.kturrets.registers.RegisterCapability;
import dev.buildtool.kturrets.registers.UnitLimitCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import java.util.function.Supplier;

/**
 * Works the same as spawn egg, except it reads the saved entity data from NBT
 */
public class ContainerItem extends ForgeSpawnEggItem {
    public static List<ContainerItem> turretPlacers = new ArrayList<>(12);
    public enum Unit {
        TURRET, DRONE
    }

    private final Unit unit;

    public ContainerItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props, Unit kind) {
        super(type, backgroundColor, highlightColor, props);
        unit = kind;
        turretPlacers.add(this);
    }

    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {

            if (FMLEnvironment.dist.isDedicatedServer()) {
                Player player = context.getPlayer();
                UnitLimitCapability unitLimitCapability = player.getCapability(RegisterCapability.unitCapability, null).orElse(null);
                if (unit == Unit.TURRET) {
                    if (unitLimitCapability.getTurretCount() >= KTurrets.TURRET_LIMIT_PER_PLAYER.get()) {
                        player.displayClientMessage(Component.literal("Reached turret limit of " + KTurrets.TURRET_LIMIT_PER_PLAYER.get() + ". Can't create more"), false);
                        return InteractionResult.CONSUME;
                    } else {
                        unitLimitCapability.setTurretCount(unitLimitCapability.getTurretCount() + 1);
                    }
                } else if (unit == Unit.DRONE) {
                    if (unitLimitCapability.getDroneCount() >= KTurrets.DRONE_LIMIT_PER_PLAYER.get()) {
                        player.displayClientMessage(Component.literal("Reached drone limit of " + KTurrets.DRONE_LIMIT_PER_PLAYER.get() + ". Can't create more"), false);
                        return InteractionResult.CONSUME;
                    } else {
                        unitLimitCapability.setDroneCount(unitLimitCapability.getDroneCount() + 1);
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
                    entity.setPosRaw(blockpos1.getX() + 0.5, blockpos.getY() + 1, blockpos.getZ() + 0.5);
                }
                itemstack.shrink(1);
            }

            return InteractionResult.CONSUME;
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        super.appendHoverText(itemStack, p_41422_, components, p_41424_);
        if (itemStack.hasTag()) {
            components.add(Component.literal("" + itemStack.getTag().getUUID("UUID")));
        }
    }
}
