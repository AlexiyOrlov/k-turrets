package dev.buildtool.kturrets.arrow;

import dev.buildtool.kturrets.IndirectDamageSource;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;


public class Arrow2 extends Arrow {
    public Arrow2(Level world, AbstractArrow abstractArrowEntity, LivingEntity shooter, float f) {
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
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
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

    protected void onHitEntity(EntityHitResult p_36757_) {
        Entity entity = p_36757_.getEntity();
        double i = getBaseDamage();

        if (this.isCritArrow()) {
            long j = this.random.nextInt((int) (i / 2 + 2));
            i = (int) Math.min(j + (long) i, 2147483647L);
        }

        Entity entity1 = this.getOwner();
        DamageSource damagesource;
        if (entity1 == null || !entity1.isAlliedTo(entity)) {
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
    }

    @Override
    public void tick() {
        super.tick();
        tickDespawn();
    }
}
