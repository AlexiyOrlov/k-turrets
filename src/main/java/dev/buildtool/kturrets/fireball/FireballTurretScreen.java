package dev.buildtool.kturrets.fireball;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class FireballTurretScreen extends ContainerScreen2<FireballTurretContainer> {
    public FireballTurretScreen(FireballTurretContainer container, PlayerInventory playerInventory, ITextComponent name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
