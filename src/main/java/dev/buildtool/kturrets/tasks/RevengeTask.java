package dev.buildtool.kturrets.tasks;

import dev.buildtool.kturrets.Turret;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class RevengeTask extends TargetGoal {
    private static final TargetingConditions HURT_BY_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
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

    @Override
    public boolean canContinueToUse() {
        return turret.isArmed() && super.canContinueToUse();
    }

    public RevengeTask setAlertOthers(Class<?>... p_26045_) {
        this.alertSameType = true;
        this.toIgnoreAlert = p_26045_;
        return this;
    }

    public void start() {
        this.mob.setTarget(this.mob.getLastHurtByMob());
        this.targetMob = this.mob.getTarget();
        this.timestamp = this.mob.getLastHurtByMobTimestamp();
        this.unseenMemoryTicks = 300;
        if (this.alertSameType) {
            this.alertOthers();
        }

        super.start();
    }

    protected void alertOthers() {
        double d0 = this.getFollowDistance();
        AABB aabb = AABB.unitCubeFromLowerCorner(this.mob.position()).inflate(d0, 10.0D, d0);
        List<? extends Mob> list = this.mob.level.getEntitiesOfClass(this.mob.getClass(), aabb, EntitySelector.NO_SPECTATORS);
        Iterator<? extends Mob> iterator = list.iterator();

        while (true) {
            Mob mob;
            while (true) {
                if (!iterator.hasNext()) {
                    return;
                }

                mob = iterator.next();
                if (this.mob != mob && mob.getTarget() == null && (!(this.mob instanceof TamableAnimal) || ((TamableAnimal) this.mob).getOwner() == ((TamableAnimal) mob).getOwner()) && !mob.isAlliedTo(this.mob.getLastHurtByMob())) {
                    if (this.toIgnoreAlert == null) {
                        break;
                    }

                    boolean flag = false;

                    for (Class<?> oclass : this.toIgnoreAlert) {
                        if (mob.getClass() == oclass) {
                            flag = true;
                            break;
                        }
                    }

                    if (!flag) {
                        break;
                    }
                }
            }

            this.alertOther(mob, this.mob.getLastHurtByMob());
        }
    }

    protected void alertOther(Mob p_26042_, LivingEntity p_26043_) {
        p_26042_.setTarget(p_26043_);
    }
}
