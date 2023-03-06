package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Sounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, KTurrets.ID);
    public static final RegistryObject<SoundEvent> BULLET_FIRE = SOUNDS.register("bullet_fire", () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(KTurrets.ID, "bullet_shoot"), 16));
    public static final RegistryObject<SoundEvent> GAUSS_SHOT = SOUNDS.register("gauss_shot", () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(KTurrets.ID, "gauss_shoot"), 16));
    public static final RegistryObject<SoundEvent> COBBLE_SHOT = SOUNDS.register("cobble_shoot", () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(KTurrets.ID, "cobble_shoot"), 16));
    public static final RegistryObject<SoundEvent> DRONE_FLY = SOUNDS.register("drone_fly", () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(KTurrets.ID, "drone_fly"), 16));
}
