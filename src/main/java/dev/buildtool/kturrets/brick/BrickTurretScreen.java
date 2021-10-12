package dev.buildtool.kturrets.brick;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class BrickTurretScreen extends ContainerScreen2<BrickTurretContainer> {
    public BrickTurretScreen(BrickTurretContainer container, PlayerInventory playerInventory, ITextComponent name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
