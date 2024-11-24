package dev.buildtool.kturrets;

import dev.buildtool.kturrets.packets.SetTarget;
import dev.buildtool.satako.IntegerColor;
import dev.buildtool.satako.UniqueList;
import dev.buildtool.satako.gui.BetterButton;
import dev.buildtool.satako.gui.Label;
import dev.buildtool.satako.gui.Screen2;
import dev.buildtool.satako.gui.SwitchButton;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.*;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractButtonListScreen;
import dev.ftb.mods.ftblibrary.util.client.PositionedIngredient;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class UnitOptionsScreen extends ButtonListScreen {
    protected Turret turret;
    private final List<Label> suggestions=new ArrayList<>(14);
    private UniqueList<EntityType<?>> targets;
    private Panel rightPanel;
    public ScreenWrapper wrapper;
    private ArrayList<BetterButton> hideableWidgets=new ArrayList<>();
    public UnitOptionsScreen(Turret turret) {
        this.turret=turret;
        targets=new UniqueList<>(Turret.decodeTargets(turret.getTargets()));
        showBottomPanel(false);
        initGui();
        setFullscreen();
        setTitle(Component.translatable("k_turrets.targets"));
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
        targets.stream().map(entityType -> Component.literal(ForgeRegistries.ENTITY_TYPES.getKey(entityType).toString())).map(entityName -> new TextButton(var1, entityName, true)).forEach(var1::add);
    }

    @Override
    public void addWidgets() {
        super.addWidgets();
        rightPanel=new Panel(this) {
            private BetterButton addButton;
            private dev.buildtool.satako.gui.TextField addEntity;

            @Override
            public void addWidgets() {
                addEntity = new dev.buildtool.satako.gui.TextField(mainPanel.width, 10,Component.empty(),width-mainPanel.width){
                    @Override
                    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
                        suggestions.forEach(wrapper::removeWidget);
                        suggestions.clear();
                        String text = addEntity.getValue();
                        if (!text.isEmpty()) {
                            List<ResourceLocation> entityTypes;
                            entityTypes = new ArrayList<>(ForgeRegistries.ENTITY_TYPES.getKeys().stream().filter(resourceLocation -> resourceLocation.toString().contains(text)).toList());
                            int yOffset = 20;
                            entityTypes.removeAll(targets.stream().map(ForgeRegistries.ENTITY_TYPES::getKey).toList());
                            for (ResourceLocation entityType : entityTypes.subList(0, Math.min(entityTypes.size(), 14))) {
                                Label hint = new Label(addEntity.getX(), addEntity.getY() + yOffset, Component.literal(ChatFormatting.YELLOW + entityType.toString()), wrapper, p_93751_ -> {
                                    addEntity.setValue(p_93751_.getMessage().getString().substring(2));
                                    suggestions.forEach(wrapper::removeWidget);
                                    suggestions.clear();
//                                    showButtonsAndHints();
                                },new IntegerColor(0xff000000));
                                wrapper.addRenderableWidget(hint);
                                suggestions.add(hint);
                                yOffset += 14;
                            }
                            if (!entityTypes.isEmpty()) {
//                                this.addTarget.setHidden(true);
//                                if (claimTurret != null)
//                                    this.claimTurret.setHidden(true);
//                                this.clearTargets.setHidden(true);
//                                this.dismantle.setHidden(true);
//                                this.mobilitySwitch.setHidden(true);
//                                this.protectionFromPlayers.setHidden(true);
//                                this.resetList.setHidden(true);
//                                if (dropDownButton != null)
//                                    dropDownButton.setHidden(true);
//                                inventoryRefillSwitch.setHidden(true);
                                hideableWidgets.forEach(betterButton -> betterButton.setHidden(true));
                            } else {
//                                showButtonsAndHints();
                                hideableWidgets.forEach(betterButton -> betterButton.setHidden(false));
                            }
                        } else {
//                            showButtonsAndHints();
                            hideableWidgets.forEach(betterButton -> betterButton.setHidden(false));
                        }
                        return true;
                    }


                };
                wrapper.addRenderableWidget(addEntity);
                addButton = new BetterButton(addEntity.getX(), addEntity.getY() + addEntity.getHeight(), Component.translatable("k_turrets.add.entity.type"), button -> {
                    String string = addEntity.getValue();
                    EntityType<?> entityTypesValue = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(string));
                    targets.add(entityTypesValue);
                    KTurrets.channel.sendToServer(new SetTarget(true,string,turret.getId()));
                    addEntity.setValue("");
                    mainPanel.refreshWidgets();

                });
                wrapper.addRenderableWidget(addButton);
            }

            @Override
            public void alignWidgets() {

            }
        };
        rightPanel.setPosAndSize(mainPanel.width+90,10,width- mainPanel.width,height);
        add(rightPanel);
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
            setIcon(state ? Icons.CHECK : Icons.CLOSE);
            playClickSound();
            List<EntityType<?>> targets=Turret.decodeTargets(turret.getTargets());
            if(state)
            {
                targets.add(ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(title.getString())));
                turret.setTargets(Turret.encodeTargets(targets));
            }
            KTurrets.channel.sendToServer(new SetTarget(state, title.getString(), UnitOptionsScreen.this.turret.getId()));
        }
    }

    private class Suggestion extends TextField {
        TextBox target;
        String text;
        public Suggestion(TextBox textBox) {
            super(UnitOptionsScreen.this);
            target=textBox;
        }

        @Override
        public boolean mousePressed(MouseButton button) {
            target.setText(text.substring(2));
            suggestions.forEach(textField -> wrapper.removeWidget(textField));
            suggestions.clear();
            return super.mousePressed(button);
        }

        @Override
        public TextField setText(String txt) {
            text=txt;
            return super.setText(txt);
        }
    }
}
