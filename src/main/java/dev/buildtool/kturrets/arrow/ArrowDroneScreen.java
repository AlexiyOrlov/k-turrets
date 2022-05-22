package dev.buildtool.kturrets.arrow;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ArrowDroneScreen extends ContainerScreen2<ArrowDroneContainer> {
    public ArrowDroneScreen(ArrowDroneContainer container, Inventory playerInventory, Component name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
