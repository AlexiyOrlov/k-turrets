package dev.buildtool.kturrets.bullet;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class BulletScreen extends ContainerScreen2<BulletTurretContainer> {
    public BulletScreen(BulletTurretContainer container, Inventory playerInventory, Component name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
