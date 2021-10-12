package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.arrow.ArrowTurret;
import dev.buildtool.kturrets.brick.Brick;
import dev.buildtool.kturrets.brick.BrickTurret;
import dev.buildtool.kturrets.bullet.Bullet;
import dev.buildtool.kturrets.bullet.BulletTurret;
import dev.buildtool.kturrets.firecharge.FireChargeTurret;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attributes;
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
    public static final EntityType<Brick> BRICK = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new Brick(p_create_2_), EntityClassification.MISC).sized(0.4f, 0.4f).build("brick"));
    public static final EntityType<BrickTurret> BRICK_TURRET = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new BrickTurret(p_create_2_), EntityClassification.MISC).sized(0.7f, 1.8f).build("brick_turret"));

    static {
        ENTITIES.register("arrow_turret", () -> ARROW_TURRET);
        ENTITIES.register("bullet_turret", () -> BULLET_TURRET);
        ENTITIES.register("bullet", () -> BULLET);
        ENTITIES.register("fire_charge_turret", () -> FIRE_CHARGE_TURRET);
        ENTITIES.register("brick", () -> BRICK);
        ENTITIES.register("brick_turret", () -> BRICK_TURRET);
    }

    private static <E extends EntityType<T>, T extends Entity> E cast(EntityType<Entity> entityType) {
        return (E) entityType;
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent attributeCreationEvent) {
        attributeCreationEvent.put(ARROW_TURRET, Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.ARROW_TURRET_RANGE.get()).add(Attributes.MAX_HEALTH, KTurrets.ARROW_TURRET_HEALTH.get()).add(Attributes.ARMOR, KTurrets.ARROW_TURRET_ARMOR.get()).build());
        attributeCreationEvent.put(BULLET_TURRET, Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.BULLET_TURRET_RANGE.get()).add(Attributes.MAX_HEALTH, KTurrets.BULLET_TURRET_HEALTH.get()).add(Attributes.ARMOR, KTurrets.BULLET_TURRET_ARMOR.get()).build());
        attributeCreationEvent.put(FIRE_CHARGE_TURRET, Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.CHARGE_TURRET_RANGE.get()).add(Attributes.MAX_HEALTH, KTurrets.CHARGE_TURRET_HEALTH.get()).add(Attributes.ARMOR, KTurrets.CHARGE_TURRET_ARMOR.get()).build());
        attributeCreationEvent.put(BRICK_TURRET, Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.BRICK_TURRET_RANGE.get()).add(Attributes.MAX_HEALTH, KTurrets.BRICK_TURRET_HEALTH.get()).add(Attributes.ARMOR, KTurrets.BRICK_TURRET_ARMOR.get()).build());
    }
}
