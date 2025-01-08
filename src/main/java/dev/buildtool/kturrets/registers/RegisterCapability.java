package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
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
    public static void attachCapability(AttachCapabilitiesEvent attachCapabilitiesEvent) {
        Object o = attachCapabilitiesEvent.getObject();
        if (o instanceof ServerLevel) {
            attachCapabilitiesEvent.addCapability(new ResourceLocation(KTurrets.ID, "unit_limits"), new UnitLimitCapability.Provider());
        } else if (o instanceof ItemStack itemStack && KTItems.MAGNET_UPGRADE.isPresent() && itemStack.getItem()== KTItems.MAGNET_UPGRADE.get()) {
            attachCapabilitiesEvent.addCapability(new ResourceLocation(KTurrets.ID,"magnet_inventory"), new MagnetInventoryProvider());
        }
    }
}
