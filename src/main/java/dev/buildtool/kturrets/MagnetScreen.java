package dev.buildtool.kturrets;

import dev.buildtool.kturrets.packets.MagnetFilterState;
import dev.buildtool.kturrets.registers.KItems;
import dev.buildtool.satako.gui.ContainerScreen2;
import dev.buildtool.satako.gui.SwitchButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class MagnetScreen extends ContainerScreen2<MagnetMenu> {
    ItemStack magnet;
    public MagnetScreen(MagnetMenu container, Inventory playerInventory, Component name) {
        super(container, playerInventory, name, true);
        magnet=playerInventory.getSelected();
    }

    @Override
    public void init() {
        super.init();
        MutableComponent whitelist=Component.translatable("k_turrets.whitelist");
        addRenderableWidget(new SwitchButton(centerX-font.width(whitelist)/2-5,topPos+getSlots().get(25).y+20,whitelist,Component.translatable("k_turrets.blacklist"),magnet.getOrCreateTag().getBoolean(KTurrets.FILTER),pButton -> {
            SwitchButton switchButton= (SwitchButton) pButton;
            switchButton.state=!switchButton.state;
            magnet.getTag().putBoolean(KTurrets.FILTER,switchButton.state);
            KTurrets.channel.sendToServer(new MagnetFilterState(switchButton.state));
        }));
    }
}
