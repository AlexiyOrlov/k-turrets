package dev.buildtool.kturrets.brick;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class BrickTurretScreen extends ContainerScreen2<BrickTurretContainer> {
    public BrickTurretScreen(BrickTurretContainer container, Inventory playerInventory, Component name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
