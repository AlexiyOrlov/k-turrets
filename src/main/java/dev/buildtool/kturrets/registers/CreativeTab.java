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
        creativeModeTabEvent.registerCreativeModeTab(new ResourceLocation(KTurrets.ID, "items"), builder -> builder.title(Component.literal("K-Turrets")).icon(() -> new ItemStack(TItems.GAUSS_BULLET.get())).displayItems((p_259204_, output, p_260123_) -> {
            output.accept(TItems.COBBLE_TURRET.get());
            output.accept(TItems.ARROW_TURRET.get());
            output.accept(TItems.FIRECHARGE_TURRET.get());
            output.accept(TItems.BULLET_TURRET.get());
            output.accept(TItems.BRICK_TURRET.get());
            output.accept(TItems.GAUSS_TURRET.get());
            output.accept(TItems.SEMI_STEEL.get());
            output.accept(TItems.STEEL_INGOT.get());
            output.accept(TItems.GAUSS_BULLET.get());
            output.accept(TItems.COBBLE_DRONE.get());
            output.accept(TItems.ARROW_DRONE.get());
            output.accept(TItems.FIRE_CHARGE_DRONE.get());
            output.accept(TItems.BULLET_DRONE.get());
            output.accept(TItems.BRICK_DRONE.get());
            output.accept(TItems.GAUSS_DRONE.get());
            output.accept(TItems.EXPLOSIVE_POWDER.get());
        }).build());
    }
}
