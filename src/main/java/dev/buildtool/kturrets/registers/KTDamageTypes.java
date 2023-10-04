package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class KTDamageTypes {
    public static final ResourceKey<DamageType> TURRET_ARROW = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(KTurrets.ID, "arrow"));
    public static final ResourceKey<DamageType> TURRET_FIREBALL = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(KTurrets.ID, "fireball"));
    public static final ResourceKey<DamageType> BULLET = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(KTurrets.ID, "bullet"));
    public static final ResourceKey<DamageType> GAUSS_BULLET = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(KTurrets.ID, "gauss_bullet"));
    public static final ResourceKey<DamageType> COBBLESTONE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(KTurrets.ID, "cobblestone"));
    public static final ResourceKey<DamageType> BRICK = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(KTurrets.ID, "brick"));
}
