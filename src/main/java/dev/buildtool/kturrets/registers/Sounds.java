package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class Sounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(SoundEvent.class, KTurrets.ID);
    public static final RegistryObject<SoundEvent> BULLET_FIRE = SOUNDS.register("bullet_fire", () -> new SoundEvent(new ResourceLocation(KTurrets.ID, "bullet_shoot")));
}
