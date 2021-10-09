package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.ArrowTurretContainer;
import dev.buildtool.kturrets.KTurrets;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TContainers {
    public static DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, KTurrets.ID);
    static public ContainerType<ArrowTurretContainer> ARROW_TURRET;

    static {

        ARROW_TURRET = new ContainerType<>((p_create_1_, p_create_2_) -> new ArrowTurretContainer(p_create_1_));
        CONTAINERS.register("arrow_turret", () -> ARROW_TURRET);

    }
}
