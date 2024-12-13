package dev.buildtool.kturrets;

import com.mojang.datafixers.util.Pair;
import dev.ftb.mods.ftblibrary.ui.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

public abstract class ButtonListScreen extends ThreePanelScreen{
    private Component title = Component.empty();
    private final TextBox searchBox;
    private final TextField titleField;
    private boolean hasSearchBox;
    private int borderH;
    private int borderV;
    private int borderW;
    private static final int SCROLLBAR_WIDTH = 16;
    private static final int GUTTER_SIZE = 5;

    public ButtonListScreen() {
        this.scrollBar.setCanAlwaysScroll(true);
        this.scrollBar.setScrollStep(20.0);
        this.titleField = new TextField(this.topPanel);
        this.searchBox = new TextBox(this.topPanel) {
            public void onTextChanged() {
                ButtonListScreen.this.mainPanel.refreshWidgets();
            }
        };
        this.searchBox.ghostText = I18n.get("gui.search_box", new Object[0]);
        this.hasSearchBox = false;
    }

    public void setHasSearchBox(boolean newVal) {
        if (this.hasSearchBox != newVal) {
            this.hasSearchBox = newVal;
            this.refreshWidgets();
        }

    }

    public String getFilterText(Widget widget) {
        return widget.getTitle().getString().toLowerCase();
    }

    protected int getTopPanelHeight() {
        return this.hasSearchBox ? 15 + this.titleField.getHeight() + this.searchBox.getHeight() : 10 + this.titleField.getHeight();
    }

    protected Panel createTopPanel() {
        return new ButtonListScreen.ButtonListTopPanel();
    }

    protected ButtonListScreen.ButtonPanel createMainPanel() {
        return new ButtonListScreen.ButtonPanel();
    }

    protected Pair<Integer, Integer> mainPanelInset() {
        return Pair.of(2, 2);
    }

    public abstract void addButtons(Panel var1);

    public void setTitle(Component txt) {
        this.title = txt;
        this.titleField.setText(txt);
    }

    public Component getTitle() {
        return this.title;
    }

    public void setBorder(int h, int v, int w) {
        this.borderH = h;
        this.borderV = v;
        this.borderW = w;
    }

    public void focus() {
        this.searchBox.setFocused(true);
    }

    protected class ButtonListTopPanel extends ThreePanelScreen.TopPanel {
        protected ButtonListTopPanel() {
            super();
        }

        public void addWidgets() {
            super.addWidgets();
            this.add(ButtonListScreen.this.titleField);
            if (ButtonListScreen.this.hasSearchBox) {
                this.add(ButtonListScreen.this.searchBox);
            }

        }

        public void alignWidgets() {
            super.alignWidgets();
            ButtonListScreen.this.titleField.setPosAndSize(5, 5, this.parent.width - 10, ButtonListScreen.this.titleField.getHeight());
            if (ButtonListScreen.this.hasSearchBox) {
                ButtonListScreen.this.searchBox.setPosAndSize(5, ButtonListScreen.this.titleField.getPosY() + ButtonListScreen.this.titleField.getHeight() + 5, this.parent.width - 10, ButtonListScreen.this.getTheme().getFontHeight() + 6);
            }

        }
    }

    protected class ButtonPanel extends Panel {
        public ButtonPanel() {
            super(ButtonListScreen.this);
        }

        public void add(Widget widget) {
            if (!ButtonListScreen.this.hasSearchBox || ButtonListScreen.this.searchBox.getText().isEmpty() || ButtonListScreen.this.getFilterText(widget).contains(ButtonListScreen.this.searchBox.getText().toLowerCase())) {
                super.add(widget);
            }

        }

        public void addWidgets() {
            ButtonListScreen.this.addButtons(this);
        }

        public void alignWidgets() {
            this.align(new WidgetLayout.Vertical(ButtonListScreen.this.borderV, ButtonListScreen.this.borderW, ButtonListScreen.this.borderV));

            if(!widgets.isEmpty()) {
                int maxWidth = widgets.stream().reduce((widget, widget2) -> widget.width < widget2.width ? widget2 : widget).get().width;
                this.widgets.forEach((w) -> {
                    w.setX(ButtonListScreen.this.borderH);
                    w.setWidth(maxWidth);
                });
                mainPanel.setWidth(Math.min(maxWidth + 14, width / 2));
            }
            topPanel.setWidth(mainPanel.getWidth());
        }
    }
}
