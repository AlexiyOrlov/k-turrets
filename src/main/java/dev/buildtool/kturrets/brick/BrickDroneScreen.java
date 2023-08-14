package dev.buildtool.kturrets.brick;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class BrickDroneScreen extends ContainerScreen2<BrickDroneContainer> {
    public BrickDroneScreen(BrickDroneContainer container, PlayerInventory playerInventory, ITextComponent name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
