package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.KTurrets;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeTab {
    @SubscribeEvent
    public static void registerCreativeTab(CreativeModeTabEvent.Register creativeModeTabEvent) {
        creativeModeTabEvent.registerCreativeModeTab(new ResourceLocation(KTurrets.ID, "items"), builder -> builder.title(Component.literal("K-Turrets")).icon(() -> new ItemStack(TItems.GAUSS_BULLET.get())).displayItems((p_259204_, p_259752_, p_260123_) -> {
            p_259752_.accept(TItems.COBBLE_TURRET.get());
            p_259752_.accept(TItems.ARROW_TURRET.get());
            p_259752_.accept(TItems.FIRECHARGE_TURRET.get());
            p_259752_.accept(TItems.BULLET_TURRET.get());
            p_259752_.accept(TItems.BRICK_TURRET.get());
            p_259752_.accept(TItems.GAUSS_TURRET.get());
            p_259752_.accept(TItems.SEMI_STEEL.get());
            p_259752_.accept(TItems.STEEL_INGOT.get());
            p_259752_.accept(TItems.GAUSS_BULLET.get());
            p_259752_.accept(TItems.COBBLE_DRONE.get());
            p_259752_.accept(TItems.ARROW_DRONE.get());
            p_259752_.accept(TItems.FIRE_CHARGE_DRONE.get());
            p_259752_.accept(TItems.BULLET_DRONE.get());
            p_259752_.accept(TItems.BRICK_DRONE.get());
            p_259752_.accept(TItems.GAUSS_DRONE.get());
        }).build());
    }
}
