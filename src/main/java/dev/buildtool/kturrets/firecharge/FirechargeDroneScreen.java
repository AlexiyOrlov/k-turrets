package dev.buildtool.kturrets.firecharge;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class FirechargeDroneScreen extends ContainerScreen2<FirechargeDroneContainer> {
    public FirechargeDroneScreen(FirechargeDroneContainer container, Inventory playerInventory, Component name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
