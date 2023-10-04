package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import dev.buildtool.kturrets.arrow.ArrowDrone;
import dev.buildtool.kturrets.arrow.ArrowTurret;
import dev.buildtool.kturrets.brick.Brick;
import dev.buildtool.kturrets.brick.BrickDrone;
import dev.buildtool.kturrets.brick.BrickTurret;
import dev.buildtool.kturrets.bullet.Bullet;
import dev.buildtool.kturrets.bullet.BulletDrone;
import dev.buildtool.kturrets.bullet.BulletTurret;
import dev.buildtool.kturrets.cobble.CobbleDrone;
import dev.buildtool.kturrets.cobble.CobbleTurret;
import dev.buildtool.kturrets.cobble.Cobblestone;
import dev.buildtool.kturrets.fireball.FireballDrone;
import dev.buildtool.kturrets.fireball.FireballTurret;
import dev.buildtool.kturrets.gauss.GaussBullet;
import dev.buildtool.kturrets.gauss.GaussDrone;
import dev.buildtool.kturrets.gauss.GaussTurret;
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
public class KEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, KTurrets.ID);
    public static final EntityType<ArrowTurret> ARROW_TURRET = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new ArrowTurret(p_create_2_), EntityClassification.MISC).sized(0.6f, 0.7f).build("arrow_turret"));
    public static final EntityType<BulletTurret> BULLET_TURRET = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new BulletTurret(p_create_2_), EntityClassification.MISC).sized(0.8f, 0.8f).build("bullet_turret"));
    public static final EntityType<Bullet> BULLET = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new Bullet(p_create_2_), EntityClassification.MISC).sized(0.2f, 0.2f).build("bullet"));
    public static final EntityType<FireballTurret> FIRE_CHARGE_TURRET = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new FireballTurret(p_create_2_), EntityClassification.MISC).sized(0.8f, 0.9f).fireImmune().build("fire_charge"));
    public static final EntityType<Brick> BRICK = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new Brick(p_create_2_), EntityClassification.MISC).sized(0.4f, 0.4f).build("brick"));
    public static final EntityType<BrickTurret> BRICK_TURRET = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new BrickTurret(p_create_2_), EntityClassification.MISC).sized(0.7f, 0.7f).build("brick_turret"));
    public static final EntityType<GaussTurret> GAUSS_TURRET = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new GaussTurret(p_create_2_), EntityClassification.MISC).sized(0.8f, 1f).build("gauss_turret"));
    public static final EntityType<GaussBullet> GAUSS_BULLET = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new GaussBullet(p_create_2_), EntityClassification.MISC).sized(0.2f, 0.2f).build("gauss_bullet"));
    public static final EntityType<CobbleTurret> COBBLE_TURRET = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new CobbleTurret(p_create_2_), EntityClassification.MISC).sized(0.5f, 0.7f).build("cobble_turret"));
    public static final EntityType<Cobblestone> COBBLESTONE = cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new Cobblestone(p_create_2_), EntityClassification.MISC).sized(0.25f, 0.25f).build("cobblestone"));
    public static final float DRONE_WIDTH = 0.6f;
    public static final EntityType<BrickDrone> BRICK_DRONE = cast(EntityType.Builder.of((p_20722_, p_20723_) -> new BrickDrone(p_20723_), EntityClassification.MISC).sized(DRONE_WIDTH, DRONE_WIDTH).build("brick_drone"));
    public static final EntityType<BulletDrone> BULLET_DRONE = cast(EntityType.Builder.of((p_20722_, p_20723_) -> new BulletDrone(p_20723_), EntityClassification.MISC).sized(DRONE_WIDTH, DRONE_WIDTH).build("bullet_drone"));
    public static final EntityType<CobbleDrone> COBBLE_DRONE = cast(EntityType.Builder.of((p_20722_, p_20723_) -> new CobbleDrone(p_20723_), EntityClassification.MISC).sized(DRONE_WIDTH, DRONE_WIDTH).build("cobble_drone"));
    public static final EntityType<ArrowDrone> ARROW_DRONE = cast(EntityType.Builder.of((p_20722_, p_20723_) -> new ArrowDrone(p_20723_), EntityClassification.MISC).sized(DRONE_WIDTH, DRONE_WIDTH).build("arrow_drone"));
    public static final EntityType<GaussDrone> GAUSS_DRONE = cast(EntityType.Builder.of((p_20722_, p_20723_) -> new GaussDrone(p_20723_), EntityClassification.MISC).sized(DRONE_WIDTH, DRONE_WIDTH).build("gauss_drone"));
    public static final EntityType<FireballDrone> FIRECHARGE_DRONE = cast(EntityType.Builder.of((p_20722_, p_20723_) -> new FireballDrone(p_20723_), EntityClassification.MISC).sized(DRONE_WIDTH, DRONE_WIDTH).fireImmune().build("firecharge_drone"));

    static {
        ENTITIES.register("arrow_turret", () -> ARROW_TURRET);
        ENTITIES.register("bullet_turret", () -> BULLET_TURRET);
        ENTITIES.register("fire_charge_turret", () -> FIRE_CHARGE_TURRET);
        ENTITIES.register("brick_turret", () -> BRICK_TURRET);
        ENTITIES.register("gauss_turret", () -> GAUSS_TURRET);
        ENTITIES.register("cobble_turret", () -> COBBLE_TURRET);
        ENTITIES.register("cobblestone", () -> COBBLESTONE);
        ENTITIES.register("bullet", () -> BULLET);
        ENTITIES.register("gauss_bullet", () -> GAUSS_BULLET);
        ENTITIES.register("brick", () -> BRICK);
        ENTITIES.register("cobble_drone", () -> COBBLE_DRONE);
        ENTITIES.register("arrow_drone", () -> ARROW_DRONE);
        ENTITIES.register("bullet_drone", () -> BULLET_DRONE);
        ENTITIES.register("firecharge_drone", () -> FIRECHARGE_DRONE);
        ENTITIES.register("brick_drone", () -> BRICK_DRONE);
        ENTITIES.register("gauss_drone", () -> GAUSS_DRONE);
    }

    private static <E extends EntityType<T>, T extends Entity> E cast(EntityType<Entity> entityType) {
        return (E) entityType;
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent attributeCreationEvent) {
        attributeCreationEvent.put(ARROW_TURRET, Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.ARROW_TURRET_RANGE.get()).add(Attributes.MAX_HEALTH, KTurrets.ARROW_TURRET_HEALTH.get()).add(Attributes.ARMOR, KTurrets.ARROW_TURRET_ARMOR.get()).build());
        attributeCreationEvent.put(BULLET_TURRET, Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.BULLET_TURRET_RANGE.get()).add(Attributes.MAX_HEALTH, KTurrets.BULLET_TURRET_HEALTH.get()).add(Attributes.ARMOR, KTurrets.BULLET_TURRET_ARMOR.get()).build());
        attributeCreationEvent.put(FIRE_CHARGE_TURRET, Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.FIREBALL_TURRET_RANGE.get()).add(Attributes.MAX_HEALTH, KTurrets.FIREBALL_TURRET_HEALTH.get()).add(Attributes.ARMOR, KTurrets.FIREBALL_TURRET_ARMOR.get()).build());
        attributeCreationEvent.put(BRICK_TURRET, Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.BRICK_TURRET_RANGE.get()).add(Attributes.MAX_HEALTH, KTurrets.BRICK_TURRET_HEALTH.get()).add(Attributes.ARMOR, KTurrets.BRICK_TURRET_ARMOR.get()).build());
        attributeCreationEvent.put(GAUSS_TURRET, Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.GAUSS_TURRET_RANGE.get()).add(Attributes.MAX_HEALTH, KTurrets.GAUSS_TURRET_HEALTH.get()).add(Attributes.ARMOR, KTurrets.GAUSS_TURRET_ARMOR.get()).build());
        attributeCreationEvent.put(COBBLE_TURRET, Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.COBBLE_TURRET_RANGE.get()).add(Attributes.MAX_HEALTH, KTurrets.COBBLE_TURRET_HEALTH.get()).add(Attributes.ARMOR, KTurrets.COBBLE_TURRET_ARMOR.get()).build());

        attributeCreationEvent.put(COBBLE_DRONE, Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.COBBLE_TURRET_RANGE.get() - 5).add(Attributes.MAX_HEALTH, KTurrets.COBBLE_TURRET_HEALTH.get() * 0.83).add(Attributes.ARMOR, KTurrets.COBBLE_TURRET_ARMOR.get() * 0.34).build());
        attributeCreationEvent.put(ARROW_DRONE, Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.ARROW_TURRET_RANGE.get() - 5).add(Attributes.MAX_HEALTH, KTurrets.ARROW_TURRET_HEALTH.get() * 0.83).add(Attributes.ARMOR, KTurrets.ARROW_TURRET_ARMOR.get() * 0.34).build());
        attributeCreationEvent.put(BULLET_DRONE, Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.BULLET_TURRET_RANGE.get() - 5).add(Attributes.MAX_HEALTH, KTurrets.BULLET_TURRET_HEALTH.get() * 0.83).add(Attributes.ARMOR, KTurrets.BULLET_TURRET_ARMOR.get() * 0.34).build());
        attributeCreationEvent.put(FIRECHARGE_DRONE, Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.FIREBALL_TURRET_RANGE.get() - 5).add(Attributes.MAX_HEALTH, KTurrets.FIREBALL_TURRET_HEALTH.get() * 0.83).add(Attributes.ARMOR, KTurrets.FIREBALL_TURRET_ARMOR.get() * 0.34).build());
        attributeCreationEvent.put(BRICK_DRONE, Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.BRICK_TURRET_RANGE.get() - 5).add(Attributes.MAX_HEALTH, KTurrets.BRICK_TURRET_HEALTH.get() * 0.83).add(Attributes.ARMOR, KTurrets.BRICK_TURRET_ARMOR.get() * 0.34).build());
        attributeCreationEvent.put(GAUSS_DRONE, Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.GAUSS_TURRET_RANGE.get() - 5).add(Attributes.MAX_HEALTH, KTurrets.GAUSS_TURRET_HEALTH.get() * 0.83).add(Attributes.ARMOR, KTurrets.GAUSS_TURRET_ARMOR.get() * 0.34).build());
    }
}
