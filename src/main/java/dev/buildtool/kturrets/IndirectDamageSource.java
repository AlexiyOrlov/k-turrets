package dev.buildtool.kturrets;

public class IndirectDamageSource /*extends DamageSource*/ {
//    @Nullable
//    private final Entity owner;

//    public IndirectDamageSource(String id, Entity target, @Nullable Entity owner) {
//        super(id, target);
//        this.owner = owner;
//    }

//    @Nullable
//    public Entity getEntity() {
//        return this.owner;
//    }
//
//    public Component getLocalizedDeathMessage(LivingEntity p_19410_) {
//        Component component = this.owner == null ? this.getDirectEntity().getDisplayName() : this.owner.getDisplayName();
//        ItemStack itemstack = this.owner instanceof LivingEntity ? ((LivingEntity) this.owner).getMainHandItem() : ItemStack.EMPTY;
//        String s = "death.attack." + this.getMsgId();
//        String s1 = s + ".item";
//        return !itemstack.isEmpty() && itemstack.hasCustomHoverName() ? Component.translatable(s1, p_19410_.getDisplayName(), component, itemstack.getDisplayName()) : Component.translatable(s, p_19410_.getDisplayName(), component);
//    }
}