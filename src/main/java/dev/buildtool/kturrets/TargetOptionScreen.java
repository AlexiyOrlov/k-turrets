package dev.buildtool.kturrets;

import dev.buildtool.satako.gui.*;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.HashMap;

public class TargetOptionScreen extends Screen2 {
    protected Turret turret;
    protected HashMap<EntityType<?>, Boolean> tempStatusMap;

    public TargetOptionScreen(Turret turret) {
        super(new TranslationTextComponent("k-turrets.targets"));
        this.turret = turret;
        tempStatusMap = new HashMap<>(turret.targets.size());
        turret.targets.forEach(entityType -> tempStatusMap.put(entityType, true));
    }

    @Override
    public void init() {
        super.init();
        TextField addEntityField = addButton(new TextField(centerX, 3, 100));
        addButton(new BetterButton(centerX, 20, new TranslationTextComponent("k-turrets.add.entity.type"), p_onPress_1_ -> {
            String entityType = addEntityField.getValue();
            EntityType.byString(entityType).ifPresent(entityType1 -> {
                turret.targets.add(entityType1);
                tempStatusMap.put(entityType1, true);
                minecraft.player.sendMessage(new TranslationTextComponent("k-turrets.added"), Util.NIL_UUID);
            });
        }));
        addButton(new Label(centerX, 40, new TranslationTextComponent("k-turrets.choose.tooltip")));
        addButton(new Label(centerX, 60, new TranslationTextComponent("k-turrets.hold.alt.to.scroll")));

        addButton(new Label(3, 3, new TranslationTextComponent("k-turrets.targets")));
        for (int i = 0; i < turret.targets.size(); i++) {
            EntityType<?> entityType = turret.targets.get(i);
            SwitchButton switchButton = new SwitchButton(3, 20 * i + 40, new StringTextComponent(entityType.getRegistryName().toString()), new StringTextComponent(TextFormatting.STRIKETHROUGH + entityType.getRegistryName().toString()), true, p_onPress_1_ -> {
                if (p_onPress_1_ instanceof SwitchButton) {
                    ((SwitchButton) p_onPress_1_).state = !((SwitchButton) p_onPress_1_).state;
                    tempStatusMap.put(entityType, ((SwitchButton) p_onPress_1_).state);
                }
            });
            switchButton.verticalScroll = true;
            addButton(switchButton);
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        //send
        tempStatusMap.forEach((entityType, aBoolean) -> {
            if (aBoolean)
                turret.targets.add(entityType);
            else
                turret.targets.remove(entityType);
        });
    }
}
