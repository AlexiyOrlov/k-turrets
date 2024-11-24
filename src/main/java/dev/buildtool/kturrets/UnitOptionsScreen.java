package dev.buildtool.kturrets;

import dev.buildtool.kturrets.packets.SetTarget;
import dev.buildtool.satako.UniqueList;
import dev.buildtool.satako.gui.Screen2;
import dev.buildtool.satako.gui.SwitchButton;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleButton;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractButtonListScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Comparator;
import java.util.List;

public class UnitOptionsScreen extends ButtonListScreen {
    protected Turret turret;
    private UniqueList<EntityType<?>> targets;
    public UnitOptionsScreen(Turret turret) {
        this.turret=turret;
        showBottomPanel(false);
        targets=new UniqueList<>(Turret.decodeTargets(turret.getTargets()));
    }

    @Override
    protected void doCancel() {

    }

    @Override
    protected void doAccept() {

    }

    @Override
    public void addButtons(Panel var1) {
        targets.sort(Comparator.comparing(o -> ForgeRegistries.ENTITY_TYPES.getKey(o).toString()));
        for (int i = 0; i < targets.size(); i++) {
            EntityType<?> entityType = targets.get(i);
            MutableComponent entityName = Component.literal(ForgeRegistries.ENTITY_TYPES.getKey(entityType).toString());
            TextButton simpleButton= new TextButton(var1, entityName,true);
            var1.add(simpleButton);
        }
    }

    private class TextButton extends SimpleTextButton {
        boolean state;

        public TextButton(Panel var1, MutableComponent entityName,boolean state) {
            super(var1, entityName, Icons.CHECK);
            this.state=state;
        }

        @Override
        public void onClicked(MouseButton mouseButton) {
            state=!state;
            setIcon(state ? Icons.CHECK : Icons.CANCEL);
            playClickSound();
            KTurrets.channel.sendToServer(new SetTarget(state, title.getString(), UnitOptionsScreen.this.turret.getId()));
        }
    }
}
