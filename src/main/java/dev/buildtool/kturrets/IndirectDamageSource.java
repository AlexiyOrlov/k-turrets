package dev.buildtool.kturrets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class IndirectDamageSource extends EntityDamageSource {
    private final Entity owner;

    public IndirectDamageSource(String p_i1566_1_, Entity target, @Nullable Entity owner) {
        super(p_i1566_1_, target);
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

    public ITextComponent getLocalizedDeathMessage(LivingEntity p_151519_1_) {
        ITextComponent itextcomponent = this.owner == null ? this.entity.getDisplayName() : this.owner.getDisplayName();
        ItemStack itemstack = this.owner instanceof LivingEntity ? ((LivingEntity) this.owner).getMainHandItem() : ItemStack.EMPTY;
        String s = "death.attack." + this.msgId;
        String s1 = s + ".item";
        return !itemstack.isEmpty() && itemstack.hasCustomHoverName() ? new TranslationTextComponent(s1, p_151519_1_.getDisplayName(), itextcomponent, itemstack.getDisplayName()) : new TranslationTextComponent(s, p_151519_1_.getDisplayName(), itextcomponent);
    }
}
