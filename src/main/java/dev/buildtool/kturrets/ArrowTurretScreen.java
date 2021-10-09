package dev.buildtool.kturrets;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ArrowTurretScreen extends ContainerScreen2<ArrowTurretContainer> {
    public ArrowTurretScreen(ArrowTurretContainer container, PlayerInventory playerInventory, ITextComponent name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
