package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.arrow.ArrowTurretContainer;
import dev.buildtool.kturrets.bullet.BulletTurretContainer;
import dev.buildtool.kturrets.firecharge.FireChargeTurretContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TContainers {
    public static DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, KTurrets.ID);
    static public ContainerType<ArrowTurretContainer> ARROW_TURRET;
    public static ContainerType<BulletTurretContainer> BULLET_TURRET;
    public static ContainerType<FireChargeTurretContainer> FIRE_CHARGE_TURRET;
    static {
        ARROW_TURRET = IForgeContainerType.create(ArrowTurretContainer::new);
        CONTAINERS.register("arrow_turret", () -> ARROW_TURRET);
        BULLET_TURRET = IForgeContainerType.create(BulletTurretContainer::new);
        CONTAINERS.register("bullet_turret", () -> BULLET_TURRET);
        FIRE_CHARGE_TURRET = IForgeContainerType.create(FireChargeTurretContainer::new);
        CONTAINERS.register("fire_charge_turret", () -> FIRE_CHARGE_TURRET);
    }
}
