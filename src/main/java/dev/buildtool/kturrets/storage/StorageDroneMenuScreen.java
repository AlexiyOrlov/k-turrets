package dev.buildtool.kturrets.storage;

import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.packets.CompressItems;
import dev.buildtool.satako.gui.BetterButton;
import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class StorageDroneMenuScreen extends ContainerScreen2<StorageDroneMenu> {
    public StorageDroneMenuScreen(StorageDroneMenu container, Inventory playerInventory, Component name) {
        super(container, playerInventory, name, true);
    }

    @Override
    public void init() {
        super.init();
        Component compress=Component.translatable("k_turrets.compress.items");
        StorageDroneMenu droneMenu=menu;
        addRenderableWidget(new BetterButton(getGuiLeft()-font.width(compress)-20,centerY-10,compress,pButton -> KTurrets.channel.sendToServer(new CompressItems(droneMenu.storageDrone.getId()))));
    }
}
