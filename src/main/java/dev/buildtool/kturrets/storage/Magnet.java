package dev.buildtool.kturrets.storage;

import dev.buildtool.kturrets.KTurrets;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Magnet extends Item implements MenuProvider {
    public Magnet(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("k_turrets.magnet");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new MagnetMenu(pContainerId,pPlayerInventory,new FriendlyByteBuf(Unpooled.buffer()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemInHand = pPlayer.getItemInHand(pUsedHand);
        if(pPlayer instanceof ServerPlayer serverPlayer)
        {
            NetworkHooks.openScreen(serverPlayer, (MenuProvider) itemInHand.getItem(),byteBuf -> {});
        }
        return InteractionResultHolder.success(itemInHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("k_turrets.mode").append(": ").append(pStack.getOrCreateTag().getBoolean(KTurrets.FILTER)?Component.translatable("k_turrets.whitelist.items"):Component.translatable("k_turrets.blacklist.items")));
        pTooltipComponents.add(Component.translatable("k_turrets.right.click"));
    }
}
