package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.Drone;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityAttributes {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent attributeCreationEvent) {
        attributeCreationEvent.put(KEntities.ARROW_TURRET.get(), Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.ARROW_TURRET_RANGE.get()).add(Attributes.MAX_HEALTH, KTurrets.ARROW_TURRET_HEALTH.get()).add(Attributes.ARMOR, KTurrets.ARROW_TURRET_ARMOR.get()).build());
        attributeCreationEvent.put(KEntities.BULLET_TURRET.get(), Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.BULLET_TURRET_RANGE.get()).add(Attributes.MAX_HEALTH, KTurrets.BULLET_TURRET_HEALTH.get()).add(Attributes.ARMOR, KTurrets.BULLET_TURRET_ARMOR.get()).build());
        attributeCreationEvent.put(KEntities.FIRE_CHARGE_TURRET.get(), Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.CHARGE_TURRET_RANGE.get()).add(Attributes.MAX_HEALTH, KTurrets.CHARGE_TURRET_HEALTH.get()).add(Attributes.ARMOR, KTurrets.CHARGE_TURRET_ARMOR.get()).build());
        attributeCreationEvent.put(KEntities.BRICK_TURRET.get(), Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.BRICK_TURRET_RANGE.get()).add(Attributes.MAX_HEALTH, KTurrets.BRICK_TURRET_HEALTH.get()).add(Attributes.ARMOR, KTurrets.BRICK_TURRET_ARMOR.get()).build());
        attributeCreationEvent.put(KEntities.GAUSS_TURRET.get(), Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.GAUSS_TURRET_RANGE.get()).add(Attributes.MAX_HEALTH, KTurrets.GAUSS_TURRET_HEALTH.get()).add(Attributes.ARMOR, KTurrets.GAUSS_TURRET_ARMOR.get()).build());
        attributeCreationEvent.put(KEntities.COBBLE_TURRET.get(), Turret.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.COBBLE_TURRET_RANGE.get()).add(Attributes.MAX_HEALTH, KTurrets.COBBLE_TURRET_HEALTH.get()).add(Attributes.ARMOR, KTurrets.COBBLE_TURRET_ARMOR.get()).build());

        attributeCreationEvent.put(KEntities.BRICK_DRONE.get(), Drone.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.BRICK_TURRET_RANGE.get() - 5).add(Attributes.MAX_HEALTH, Math.max(10, KTurrets.BRICK_TURRET_HEALTH.get() * 0.83)).add(Attributes.ARMOR, Math.max(0, KTurrets.BRICK_TURRET_ARMOR.get() * 0.34)).build());
        attributeCreationEvent.put(KEntities.BULLET_DRONE.get(), Drone.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.BULLET_TURRET_RANGE.get() - 5).add(Attributes.MAX_HEALTH, Math.max(10, KTurrets.BULLET_TURRET_HEALTH.get() * 0.83)).add(Attributes.ARMOR, Math.max(0, KTurrets.BULLET_TURRET_ARMOR.get() * 0.34)).build());
        attributeCreationEvent.put(KEntities.COBBLE_DRONE.get(), Drone.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.COBBLE_TURRET_RANGE.get() - 5).add(Attributes.MAX_HEALTH, Math.max(10, KTurrets.COBBLE_TURRET_HEALTH.get() * 0.83)).add(Attributes.ARMOR, Math.max(0, KTurrets.COBBLE_TURRET_ARMOR.get() * 0.34)).build());
        attributeCreationEvent.put(KEntities.ARROW_DRONE.get(), Drone.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.ARROW_TURRET_RANGE.get() - 5).add(Attributes.MAX_HEALTH, Math.max(10, KTurrets.ARROW_TURRET_HEALTH.get() * 0.83)).add(Attributes.ARMOR, Math.max(0, KTurrets.ARROW_TURRET_ARMOR.get() * 0.34)).build());
        attributeCreationEvent.put(KEntities.GAUSS_DRONE.get(), Drone.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.GAUSS_TURRET_RANGE.get() - 5).add(Attributes.MAX_HEALTH, Math.max(10, KTurrets.GAUSS_TURRET_HEALTH.get() * 0.83)).add(Attributes.ARMOR, Math.max(0, KTurrets.GAUSS_TURRET_ARMOR.get() * 0.34)).build());
        attributeCreationEvent.put(KEntities.FIRECHARGE_DRONE.get(), Drone.createDefaultAttributes().add(Attributes.FOLLOW_RANGE, KTurrets.CHARGE_TURRET_RANGE.get() - 5).add(Attributes.MAX_HEALTH, Math.max(10, KTurrets.CHARGE_TURRET_HEALTH.get() * 0.83)).add(Attributes.ARMOR, Math.max(0, KTurrets.CHARGE_TURRET_ARMOR.get() * 0.34)).build());
    }
}
