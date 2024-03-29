package dev.buildtool.kturrets;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Copy of {@link net.minecraft.world.damagesource.IndirectEntityDamageSource}
 */
public class IndirectDamageSource extends EntityDamageSource {
    @Nullable
    private final Entity owner;

    public IndirectDamageSource(String id, Entity target, @Nullable Entity owner) {
        super(id, target);
        this.owner = owner;
    }

    @Nullable
    public Entity getDirectEntity() {
        return this.entity;
    }

    @Nullable
    public Entity getEntity() {
        return this.owner;
    }

    public Component getLocalizedDeathMessage(LivingEntity p_19410_) {
        Component component = this.owner == null ? this.entity.getDisplayName() : this.owner.getDisplayName();
        ItemStack itemstack = this.owner instanceof LivingEntity ? ((LivingEntity) this.owner).getMainHandItem() : ItemStack.EMPTY;
        String s = "death.attack." + this.msgId;
        String s1 = s + ".item";
        return !itemstack.isEmpty() && itemstack.hasCustomHoverName() ? Component.translatable(s1, p_19410_.getDisplayName(), component, itemstack.getDisplayName()) : Component.translatable(s, p_19410_.getDisplayName(), component);
    }
}