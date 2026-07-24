package dev.buildtool.kturrets.registers;

import dev.buildtool.kturrets.ContainerItem;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.storage.Magnet;
import dev.buildtool.kturrets.TargetCopier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KTItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KTurrets.ID);

    public static RegistryObject<Item> ARROW_TURRET;
    public static RegistryObject<Item> BRICK_TURRET;
    public static RegistryObject<Item> BULLET_TURRET;
    public static RegistryObject<Item> FIRECHARGE_TURRET;
    public static RegistryObject<Item> GAUSS_BULLET,BULLET;
    public static RegistryObject<Item> GAUSS_TURRET;
    public static RegistryObject<Item> COBBLE_TURRET;
    public static RegistryObject<Item> EXPLOSIVE_POWDER;
    public static RegistryObject<Item> BRICK_DRONE;
    public static RegistryObject<Item> BULLET_DRONE;
    public static RegistryObject<Item> COBBLE_DRONE;
    public static RegistryObject<Item> ARROW_DRONE;
    public static RegistryObject<Item> GAUSS_DRONE;
    public static RegistryObject<Item> FIREBALL_DRONE;
    public static RegistryObject<Item> TITANIUM_ORE;
    public static RegistryObject<Item> DEEPSLATE_TITANIUM_ORE;
    public static RegistryObject<Item> RAW_TITANIUM;
    public static RegistryObject<Item> TITANIUM_INGOT;
    public static RegistryObject<Item> TARGET_COPIER;
    public static RegistryObject<Item> RELOADER;
    public static RegistryObject<Item> STORAGE_DRONE;
    public static RegistryObject<Item> LIGHT_UPGRADE,MAGNET_UPGRADE,RECALL_UPGRADE,EXP_LINK, FIRE_SHIELD,LOOTING_LINK;
    public static RegistryObject<Item> COPPER_PLATE;

    static {
        ARROW_TURRET = ITEMS.register("arrow_turret_item", () -> new ContainerItem(KTEntities.ARROW_TURRET, 0x0CA207, 0xA2A009, defaults(), ContainerItem.Unit.TURRET){
            @Override
            public void appendHoverText(ItemStack itemStack, @Nullable Level pLevel, List<Component> components, TooltipFlag tooltipFlag) {
                super.appendHoverText(itemStack, pLevel, components, tooltipFlag);
                components.add(Component.translatable("k_turrets.damage.info").append(": "+KTurrets.ARROW_TURRET_DAMAGE.get()));
                components.add(Component.translatable("k_turrets.integrity").append(": "+String.format("%.1f",KTurrets.ARROW_TURRET_HEALTH.get())));
                components.add(Component.translatable("k_turrets.armor.info").append(": "+String.format("%.1f",KTurrets.ARROW_TURRET_ARMOR.get())));
                components.add(Component.translatable("k_turrets.range").append(": "+String.format("%.1f",KTurrets.ARROW_TURRET_RANGE.get())));
                components.add(Component.translatable("k_turrets.fire.rate").append(": "+String.format("%.1f",(float)20/KTurrets.ARROW_TURRET_RATE.get())).append(" ").append(Component.translatable("k_turrets.shots.per.second")));
            }
        });
        BULLET_TURRET = ITEMS.register("bullet_turret_item", () -> new ContainerItem(KTEntities.BULLET_TURRET, 0xA2A1A0, 0x009EA2, defaults(), ContainerItem.Unit.TURRET){
            @Override
            public void appendHoverText(ItemStack itemStack, @Nullable Level pLevel, List<Component> components, TooltipFlag tooltipFlag) {
                super.appendHoverText(itemStack, pLevel, components, tooltipFlag);
                components.add(Component.translatable("k_turrets.damage.info").append(": "+KTurrets.IRON_BULLET_DAMAGE.get()));
                components.add(Component.translatable("k_turrets.integrity").append(": "+String.format("%.1f",KTurrets.BULLET_TURRET_HEALTH.get())));
                components.add(Component.translatable("k_turrets.armor.info").append(": "+String.format("%.1f",KTurrets.BULLET_TURRET_ARMOR.get())));
                components.add(Component.translatable("k_turrets.range").append(": "+String.format("%.1f",KTurrets.BULLET_TURRET_RANGE.get())));
                components.add(Component.translatable("k_turrets.fire.rate").append(": "+String.format("%.1f",(float)20/KTurrets.BULLET_TURRET_RATE.get())).append(" ").append(Component.translatable("k_turrets.shots.per.second")));
            }
        });
        FIRECHARGE_TURRET = ITEMS.register("firecharge_turret_item", () -> new ContainerItem(KTEntities.FIRE_CHARGE_TURRET, 0x0, 0xA20005, defaults(), ContainerItem.Unit.TURRET){
            @Override
            public void appendHoverText(ItemStack itemStack, @Nullable Level pLevel, List<Component> components, TooltipFlag tooltipFlag) {
                super.appendHoverText(itemStack, pLevel, components, tooltipFlag);
                components.add(Component.translatable("k_turrets.damage.info").append(": "+KTurrets.FIREBALL_TURRET_DAMAGE.get()));
                components.add(Component.translatable("k_turrets.integrity").append(": "+String.format("%.1f",KTurrets.FIREBALL_TURRET_HEALTH.get())));
                components.add(Component.translatable("k_turrets.armor.info").append(": "+String.format("%.1f",KTurrets.FIREBALL_TURRET_ARMOR.get())));
                components.add(Component.translatable("k_turrets.range").append(": "+String.format("%.1f",KTurrets.FIREBALL_TURRET_RANGE.get())));
                components.add(Component.translatable("k_turrets.fire.rate").append(": "+String.format("%.1f",(float)20/KTurrets.FIREBALL_TURRET_RATE.get())).append(" ").append(Component.translatable("k_turrets.shots.per.second")));
            }
        });
        BRICK_TURRET = ITEMS.register("brick_turret_item", () -> new ContainerItem(KTEntities.BRICK_TURRET, 0x0B00FF, 0xFF6C02, defaults(), ContainerItem.Unit.TURRET){
            @Override
            public void appendHoverText(ItemStack itemStack, @Nullable Level pLevel, List<Component> components, TooltipFlag tooltipFlag) {
                super.appendHoverText(itemStack, pLevel, components, tooltipFlag);
                components.add(Component.translatable("k_turrets.damage.info").append(": "+KTurrets.BRICK_DAMAGE.get()));
                components.add(Component.translatable("k_turrets.integrity").append(": "+String.format("%.1f",KTurrets.BRICK_TURRET_HEALTH.get())));
                components.add(Component.translatable("k_turrets.armor.info").append(": "+String.format("%.1f",KTurrets.BRICK_TURRET_ARMOR.get())));
                components.add(Component.translatable("k_turrets.range").append(": "+String.format("%.1f",KTurrets.BRICK_TURRET_RANGE.get())));
                components.add(Component.translatable("k_turrets.fire.rate").append(": "+String.format("%.1f",(float)20/KTurrets.BRICK_TURRET_RATE.get())).append(" ").append(Component.translatable("k_turrets.shots.per.second")));
            }
        });
        GAUSS_TURRET = ITEMS.register("gauss_turret_item", () -> new ContainerItem(KTEntities.GAUSS_TURRET, 0xA0A0A0, 0x505050, defaults(), ContainerItem.Unit.TURRET){
            @Override
            public void appendHoverText(ItemStack itemStack, @Nullable Level pLevel, List<Component> components, TooltipFlag tooltipFlag) {
                super.appendHoverText(itemStack, pLevel, components, tooltipFlag);
                components.add(Component.translatable("k_turrets.damage.info").append(": "+KTurrets.GAUSS_TURRET_DAMAGE.get()));
                components.add(Component.translatable("k_turrets.integrity").append(": "+String.format("%.1f",KTurrets.GAUSS_TURRET_HEALTH.get())));
                components.add(Component.translatable("k_turrets.armor.info").append(": "+String.format("%.1f",KTurrets.GAUSS_TURRET_ARMOR.get())));
                components.add(Component.translatable("k_turrets.range").append(": "+String.format("%.1f",KTurrets.GAUSS_TURRET_RANGE.get())));
                components.add(Component.translatable("k_turrets.fire.rate").append(": "+String.format("%.1f",(float)20/KTurrets.GAUSS_TURRET_RATE.get())).append(" ").append(Component.translatable("k_turrets.shots.per.second")));
            }
        });
        COBBLE_TURRET = ITEMS.register("cobble_turret_item", () -> new ContainerItem(KTEntities.COBBLE_TURRET, 0x46778b, 0x2d4c59, defaults(), ContainerItem.Unit.TURRET){
            @Override
            public void appendHoverText(ItemStack itemStack, @Nullable Level pLevel, List<Component> components, TooltipFlag tooltipFlag) {
                super.appendHoverText(itemStack, pLevel, components, tooltipFlag);
                components.add(Component.translatable("k_turrets.damage.info").append(": "+KTurrets.COBBLE_TURRET_DAMAGE.get()));
                components.add(Component.translatable("k_turrets.integrity").append(": "+String.format("%.1f",KTurrets.COBBLE_TURRET_HEALTH.get())));
                components.add(Component.translatable("k_turrets.armor.info").append(": "+String.format("%.1f",KTurrets.COBBLE_TURRET_ARMOR.get())));
                components.add(Component.translatable("k_turrets.range").append(": "+String.format("%.1f",KTurrets.COBBLE_TURRET_RANGE.get())));
                components.add(Component.translatable("k_turrets.fire.rate").append(": "+String.format("%.1f",(float) 20/KTurrets.COBBLE_TURRET_RATE.get())).append(" ").append(Component.translatable("k_turrets.shots.per.second")));
            }
        });

        BRICK_DRONE = ITEMS.register("brick_drone_item", () -> new ContainerItem(KTEntities.BRICK_DRONE, 0xFF6C02, 0x0B00FF, defaults(), ContainerItem.Unit.DRONE){
            @Override
            public void appendHoverText(ItemStack itemStack, @Nullable Level pLevel, List<Component> components, TooltipFlag tooltipFlag) {
                super.appendHoverText(itemStack, pLevel, components, tooltipFlag);
                components.add(Component.translatable("k_turrets.damage.info").append(": "+KTurrets.BRICK_DAMAGE.get()));
                components.add(Component.translatable("k_turrets.integrity").append(": "+String.format("%.1f",KTurrets.BRICK_TURRET_HEALTH.get()*0.83)));
                components.add(Component.translatable("k_turrets.armor.info").append(": "+String.format("%.1f",KTurrets.BRICK_TURRET_ARMOR.get()*0.34)));
                components.add(Component.translatable("k_turrets.range").append(": "+String.format("%.1f",KTurrets.BRICK_TURRET_RANGE.get())));
                components.add(Component.translatable("k_turrets.fire.rate").append(": "+String.format("%.1f",(float) 20/KTurrets.BRICK_TURRET_RATE.get())).append(" ").append(Component.translatable("k_turrets.shots.per.second")));
            }
        });
        BULLET_DRONE = ITEMS.register("bullet_drone_item", () -> new ContainerItem(KTEntities.BULLET_DRONE, 0x009EA2, 0xA2A1A0, defaults(), ContainerItem.Unit.DRONE){
            @Override
            public void appendHoverText(ItemStack itemStack, @Nullable Level pLevel, List<Component> components, TooltipFlag tooltipFlag) {
                super.appendHoverText(itemStack, pLevel, components, tooltipFlag);
                components.add(Component.translatable("k_turrets.damage.info").append(": "+KTurrets.IRON_BULLET_DAMAGE.get()));
                components.add(Component.translatable("k_turrets.integrity").append(": "+String.format("%.1f",KTurrets.BULLET_TURRET_HEALTH.get()*0.83)));
                components.add(Component.translatable("k_turrets.armor.info").append(": "+String.format("%.1f",KTurrets.BULLET_TURRET_ARMOR.get()*0.34)));
                components.add(Component.translatable("k_turrets.range").append(": "+String.format("%.1f",KTurrets.BULLET_TURRET_RANGE.get())));
                components.add(Component.translatable("k_turrets.fire.rate").append(": "+String.format("%.1f",(float) 20/KTurrets.BULLET_TURRET_RATE.get())).append(" ").append(Component.translatable("k_turrets.shots.per.second")));
            }
        });
        COBBLE_DRONE = ITEMS.register("cobble_drone_item", () -> new ContainerItem(KTEntities.COBBLE_DRONE, 0x2d4c59, 0x46778b, defaults(), ContainerItem.Unit.DRONE){
            @Override
            public void appendHoverText(ItemStack itemStack, @Nullable Level pLevel, List<Component> components, TooltipFlag tooltipFlag) {
                super.appendHoverText(itemStack, pLevel, components, tooltipFlag);
                components.add(Component.translatable("k_turrets.damage.info").append(": "+KTurrets.COBBLE_TURRET_DAMAGE.get()));
                components.add(Component.translatable("k_turrets.integrity").append(": "+String.format("%.1f",KTurrets.COBBLE_TURRET_HEALTH.get()*0.83)));
                components.add(Component.translatable("k_turrets.armor.info").append(": "+String.format("%.1f",KTurrets.COBBLE_TURRET_ARMOR.get()*0.34)));
                components.add(Component.translatable("k_turrets.range").append(": "+String.format("%.1f",KTurrets.COBBLE_TURRET_RANGE.get())));
                components.add(Component.translatable("k_turrets.fire.rate").append(": "+String.format("%.1f",(float) 20/KTurrets.COBBLE_TURRET_RATE.get())).append(" ").append(Component.translatable("k_turrets.shots.per.second")));
            }
        });
        ARROW_DRONE = ITEMS.register("arrow_drone_item", () -> new ContainerItem(KTEntities.ARROW_DRONE, 0xA2A009, 0x0CA207, defaults(), ContainerItem.Unit.DRONE){
            @Override
            public void appendHoverText(ItemStack itemStack, @Nullable Level pLevel, List<Component> components, TooltipFlag tooltipFlag) {
                super.appendHoverText(itemStack, pLevel, components, tooltipFlag);
                components.add(Component.translatable("k_turrets.damage.info").append(": "+KTurrets.ARROW_TURRET_DAMAGE.get()));
                components.add(Component.translatable("k_turrets.integrity").append(": "+String.format("%.1f",KTurrets.ARROW_TURRET_HEALTH.get()*0.83)));
                components.add(Component.translatable("k_turrets.armor.info").append(": "+String.format("%.1f",KTurrets.ARROW_TURRET_ARMOR.get()*0.34)));
                components.add(Component.translatable("k_turrets.range").append(": "+String.format("%.1f",KTurrets.ARROW_TURRET_RANGE.get())));
                components.add(Component.translatable("k_turrets.fire.rate").append(": "+String.format("%.1f",(float) 20/KTurrets.ARROW_TURRET_RATE.get())).append(" ").append(Component.translatable("k_turrets.shots.per.second")));
            }
        });
        GAUSS_DRONE = ITEMS.register("gauss_drone_item", () -> new ContainerItem(KTEntities.GAUSS_DRONE, 0x505050, 0xA0A0A0, defaults(), ContainerItem.Unit.DRONE){
            @Override
            public void appendHoverText(ItemStack itemStack, @Nullable Level pLevel, List<Component> components, TooltipFlag tooltipFlag) {
                super.appendHoverText(itemStack, pLevel, components, tooltipFlag);
                components.add(Component.translatable("k_turrets.damage.info").append(": "+KTurrets.GAUSS_TURRET_DAMAGE.get()));
                components.add(Component.translatable("k_turrets.integrity").append(": "+String.format("%.1f",KTurrets.GAUSS_TURRET_HEALTH.get()*0.83)));
                components.add(Component.translatable("k_turrets.armor.info").append(": "+String.format("%.1f",KTurrets.GAUSS_TURRET_ARMOR.get()*0.34)));
                components.add(Component.translatable("k_turrets.range").append(": "+String.format("%.1f",KTurrets.GAUSS_TURRET_RANGE.get())));
                components.add(Component.translatable("k_turrets.fire.rate").append(": "+String.format("%.1f",(float) 20/KTurrets.GAUSS_TURRET_RATE.get())).append(" ").append(Component.translatable("k_turrets.shots.per.second")));
            }
        });
        FIREBALL_DRONE = ITEMS.register("firecharge_drone_item", () -> new ContainerItem(KTEntities.FIRECHARGE_DRONE, 0xA20005, 0x0, defaults(), ContainerItem.Unit.DRONE){
            @Override
            public void appendHoverText(ItemStack itemStack, @Nullable Level pLevel, List<Component> components, TooltipFlag tooltipFlag) {
                super.appendHoverText(itemStack, pLevel, components, tooltipFlag);
                components.add(Component.translatable("k_turrets.damage.info").append(": "+KTurrets.FIREBALL_TURRET_DAMAGE.get()));
                components.add(Component.translatable("k_turrets.integrity").append(": "+String.format("%.1f",KTurrets.FIREBALL_TURRET_HEALTH.get()*0.83)));
                components.add(Component.translatable("k_turrets.armor.info").append(": "+String.format("%.1f",KTurrets.FIREBALL_TURRET_ARMOR.get()*0.34)));
                components.add(Component.translatable("k_turrets.range").append(": "+String.format("%.1f",KTurrets.FIREBALL_TURRET_RANGE.get())));
                components.add(Component.translatable("k_turrets.fire.rate").append(": "+String.format("%.1f",(float) 20/KTurrets.FIREBALL_TURRET_RATE.get())).append(" ").append(Component.translatable("k_turrets.shots.per.second")));
            }
        });

        STORAGE_DRONE = ITEMS.register("storage_drone_item", () -> new ContainerItem(KTEntities.STORAGE_DRONE, 0x000000, 0x000000, defaults(), ContainerItem.Unit.DRONE){
            @Override
            public void appendHoverText(ItemStack itemStack, @Nullable Level pLevel, List<Component> components, TooltipFlag tooltipFlag) {
                super.appendHoverText(itemStack, pLevel, components, tooltipFlag);
                components.add(Component.translatable("k_turrets.integrity").append(": "+KTurrets.STORAGE_DRONE_HEALTH.get()));
                components.add(Component.translatable("k_turrets.armor.info").append(": "+KTurrets.STORAGE_DRONE_ARMOR.get()));
            }
        });

        GAUSS_BULLET = ITEMS.register("gauss_bullet", () -> new Item(defaults()));
        EXPLOSIVE_POWDER = ITEMS.register("explosive_powder", () -> new Item(defaults()));
        TITANIUM_ORE = ITEMS.register("titanium_ore", () -> new BlockItem(KTBlocks.TITANIUM_ORE.get(), defaults()));
        DEEPSLATE_TITANIUM_ORE = ITEMS.register("deepslate_titanium_ore", () -> new BlockItem(KTBlocks.DEEP_SLATE_TITANIUM_ORE.get(), defaults()));
        RAW_TITANIUM = ITEMS.register("raw_titanium", () -> new Item(defaults()));
        TITANIUM_INGOT = ITEMS.register("titanium_ingot", () -> new Item(defaults()));
        TARGET_COPIER = ITEMS.register("wrench", () -> new TargetCopier(defaults().stacksTo(1)));
        RELOADER = ITEMS.register("reloader", () -> new BlockItem(KTBlocks.RELOADER.get(), defaults()){
            @Override
            public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
                pTooltip.add(Component.translatable("k_turrets.reloads.drones"));
            }
        });
        LIGHT_UPGRADE=ITEMS.register("light_upgrade",() -> new Item(new Item.Properties().stacksTo(4)));
        MAGNET_UPGRADE=ITEMS.register("magnet_upgrade",() -> new Magnet(new Item.Properties().stacksTo(1)));
        RECALL_UPGRADE=ITEMS.register("recall_upgrade",() -> new Item(defaults().stacksTo(4)){
            @Override
            public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                pTooltipComponents.add(Component.translatable("k_turrets.recall.description"));
            }
        });
        EXP_LINK=ITEMS.register("exp_link",() -> new Item(defaults().stacksTo(4)){
            @Override
            public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                pTooltipComponents.add(Component.translatable("k_turrets.exp.link.info"));
            }
        });
        FIRE_SHIELD =ITEMS.register("fire_shield",() -> new Item(defaults().stacksTo(4))
        {
            @Override
            public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                pTooltipComponents.add(Component.translatable("k_turrets.fire.shield.info"));
            }
        });
        LOOTING_LINK=ITEMS.register("looting_link",() -> new Item(defaults().stacksTo(4)){
            @Override
            public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                pTooltipComponents.add(Component.translatable("k_turrets.looting.link.info"));
            }
        });
        COPPER_PLATE=ITEMS.register("copper_plate",() -> new Item(defaults()));
        BULLET=ITEMS.register("bullet",() -> new Item(defaults()));
    }

    private static Item.Properties defaults() {
        return new Item.Properties();
    }
}
