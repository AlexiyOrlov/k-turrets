package dev.buildtool.kturrets.arrow;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ArrowDroneScreen extends ContainerScreen2<ArrowDroneContainer> {
    public ArrowDroneScreen(ArrowDroneContainer container, PlayerInventory playerInventory, ITextComponent name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
