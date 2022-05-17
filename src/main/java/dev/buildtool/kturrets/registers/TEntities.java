package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.arrow.ArrowTurret;
import dev.buildtool.kturrets.brick.Brick;
import dev.buildtool.kturrets.brick.BrickDrone;
import dev.buildtool.kturrets.brick.BrickTurret;
import dev.buildtool.kturrets.bullet.Bullet;
import dev.buildtool.kturrets.bullet.BulletTurret;
import dev.buildtool.kturrets.cobble.CobbleTurret;
import dev.buildtool.kturrets.cobble.Cobblestone;
import dev.buildtool.kturrets.firecharge.FireChargeTurret;
import dev.buildtool.kturrets.gauss.GaussBullet;
import dev.buildtool.kturrets.gauss.GaussTurret;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unchecked")
public class TEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, KTurrets.ID);
    public static final RegistryObject<EntityType<ArrowTurret>> ARROW_TURRET = ENTITIES.register("arrow_turret", () -> cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new ArrowTurret(p_create_2_), MobCategory.MISC).sized(0.6f, 0.8f).build("arrow_turret")));
    public static final RegistryObject<EntityType<BulletTurret>> BULLET_TURRET = ENTITIES.register("bullet_turret", () -> cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new BulletTurret(p_create_2_), MobCategory.MISC).sized(0.8f, 0.8f).build("bullet_turret")));
    public static final RegistryObject<EntityType<Bullet>> BULLET = ENTITIES.register("bullet", () -> cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new Bullet(p_create_2_), MobCategory.MISC).sized(0.2f, 0.2f).build("bullet")));
    public static final RegistryObject<EntityType<FireChargeTurret>> FIRE_CHARGE_TURRET = ENTITIES.register("fire_charge_turret", () -> cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new FireChargeTurret(p_create_2_), MobCategory.MISC).sized(0.8f, 0.7f).build("fire_charge")));
    public static final RegistryObject<EntityType<Brick>> BRICK = ENTITIES.register("brick", () -> cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new Brick(p_create_2_), MobCategory.MISC).sized(0.4f, 0.4f).build("brick")));
    public static final RegistryObject<EntityType<BrickTurret>> BRICK_TURRET = ENTITIES.register("brick_turret", () -> cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new BrickTurret(p_create_2_), MobCategory.MISC).sized(0.7f, 0.7f).build("brick_turret")));
    public static final RegistryObject<EntityType<GaussTurret>> GAUSS_TURRET = ENTITIES.register("gauss_turret", () -> cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new GaussTurret(p_create_2_), MobCategory.MISC).sized(0.8f, 1f).build("gauss_turret")));
    public static final RegistryObject<EntityType<GaussBullet>> GAUSS_BULLET = ENTITIES.register("gauss_bullet", () -> cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new GaussBullet(p_create_2_), MobCategory.MISC).sized(0.2f, 0.2f).build("gauss_bullet")));
    public static final RegistryObject<EntityType<CobbleTurret>> COBBLE_TURRET = ENTITIES.register("cobble_turret", () -> cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new CobbleTurret(p_create_2_), MobCategory.MISC).sized(0.5f, 0.7f).build("cobble_turret")));
    public static final RegistryObject<EntityType<Cobblestone>> COBBLESTONE = ENTITIES.register("cobblestone", () -> cast(EntityType.Builder.of((p_create_1_, p_create_2_) -> new Cobblestone(p_create_2_), MobCategory.MISC).sized(0.25f, 0.25f).build("cobblestone")));

    public static final RegistryObject<EntityType<BrickDrone>> BRICK_DRONE = ENTITIES.register("brick_drone", () -> cast(EntityType.Builder.of((p_20722_, p_20723_) -> new BrickDrone(p_20723_), MobCategory.MISC).sized(0.6f, 0.6f).build("brick_drone")));
    private static EntityType cast(EntityType<Entity> entityType) {
        return entityType;
    }


}
