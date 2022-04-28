package dev.buildtool.kturrets.cobble;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CobbleTurretScreen extends ContainerScreen2<CobbleTurretContainer> {
    public CobbleTurretScreen(CobbleTurretContainer container, Inventory playerInventory, Component name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
