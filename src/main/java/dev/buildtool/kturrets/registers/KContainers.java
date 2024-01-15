package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.arrow.ArrowDroneContainer;
import dev.buildtool.kturrets.arrow.ArrowTurretContainer;
import dev.buildtool.kturrets.brick.BrickDroneContainer;
import dev.buildtool.kturrets.brick.BrickTurretContainer;
import dev.buildtool.kturrets.bullet.BulletDroneContainer;
import dev.buildtool.kturrets.bullet.BulletTurretContainer;
import dev.buildtool.kturrets.cobble.CobbleDroneContainer;
import dev.buildtool.kturrets.cobble.CobbleTurretContainer;
import dev.buildtool.kturrets.fireball.FireballDroneContainer;
import dev.buildtool.kturrets.fireball.FireballTurretContainer;
import dev.buildtool.kturrets.gauss.GaussDroneContainer;
import dev.buildtool.kturrets.gauss.GaussTurretContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class KContainers {
    public static DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, KTurrets.ID);
    static public ContainerType<ArrowTurretContainer> ARROW_TURRET;
    public static ContainerType<BulletTurretContainer> BULLET_TURRET;
    public static ContainerType<FireballTurretContainer> FIRE_CHARGE_TURRET;
    public static ContainerType<BrickTurretContainer> BRICK_TURRET;
    public static ContainerType<GaussTurretContainer> GAUSS_TURRET;
    public static ContainerType<CobbleTurretContainer> COBBLE_TURRET;
    public static final ContainerType<BulletDroneContainer> BULLET_DRONE = IForgeContainerType.create(BulletDroneContainer::new);
    public static final ContainerType<CobbleDroneContainer> COBBLE_DRONE = IForgeContainerType.create(CobbleDroneContainer::new);
    public static final ContainerType<ArrowDroneContainer> ARROW_DRONE = IForgeContainerType.create(ArrowDroneContainer::new);
    public static final ContainerType<FireballDroneContainer> FIRECHARGE_DRONE = IForgeContainerType.create(FireballDroneContainer::new);
    public static final ContainerType<BrickDroneContainer> BRICK_DRONE = IForgeContainerType.create(BrickDroneContainer::new);
    public static final ContainerType<GaussDroneContainer> GAUSS_DRONE = IForgeContainerType.create(GaussDroneContainer::new);

    static {
        ARROW_TURRET = IForgeContainerType.create(ArrowTurretContainer::new);
        CONTAINERS.register("arrow_turret", () -> ARROW_TURRET);
        BULLET_TURRET = IForgeContainerType.create(BulletTurretContainer::new);
        CONTAINERS.register("bullet_turret", () -> BULLET_TURRET);
        FIRE_CHARGE_TURRET = IForgeContainerType.create(FireballTurretContainer::new);
        CONTAINERS.register("fire_charge_turret", () -> FIRE_CHARGE_TURRET);
        BRICK_TURRET = IForgeContainerType.create(BrickTurretContainer::new);
        CONTAINERS.register("brick_turret", () -> BRICK_TURRET);
        GAUSS_TURRET = IForgeContainerType.create(GaussTurretContainer::new);
        CONTAINERS.register("gauss_turret", () -> GAUSS_TURRET);
        COBBLE_TURRET = IForgeContainerType.create(CobbleTurretContainer::new);
        CONTAINERS.register("cobble_turret", () -> COBBLE_TURRET);
        CONTAINERS.register("arrow_drone", () -> ARROW_DRONE);
        CONTAINERS.register("bullet_drone", () -> BULLET_DRONE);
        CONTAINERS.register("fire_charge_drone", () -> FIRECHARGE_DRONE);
        CONTAINERS.register("brick_drone", () -> BRICK_DRONE);
        CONTAINERS.register("gauss_drone", () -> GAUSS_DRONE);
        CONTAINERS.register("cobble_drone", () -> COBBLE_DRONE);
    }
}
