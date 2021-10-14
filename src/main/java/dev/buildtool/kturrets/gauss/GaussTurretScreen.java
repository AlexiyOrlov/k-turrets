package dev.buildtool.kturrets.gauss;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GaussTurretScreen extends ContainerScreen2<GaussTurretContainer> {
    public GaussTurretScreen(GaussTurretContainer container, PlayerInventory playerInventory, ITextComponent name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
