package dev.buildtool.kturrets.bullet;

import dev.buildtool.satako.clientside.gui.ContainerScreen2;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class BulletTurretScreen extends ContainerScreen2<BulletTurretContainer> {
    public BulletTurretScreen(BulletTurretContainer container, Inventory playerInventory, Component name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
