package dev.buildtool.kturrets.gauss;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GaussTurretScreen extends ContainerScreen2<GaussTurretContainer> {
    public GaussTurretScreen(GaussTurretContainer container, Inventory playerInventory, Component name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
