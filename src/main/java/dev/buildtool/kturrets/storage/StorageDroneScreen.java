package dev.buildtool.kturrets.storage;

import dev.buildtool.kturrets.Drone;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.packets.*;
import dev.buildtool.satako.gui.*;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.LinkedHashMap;

public class StorageDroneScreen extends Screen2 {
    private Drone drone;
    public StorageDroneScreen(Drone drone) {
        super(Component.translatable("k_turrets.storage.drone"));
        this.drone = drone;
    }

    @Override
    public void init() {
        super.init();
        MutableComponent dismantle = Component.translatable("k_turrets.dismantle");
        addRenderableWidget(new BetterButton(centerX-font.width(dismantle.getString())/2, 20, dismantle, p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new DismantleTurret(drone.getId()));
            minecraft.player.closeContainer();
        }));
        MutableComponent switchB = Component.translatable("k_turrets.immobile");
        addRenderableWidget(new SwitchButton(centerX-font.width(switchB.getString())/2, 40, Component.translatable("k_turrets.mobile"), switchB, drone.isMoveable(), p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new ToggleMobility(!drone.isMoveable(), drone.getId()));
            drone.setMoveable(!drone.isMoveable());
            if (p_onPress_1_ instanceof SwitchButton) {
                ((SwitchButton) p_onPress_1_).state = !((SwitchButton) p_onPress_1_).state;
            }
        }));
        if (!drone.getOwner().isPresent()) {
            MutableComponent claim = Component.translatable("k_turrets.claim.drone");
            addRenderableWidget(new BetterButton(centerX-font.width(claim)/2, 60, claim, p_onPress_1_ -> {
                KTurrets.channel.sendToServer(new ClaimTurret(drone.getId(), minecraft.player.getUUID()));
                drone.setOwner(minecraft.player.getUUID());
                minecraft.player.closeContainer();
            }));
        } else {
            MutableComponent followText = Component.translatable("k_turrets.following.owner");
            DropDownButton dropDownButton = new DropDownButton(centerX-font.width(followText)/2, 60, this, Component.literal(""));
            LinkedHashMap<Component, Button.OnPress> linkedHashMap = new LinkedHashMap<>(3);
            RadioButton follow = new RadioButton(centerX, 140, followText);
            linkedHashMap.put(follow.getMessage(), p_93751_ -> {
                KTurrets.channel.sendToServer(new ToggleDroneFollow(true, drone.getId()));
                drone.followOwner(true);
                drone.setGuardArea(false);
                KTurrets.channel.sendToServer(new ToggleGuardingArea(drone.getId(), false));
                dropDownButton.setMessage(p_93751_.getMessage());
                dropDownButton.onPress();
            });
            RadioButton stay = new RadioButton(centerX, 160, Component.translatable("k_turrets.staying"));
            linkedHashMap.put(stay.getMessage(), p_93751_ -> {
                KTurrets.channel.sendToServer(new ToggleDroneFollow(false, drone.getId()));
                KTurrets.channel.sendToServer(new ToggleGuardingArea(drone.getId(), false));
                drone.followOwner(false);
                drone.setGuardArea(false);
                dropDownButton.setMessage(p_93751_.getMessage());
                dropDownButton.onPress();
            });
            dropDownButton.setChoices(linkedHashMap, drone.isGuardingArea() ? 2 : drone.isFollowingOwner() ? 0 : 1);
            addRenderableWidget(dropDownButton);
        }
    }
}
