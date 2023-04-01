package dev.buildtool.kturrets.firecharge;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class FireballDroneScreen extends ContainerScreen2<FireballDroneContainer> {
    public FireballDroneScreen(FireballDroneContainer container, Inventory playerInventory, Component name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
