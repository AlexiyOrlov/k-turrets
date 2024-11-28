package dev.buildtool.kturrets;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void defendPlayer(LivingDamageEvent livingDamageEvent)
    {
        LivingEntity livingEntity= livingDamageEvent.getEntity();
        if(livingEntity instanceof ServerPlayer serverPlayer &&!livingDamageEvent.isCanceled())
        {
            Level level=serverPlayer.level();
            List<Turret> turrets=level.getEntitiesOfClass(Turret.class,serverPlayer.getBoundingBox().inflate(128));
            turrets.forEach(turret -> {
                Entity directSource = livingDamageEvent.getSource().getEntity();
                if(turret.isProtectingOwner() && turret.getTarget()==null && directSource instanceof LivingEntity living && turret.isArmed())
                {
                    turret.setTarget(living);
                }
            });
        }
    }
}
