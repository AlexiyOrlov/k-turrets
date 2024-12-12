package dev.buildtool.kturrets;

import dev.buildtool.satako.clientside.gui.ContainerScreen2;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ReloaderScreen extends ContainerScreen2<ReloaderMenu> {
    public ReloaderScreen(ReloaderMenu container, Inventory playerInventory, Component name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
