package dev.buildtool.kturrets.firecharge;

import dev.buildtool.kturrets.registers.TContainers;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class FireChargeTurretContainer extends Container2 {
    public FireChargeTurretContainer(int i, PlayerInventory playerInventory, PacketBuffer buffer) {
        super(TContainers.FIRE_CHARGE_TURRET, i);
        FireChargeTurret turret = (FireChargeTurret) playerInventory.player.level.getEntity(buffer.readInt());
        int index = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(turret.ammo, index++, k * 18, j * 18));
            }
        }

        addPlayerInventory(0, 4 * 18, playerInventory);
    }
}