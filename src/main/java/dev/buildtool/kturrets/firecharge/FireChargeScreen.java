package dev.buildtool.kturrets.firecharge;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class FireChargeScreen extends ContainerScreen2<FireChargeTurretContainer> {
    public FireChargeScreen(FireChargeTurretContainer container, PlayerInventory playerInventory, ITextComponent name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
