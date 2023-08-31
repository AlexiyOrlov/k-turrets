package dev.buildtool.kturrets.arrow;

import dev.buildtool.kturrets.IndirectDamageSource;
import dev.buildtool.kturrets.Turret;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;


public class Arrow2 extends Arrow {
    private final Turret turret;

    public Arrow2(Level world, AbstractArrow abstractArrowEntity, Turret shooter, float f) {
        super(EntityType.ARROW, world);
        copyPosition(abstractArrowEntity);
        setDeltaMovement(abstractArrowEntity.getDeltaMovement());
        setPierceLevel(abstractArrowEntity.getPierceLevel());
        if (abstractArrowEntity instanceof SpectralArrow) {
            addEffect(new MobEffectInstance(MobEffects.GLOWING, 200));
        } else if (abstractArrowEntity instanceof Arrow arrow) {
            potion = arrow.potion;
            arrow.effects.forEach(this::addEffect);
        }
        setOwner(abstractArrowEntity.getOwner());
        turret = shooter;
    }

    @Override
    protected void onHitBlock(BlockHitResult p_36755_) {
        super.onHitBlock(p_36755_);
        discard();
    }

    @Override
    public void setEnchantmentEffectsFromEntity(LivingEntity shooter, float p_36747_) {
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER_ARROWS, shooter);
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH_ARROWS, shooter);
        if (i > 0) {
            this.setBaseDamage(this.getBaseDamage() + (double) i * 0.5D + 0.5D);
        }
        if (j > 0) {
            this.setKnockback(j);
        }
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAMING_ARROWS, shooter) > 0) {
            this.setSecondsOnFire(100);
        }
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        Entity owner = getOwner();
        if (turret != null && target.getType().getCategory().isFriendly() && Turret.decodeTargets(turret.getTargets()).contains(target.getType()))
            return super.canHitEntity(target);
        else if (owner == null || !owner.isAlliedTo(target) && !target.getType().getCategory().isFriendly()) {
            return super.canHitEntity(target);
        }
        return true;
    }

    protected void onHitEntity(EntityHitResult p_36757_) {
        Entity entity = p_36757_.getEntity();
        double i = getBaseDamage();

        if (this.isCritArrow()) {
            long j = this.random.nextInt((int) (i / 2 + 2));
            i = (int) Math.min(j + (long) i, 2147483647L);
        }

        Entity entity1 = this.getOwner();
        DamageSource damagesource;
            if (entity1 == null) {
                damagesource = new IndirectDamageSource("arrow", this, this);
            } else {
                damagesource = new IndirectDamageSource("arrow", this, entity1);
                if (entity1 instanceof LivingEntity) {
                    ((LivingEntity) entity1).setLastHurtMob(entity);
                }
            }
            int k = entity.getRemainingFireTicks();
            if (this.isOnFire()) {
                entity.setSecondsOnFire(5);
            }
            if (entity.hurt(damagesource, (float) i)) {
                if (entity instanceof LivingEntity livingentity) {
                    if (!this.level.isClientSide && this.getPierceLevel() <= 0) {
                        livingentity.setArrowCount(livingentity.getArrowCount() + 1);
                    }

                    if (this.getKnockback() > 0) {
                        Vec3 vec3 = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double) this.getKnockback() * 0.6D);
                        if (vec3.lengthSqr() > 0.0D) {
                            livingentity.push(vec3.x, 0.1D, vec3.z);
                        }
                    }

                    if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
                        EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
                        EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
                    }

                    this.doPostHurtEffects(livingentity);
                    if (livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer && !this.isSilent()) {
                        ((ServerPlayer) entity1).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                    }
                }

                this.playSound(this.getHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
                if (this.getPierceLevel() <= 0) {
                    this.discard();
                }
            } else {
                entity.setRemainingFireTicks(k);
                this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
                this.setYRot(this.getYRot() + 180.0F);
                this.yRotO += 180.0F;
                if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
                    this.discard();
                }
            }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getDeltaMovement().length() < 1)
            discard();
    }


    @Override
    public void shoot(double p_36775_, double p_36776_, double p_36777_, float p_36778_, float p_36779_) {
        Vec3 vec3 = (new Vec3(p_36775_, p_36776_, p_36777_));
        this.setDeltaMovement(vec3);
        double d0 = vec3.horizontalDistance();
        this.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)));
        this.setXRot((float) (Mth.atan2(vec3.y, d0) * (double) (180F / (float) Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }
}
