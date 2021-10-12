package dev.buildtool.kturrets.brick;

import dev.buildtool.kturrets.registers.TContainers;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class BrickTurretContainer extends Container2 {
    public BrickTurretContainer(int i, PlayerInventory playerInventory, PacketBuffer buffer) {
        super(TContainers.BRICK_TURRET, i);
        BrickTurret turret = (BrickTurret) playerInventory.player.level.getEntity(buffer.readInt());
        int index = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(turret.bricks, index++, k * 18, j * 18));
            }
        }

        addPlayerInventory(0, 4 * 18, playerInventory);
    }
}