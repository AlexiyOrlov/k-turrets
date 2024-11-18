package dev.buildtool.kturrets.storage;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class StorageDroneMenuScreen extends ContainerScreen2<StorageDroneMenu> {
    public StorageDroneMenuScreen(StorageDroneMenu container, Inventory playerInventory, Component name) {
        super(container, playerInventory, name, true);
    }
}
