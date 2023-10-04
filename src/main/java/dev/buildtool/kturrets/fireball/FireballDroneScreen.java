package dev.buildtool.kturrets.fireball;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class FireballDroneScreen extends ContainerScreen2<FireballDroneContainer> {
    public FireballDroneScreen(FireballDroneContainer container, PlayerInventory playerInventory, ITextComponent name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
