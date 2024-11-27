package dev.buildtool.kturrets.storage;

import dev.buildtool.kturrets.Drone;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.packets.*;
import dev.buildtool.kturrets.registers.KItems;
import dev.buildtool.satako.gui.*;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.LinkedHashMap;

public class StorageDroneScreen extends Screen2 {
    private final StorageDrone drone;
    public StorageDroneScreen(Drone drone) {
        super(Component.translatable("k_turrets.storage.drone"));
        this.drone = (StorageDrone) drone;
    }

    @Override
    public void init() {
        super.init();
        MutableComponent dismantle = Component.translatable("k_turrets.dismantle");
        addRenderableWidget(new BetterButton(centerX-font.width(dismantle.getString())/2, centerY-40, dismantle, p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new DismantleTurret(drone.getId()));
            minecraft.player.closeContainer();
        }));
        MutableComponent switchB = Component.translatable("k_turrets.immobile");
        addRenderableWidget(new SwitchButton(centerX-font.width(switchB.getString())/2, centerY-20, Component.translatable("k_turrets.mobile"), switchB, drone.isMoveable(), p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new ToggleMobility(!drone.isMoveable(), drone.getId()));
            drone.setMoveable(!drone.isMoveable());
            if (p_onPress_1_ instanceof SwitchButton) {
                ((SwitchButton) p_onPress_1_).state = !((SwitchButton) p_onPress_1_).state;
            }
        }));
        if (!drone.getOwner().isPresent()) {
            MutableComponent claim = Component.translatable("k_turrets.claim.drone");
            addRenderableWidget(new BetterButton(centerX-font.width(claim)/2, centerY+20, claim, p_onPress_1_ -> {
                KTurrets.channel.sendToServer(new ClaimTurret(drone.getId(), minecraft.player.getUUID()));
                drone.setOwner(minecraft.player.getUUID());
                minecraft.player.closeContainer();
            }));
        } else {
            MutableComponent follow=Component.translatable("k_turrets.following.owner");
            SwitchButton toggle=new SwitchButton(centerX-font.width(follow)/2,centerY,follow,Component.translatable("k_turrets.staying"),drone.getBehavior()== Drone.Behavior.FOLLOW, pButton -> {
               SwitchButton switchButton= (SwitchButton) pButton;
               switchButton.state=!switchButton.state;
               if(switchButton.state)
               {
                   drone.setBehavior(Drone.Behavior.FOLLOW);
                   KTurrets.channel.sendToServer(new SetBehavior(drone.getId(), Drone.Behavior.FOLLOW));
               }
               else {
                   drone.setBehavior(Drone.Behavior.STAY);
                   KTurrets.channel.sendToServer(new SetBehavior(drone.getId(), Drone.Behavior.STAY));
               }

            });
            addRenderableWidget(toggle);
        }
        if(drone.upgrades.getStackInSlot(2).is(KItems.MAGNET_UPGRADE.get()))
        {
            MutableComponent magnetOff=Component.translatable("k_turrets.magnetOff.off");
            addRenderableWidget(new SwitchButton(centerX-font.width(magnetOff.getString())/2,centerY+20,Component.translatable("k_turrets.magnetOff.on"),magnetOff,drone.isMagnetActive(),pButton -> {
                SwitchButton switchButton= (SwitchButton) pButton;
                switchButton.state=!switchButton.state;
                drone.setMagnetActive(switchButton.state);
                KTurrets.channel.sendToServer(new SetMagnetState(drone.getId(),switchButton.state));
            }));
        };
    }
}
