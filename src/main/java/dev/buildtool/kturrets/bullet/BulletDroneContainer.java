package dev.buildtool.kturrets.bullet;

import dev.buildtool.kturrets.registers.TContainers;
import dev.buildtool.satako.Container2;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class BulletDroneContainer extends Container2 {
    public BulletDroneContainer(int i, Inventory playerInventory, FriendlyByteBuf buffer) {
        super(TContainers.BULLET_DRONE.get(), i);
    }
}
