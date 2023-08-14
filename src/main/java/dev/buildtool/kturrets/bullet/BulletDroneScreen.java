package dev.buildtool.kturrets.bullet;

import dev.buildtool.satako.gui.ContainerScreen2;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class BulletDroneScreen extends ContainerScreen2<BulletDroneContainer> {
    public BulletDroneScreen(BulletDroneContainer container, PlayerInventory playerInventory, ITextComponent name, boolean drawBorders_) {
        super(container, playerInventory, name, drawBorders_);
    }
}
