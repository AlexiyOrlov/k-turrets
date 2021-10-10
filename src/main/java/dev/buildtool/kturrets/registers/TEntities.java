package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.arrow.ArrowTurret;
import dev.buildtool.kturrets.bullet.Bullet;
import dev.buildtool.kturrets.bullet.BulletTurret;
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
    public static final EntityType<ArrowTurret> ARROW_TURRET = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new ArrowTurret(p_create_2_), EntityClassification.MISC).sized(0.6f, 1.4f).build("arrow_turret"));
    public static final RegistryObject<EntityType<ArrowTurret>> REGISTRY_OBJECT;
    public static final EntityType<BulletTurret> BULLET_TURRET = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new BulletTurret(p_create_2_), EntityClassification.MISC).sized(0.6f, 1.4f).build("bullet_turret"));
    public static final EntityType<Bullet> BULLET = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new Bullet(p_create_2_), EntityClassification.MISC).sized(0.1f, 0.1f).build("bullet"));

    static {
        REGISTRY_OBJECT = ENTITIES.register("arrow_turret", () -> ARROW_TURRET);
        ENTITIES.register("bullet_turret", () -> BULLET_TURRET);
        ENTITIES.register("bullet", () -> BULLET);
    }

    private static <E extends EntityType<T>, T extends Entity> E cast(EntityType<Entity> entityType) {
        return (E) entityType;
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent attributeCreationEvent) {
        attributeCreationEvent.put(ARROW_TURRET, Turret.createDefaultAttributes().build());
        attributeCreationEvent.put(BULLET_TURRET, Turret.createDefaultAttributes().build());
    }
}
