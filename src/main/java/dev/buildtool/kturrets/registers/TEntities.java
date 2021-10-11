package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.arrow.ArrowTurret;
import dev.buildtool.kturrets.bullet.Bullet;
import dev.buildtool.kturrets.bullet.BulletTurret;
import dev.buildtool.kturrets.firecharge.FireChargeTurret;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@SuppressWarnings("unchecked")
public class TEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, KTurrets.ID);
    public static final EntityType<ArrowTurret> ARROW_TURRET = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new ArrowTurret(p_create_2_), EntityClassification.MISC).sized(0.6f, 1.4f).build("arrow_turret"));
    public static final EntityType<BulletTurret> BULLET_TURRET = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new BulletTurret(p_create_2_), EntityClassification.MISC).sized(0.6f, 1.2f).build("bullet_turret"));
    public static final EntityType<Bullet> BULLET = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new Bullet(p_create_2_), EntityClassification.MISC).sized(0.2f, 0.2f).build("bullet"));
    public static final EntityType<FireChargeTurret> FIRE_CHARGE_TURRET = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new FireChargeTurret(p_create_2_), EntityClassification.MISC).sized(0.8f, 1.7f).build("fire_charge"));

    static {
        ENTITIES.register("arrow_turret", () -> ARROW_TURRET);
        ENTITIES.register("bullet_turret", () -> BULLET_TURRET);
        ENTITIES.register("bullet", () -> BULLET);
        ENTITIES.register("fire_charge_turret", () -> FIRE_CHARGE_TURRET);
    }

    private static <E extends EntityType<T>, T extends Entity> E cast(EntityType<Entity> entityType) {
        return (E) entityType;
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent attributeCreationEvent) {
        attributeCreationEvent.put(ARROW_TURRET, Turret.createDefaultAttributes().build());
        attributeCreationEvent.put(BULLET_TURRET, Turret.createDefaultAttributes().build());
        attributeCreationEvent.put(FIRE_CHARGE_TURRET, Turret.createDefaultAttributes().build());
    }
}
