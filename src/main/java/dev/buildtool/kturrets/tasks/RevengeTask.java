package dev.buildtool.kturrets.tasks;

import dev.buildtool.kturrets.Turret;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.world.GameRules;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class RevengeTask extends TargetGoal {
    private static final EntityPredicate HURT_BY_TARGETING = new EntityPredicate().allowUnseeable().ignoreInvisibilityTesting();
    private boolean alertSameType;
    private int timestamp;
    private final Class<?>[] toIgnoreDamage;
    @Nullable
    private Class<?>[] toIgnoreAlert;
    private final Turret turret;

    public RevengeTask(Turret owner, Class<?>... ignored) {
        super(owner, true);
        turret = owner;
        this.toIgnoreDamage = ignored;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (turret.isArmed()) {
            int i = this.mob.getLastHurtByMobTimestamp();
            LivingEntity livingentity = this.mob.getLastHurtByMob();
            if (i != this.timestamp && livingentity != null) {
                if (livingentity.getType() == EntityType.PLAYER && this.mob.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                    return false;
                } else {
                    for (Class<?> oclass : this.toIgnoreDamage) {
                        if (oclass.isAssignableFrom(livingentity.getClass())) {
                            return false;
                        }
                    }
                    return this.canAttack(livingentity, HURT_BY_TARGETING);
                }
            } else return false;
        } else {
            return false;
        }
    }
}
