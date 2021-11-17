package dev.buildtool.kturrets.cobble;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class CobbleTurretScreen extends ContainerScreen2<CobbleTurretContainer> {
    public CobbleTurretScreen(CobbleTurretContainer container, PlayerInventory playerInventory, ITextComponent name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
