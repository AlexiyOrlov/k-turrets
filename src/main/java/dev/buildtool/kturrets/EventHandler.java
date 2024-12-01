package dev.buildtool.kturrets;

import dev.buildtool.kturrets.registers.KItems;
import dev.buildtool.satako.Functions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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

    @SubscribeEvent
    public static void dropExp(LivingDeathEvent livingDeathEvent)
    {
        if(!livingDeathEvent.isCanceled()) {
            LivingEntity entity = livingDeathEvent.getEntity();
            Level level = entity.level();
            if (level instanceof ServerLevel serverLevel) {
                Entity deathCauser = livingDeathEvent.getSource().getEntity();
                if(deathCauser instanceof Drone drone)
                {
                    if(Functions.contains(KItems.EXP_LINK.get(), drone.upgrades))
                        ExperienceOrb.award(serverLevel,entity.getPosition(1),entity.getExperienceReward());
                }
                else if (deathCauser instanceof Turret turret) {
                    if(Functions.contains(KItems.EXP_LINK.get(), turret.upgrades))
                        ExperienceOrb.award(serverLevel, entity.getPosition(1), entity.getExperienceReward());
                }
            }
        }
    }
}
