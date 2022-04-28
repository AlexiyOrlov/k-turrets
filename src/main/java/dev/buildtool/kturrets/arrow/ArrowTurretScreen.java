package dev.buildtool.kturrets.arrow;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ArrowTurretScreen extends ContainerScreen2<ArrowTurretContainer> {
    public ArrowTurretScreen(ArrowTurretContainer container, Inventory playerInventory, Component name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
