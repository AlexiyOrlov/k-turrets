package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.storage.MagnetMenu;
import dev.buildtool.kturrets.ReloaderMenu;
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
import dev.buildtool.kturrets.storage.StorageDroneMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class KTContainers {
    public static DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, KTurrets.ID);

    public static RegistryObject<MenuType<ArrowTurretContainer>> ARROW_TURRET=CONTAINERS.register("arrow_turret", () -> IForgeMenuType.create(ArrowTurretContainer::new));;
    public static RegistryObject<MenuType<BulletTurretContainer>> BULLET_TURRET=CONTAINERS.register("bullet_turret", () -> IForgeMenuType.create(BulletTurretContainer::new));
    public static RegistryObject<MenuType<FireballTurretContainer>> FIRE_CHARGE_TURRET=CONTAINERS.register("fire_charge_turret", () ->IForgeMenuType.create(FireballTurretContainer::new));
    public static RegistryObject<MenuType<BrickTurretContainer>>BRICK_TURRET=CONTAINERS.register("brick_turret", () -> IForgeMenuType.create(BrickTurretContainer::new));
    public static RegistryObject<MenuType<GaussTurretContainer>> GAUSS_TURRET=CONTAINERS.register("gauss_turret", () -> IForgeMenuType.create(GaussTurretContainer::new));
    public static RegistryObject<MenuType<CobbleTurretContainer>> COBBLE_TURRET=CONTAINERS.register("cobble_turret", () -> IForgeMenuType.create(CobbleTurretContainer::new));

    public static final RegistryObject<MenuType<BrickDroneContainer>> BRICK_DRONE = CONTAINERS.register("brick_drone", () -> IForgeMenuType.create(BrickDroneContainer::new));
    public static final RegistryObject<MenuType<BulletDroneContainer>> BULLET_DRONE = CONTAINERS.register("bullet_drone", () -> IForgeMenuType.create(BulletDroneContainer::new));
    public static final RegistryObject<MenuType<CobbleDroneContainer>> COBBLE_DRONE = CONTAINERS.register("cobble_drone", () -> IForgeMenuType.create(CobbleDroneContainer::new));
    public static final RegistryObject<MenuType<ArrowDroneContainer>> ARROW_DRONE = CONTAINERS.register("arrow_drone", () -> IForgeMenuType.create(ArrowDroneContainer::new));
    public static final RegistryObject<MenuType<GaussDroneContainer>> GAUSS_DRONE = CONTAINERS.register("gauss_drone", () -> IForgeMenuType.create(GaussDroneContainer::new));
    public static final RegistryObject<MenuType<FireballDroneContainer>> FIRECHARGE_DRONE = CONTAINERS.register("firecharge_drone", () -> IForgeMenuType.create(FireballDroneContainer::new));
    public static final RegistryObject<MenuType<ReloaderMenu>> RELOADER = CONTAINERS.register("reloader", () -> IForgeMenuType.create(ReloaderMenu::new));
    public static final RegistryObject<MenuType<StorageDroneMenu>> STORAGE_DRONE = CONTAINERS.register("storage_drone", () -> IForgeMenuType.create(StorageDroneMenu::new));
    public static final RegistryObject<MenuType<MagnetMenu>> MAGNET=CONTAINERS.register("magnet",() -> IForgeMenuType.create(MagnetMenu::new));
}
