package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RegisterCapability {
    public static net.minecraftforge.common.capabilities.Capability<UnitLimitCapability> unitCapability = CapabilityManager.get(new CapabilityToken<>() {
    });

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Level> attachCapabilitiesEvent) {
        Level level = attachCapabilitiesEvent.getObject();
        if (level instanceof ServerLevel) {
            attachCapabilitiesEvent.addCapability(new ResourceLocation(KTurrets.ID, "unit_limits"), new UnitLimitCapability.Provider());
        }
    }
}
