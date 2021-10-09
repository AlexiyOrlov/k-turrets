package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.ArrowTurret;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@SuppressWarnings("unchecked")
public class TEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, KTurrets.ID);
    public static final EntityType<ArrowTurret> ARROW_TURRET = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new ArrowTurret(p_create_2_), EntityClassification.MISC).build("arrow_turret"));
    public static final RegistryObject<EntityType<ArrowTurret>> REGISTRY_OBJECT;

    static {
        REGISTRY_OBJECT = ENTITIES.register("arrow_turret", () -> ARROW_TURRET);
    }

    private static <E extends EntityType<T>, T extends Entity> E cast(EntityType<Entity> entityType) {
        return (E) entityType;
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent attributeCreationEvent) {
        attributeCreationEvent.put(ARROW_TURRET, Turret.createDefaultAttributes().build());
    }
}
