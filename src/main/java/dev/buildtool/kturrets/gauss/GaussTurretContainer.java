package dev.buildtool.kturrets.gauss;

import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nullable;

public class GaussTurretContainer extends Container2 {
    public GaussTurretContainer(@Nullable ContainerType<?> type, int i, PlayerInventory inventory, PacketBuffer buffer) {
        super(type, i);
        GaussTurret turret = (GaussTurret) inventory.player.level.getEntity(buffer.readInt());
        int ind = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(turret.ammo, ind++, k * 18, j * 18));
            }
        }

        addPlayerInventory(0, 4 * 18, inventory);
    }
}
