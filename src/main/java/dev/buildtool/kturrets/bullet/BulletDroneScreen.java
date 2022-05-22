package dev.buildtool.kturrets.bullet;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class BulletDroneScreen extends ContainerScreen2<BulletDroneContainer> {
    public BulletDroneScreen(BulletDroneContainer container, Inventory playerInventory, Component name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
