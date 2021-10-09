package dev.buildtool.kturrets.bullet;

import dev.buildtool.kturrets.registers.TContainers;
import dev.buildtool.satako.Container2;
import dev.buildtool.satako.ItemHandlerSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class BulletTurretContainer extends Container2 {
    public BulletTurretContainer(int i, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(TContainers.BULLET_TURRET, i);
        BulletTurret bulletTurret = (BulletTurret) playerInventory.player.level.getEntity(packetBuffer.readInt());
        int slot = 0;
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new ItemHandlerSlot(bulletTurret.ammo, slot++, k * 18, j * 18 + 18));
            }
        }
        addPlayerInventory(0, 5 * 18, playerInventory);
    }
}
