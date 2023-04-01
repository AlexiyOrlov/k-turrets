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
import dev.buildtool.kturrets.firecharge.FireballDroneContainer;
import dev.buildtool.kturrets.firecharge.FireballTurretContainer;
import dev.buildtool.kturrets.gauss.GaussDroneContainer;
import dev.buildtool.kturrets.gauss.GaussTurretContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TContainers {
    public static DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, KTurrets.ID);
    static public MenuType<ArrowTurretContainer> ARROW_TURRET;
    public static MenuType<BulletTurretContainer> BULLET_TURRET;
    public static MenuType<FireballTurretContainer> FIRE_CHARGE_TURRET;
    public static MenuType<BrickTurretContainer> BRICK_TURRET;
    public static MenuType<GaussTurretContainer> GAUSS_TURRET;
    public static MenuType<CobbleTurretContainer> COBBLE_TURRET;

    public static MenuType<BrickDroneContainer> BRICK_DRONE;

    public static final RegistryObject<MenuType<BulletDroneContainer>> BULLET_DRONE = CONTAINERS.register("bullet_drone", () -> IForgeMenuType.create(BulletDroneContainer::new));
    public static final RegistryObject<MenuType<CobbleDroneContainer>> COBBLE_DRONE = CONTAINERS.register("cobble_drone", () -> IForgeMenuType.create(CobbleDroneContainer::new));
    public static final RegistryObject<MenuType<ArrowDroneContainer>> ARROW_DRONE = CONTAINERS.register("arrow_drone", () -> IForgeMenuType.create(ArrowDroneContainer::new));
    public static final RegistryObject<MenuType<GaussDroneContainer>> GAUSS_DRONE = CONTAINERS.register("gauss_drone", () -> IForgeMenuType.create(GaussDroneContainer::new));
    public static final RegistryObject<MenuType<FireballDroneContainer>> FIRECHARGE_DRONE = CONTAINERS.register("firecharge_drone", () -> IForgeMenuType.create(FireballDroneContainer::new));

    static {
        ARROW_TURRET = IForgeMenuType.create(ArrowTurretContainer::new);
        CONTAINERS.register("arrow_turret", () -> ARROW_TURRET);
        BULLET_TURRET = IForgeMenuType.create(BulletTurretContainer::new);
        CONTAINERS.register("bullet_turret", () -> BULLET_TURRET);
        FIRE_CHARGE_TURRET = IForgeMenuType.create(FireballTurretContainer::new);
        CONTAINERS.register("fire_charge_turret", () -> FIRE_CHARGE_TURRET);
        BRICK_TURRET = IForgeMenuType.create(BrickTurretContainer::new);
        CONTAINERS.register("brick_turret", () -> BRICK_TURRET);
        GAUSS_TURRET = IForgeMenuType.create(GaussTurretContainer::new);
        CONTAINERS.register("gauss_turret", () -> GAUSS_TURRET);
        COBBLE_TURRET = IForgeMenuType.create(CobbleTurretContainer::new);
        CONTAINERS.register("cobble_turret", () -> COBBLE_TURRET);

        BRICK_DRONE = IForgeMenuType.create(BrickDroneContainer::new);
        CONTAINERS.register("brick_drone", () -> BRICK_DRONE);
    }
}
