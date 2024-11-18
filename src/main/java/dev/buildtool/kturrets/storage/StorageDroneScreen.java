package dev.buildtool.kturrets.storage;

import dev.buildtool.kturrets.Drone;
import dev.buildtool.kturrets.KTurrets;
import dev.buildtool.kturrets.packets.*;
import dev.buildtool.satako.gui.*;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

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
        addRenderableWidget(new BetterButton(centerX, 20, Component.translatable("k_turrets.dismantle"), p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new DismantleTurret(drone.getId()));
            minecraft.player.closeContainer();
        }));
        addRenderableWidget(new SwitchButton(centerX, 40, Component.translatable("k_turrets.mobile"), Component.translatable("k_turrets.immobile"), drone.isMoveable(), p_onPress_1_ -> {
            KTurrets.channel.sendToServer(new ToggleMobility(!drone.isMoveable(), drone.getId()));
            drone.setMoveable(!drone.isMoveable());
            if (p_onPress_1_ instanceof SwitchButton) {
                ((SwitchButton) p_onPress_1_).state = !((SwitchButton) p_onPress_1_).state;
            }
        }));
        if (!drone.getOwner().isPresent())
            addRenderableWidget(new BetterButton(centerX, 60, Component.translatable("k_turrets.claim.drone"), p_onPress_1_ -> {
                KTurrets.channel.sendToServer(new ClaimTurret(drone.getId(), minecraft.player.getUUID()));
                drone.setOwner(minecraft.player.getUUID());
                minecraft.player.closeContainer();
            }));
        else {
            DropDownButton dropDownButton = new DropDownButton(centerX, 60, this, Component.literal(""));
            LinkedHashMap<Component, Button.OnPress> linkedHashMap = new LinkedHashMap<>(3);
            RadioButton follow = new RadioButton(centerX, 140, Component.translatable("k_turrets.following.owner"));
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
