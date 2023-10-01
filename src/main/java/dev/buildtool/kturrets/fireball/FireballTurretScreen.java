package dev.buildtool.kturrets.fireball;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class FireballTurretScreen extends ContainerScreen2<FireballTurretContainer> {
    public FireballTurretScreen(FireballTurretContainer container, Inventory playerInventory, Component name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
