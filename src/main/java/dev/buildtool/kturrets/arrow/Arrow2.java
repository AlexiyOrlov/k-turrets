package dev.buildtool.kturrets.arrow;

import dev.buildtool.kturrets.IndirectDamageSource;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.Turret;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class Arrow2 extends ArrowEntity {
    static double SPEED = KTurrets.PROJECTILE_SPEED.get();
    private final Turret turret;
    protected int knockback;

    public Arrow2(World world, AbstractArrowEntity abstractArrowEntity, Turret shooter, float f, double dx, double dy, double dz) {
        super(EntityType.ARROW, world);
        copyPosition(abstractArrowEntity);
        setPierceLevel(abstractArrowEntity.getPierceLevel());
        if (abstractArrowEntity instanceof SpectralArrowEntity) {
            addEffect(new EffectInstance(Effects.GLOWING, 200));
        } else if (abstractArrowEntity instanceof ArrowEntity) {
            potion = ((ArrowEntity) abstractArrowEntity).potion;
            ((ArrowEntity) abstractArrowEntity).effects.forEach(this::addEffect);
        }
        setOwner(abstractArrowEntity.getOwner());
        turret = shooter;
    }

    @Override
    protected void onHit(RayTraceResult p_70227_1_) {
        super.onHit(p_70227_1_);
        remove();
    }

    @Override
    public void setEnchantmentEffectsFromEntity(LivingEntity p_190547_1_, float p_190547_2_) {
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER_ARROWS, p_190547_1_);
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH_ARROWS, p_190547_1_);
        if (i > 0) {
            this.setBaseDamage(this.getBaseDamage() + (double) i * 0.5D + 0.5D);
        }

        if (j > 0) {
            this.setKnockback(j);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAMING_ARROWS, p_190547_1_) > 0) {
            this.setSecondsOnFire(100);
        }
    }

    @Override
    public void setKnockback(int knockback) {
        super.setKnockback(knockback);
        this.knockback = knockback;
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        Entity entity = p_213868_1_.getEntity();
        int i = MathHelper.ceil(this.getBaseDamage());

        if (this.isCritArrow()) {
            long j = this.random.nextInt(i / 2 + 2);
            i = (int) Math.min(j + (long) i, 2147483647L);
        }

        Entity entity1 = this.getOwner();
        DamageSource damagesource;
        if (entity1 == null || !entity1.isAlliedTo(entity)) {
            if (entity1 == null) {
                damagesource = new IndirectDamageSource("k_turrets.arrow", this, this);
            } else {
                damagesource = new IndirectDamageSource("k_turrets.arrow", this, entity1);
                if (entity1 instanceof LivingEntity) {
                    ((LivingEntity) entity1).setLastHurtMob(entity);
                }
            }

            boolean flag = entity.getType() == EntityType.ENDERMAN;
            int k = entity.getRemainingFireTicks();
            if (this.isOnFire() && !flag) {
                entity.setSecondsOnFire(5);
            }

            if (entity.hurt(damagesource, (float) i)) {
                if (flag) {
                    return;
                }

                if (entity instanceof LivingEntity) {
                    LivingEntity livingentity = (LivingEntity) entity;
                    if (!this.level.isClientSide && this.getPierceLevel() <= 0) {
                        livingentity.setArrowCount(livingentity.getArrowCount() + 1);
                    }

                    if (this.knockback > 0) {
                        Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double) this.knockback * 0.6D);
                        if (vector3d.lengthSqr() > 0.0D) {
                            livingentity.push(vector3d.x, 0.1D, vector3d.z);
                        }
                    }

                    if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
                        EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
                        EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
                    }

                    this.doPostHurtEffects(livingentity);
                    if (livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
                        ((ServerPlayerEntity) entity1).connection.send(new SChangeGameStatePacket(SChangeGameStatePacket.ARROW_HIT_PLAYER, 0.0F));
                    }
                }

                this.playSound(this.getHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                if (this.getPierceLevel() <= 0) {
                    this.remove();
                }
            } else {
                entity.setRemainingFireTicks(k);
                this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
                this.yRot += 180.0F;
                this.yRotO += 180.0F;
            }
        }
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        Entity entity = getOwner();
        if (entity instanceof Turret) {
            Turret owner = (Turret) entity;
            if (target instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) target;
                if (owner.getOwner().isPresent() && player.getUUID().equals(owner.getOwner().get()))
                    return false;
                return !target.isAlliedTo(owner);
            }
            if (target instanceof Turret) {
                Turret turret = (Turret) target;
                if (owner.getOwner().isPresent()) {
                    if (turret.getOwner().isPresent()) {
                        return !owner.getOwner().get().equals(turret.getOwner().get());
                    } else
                        return true;
                }
                return true;
            }
            if (target.getType().getCategory().isFriendly()) {
                return target == owner.getTarget();
            } else {
                return Turret.decodeTargets(owner.getTargets()).contains(target.getType());
            }
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getDeltaMovement().length() < 0.01)
            remove();
    }

    @Override
    public void shoot(double p_36775_, double p_36776_, double p_36777_, float p_36778_, float p_36779_) {
        double movementMultiplier = KTurrets.PROJECTILE_SPEED.get();
        //this multiplication could be wrong
        Vector3d vec3 = (new Vector3d(p_36775_, p_36776_, p_36777_).normalize().scale(movementMultiplier * 3));
        setDeltaMovement(vec3);
        double d0 = MathHelper.sqrt(getHorizontalDistanceSqr(vec3));
        this.yRot = ((float) (MathHelper.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)));
        this.xRot = ((float) (MathHelper.atan2(vec3.y, d0) * (double) (180F / (float) Math.PI)));
        this.yRotO = this.yRot;
        this.xRotO = this.xRot;
    }
}
