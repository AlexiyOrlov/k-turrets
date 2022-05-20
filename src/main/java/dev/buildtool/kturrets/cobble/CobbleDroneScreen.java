package dev.buildtool.kturrets.cobble;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CobbleDroneScreen extends ContainerScreen2<CobbleDroneContainer> {
    public CobbleDroneScreen(CobbleDroneContainer container, Inventory playerInventory, Component name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
