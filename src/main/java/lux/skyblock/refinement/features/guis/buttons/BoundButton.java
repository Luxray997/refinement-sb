package lux.skyblock.refinement.features.guis.buttons;

import net.minecraft.client.gui.GuiButton;

public abstract class BoundButton extends GuiButton {

    protected final int boundSlot;

    public BoundButton(int buttonId, int x, int y, int widthIn, int heightIn, int boundSlot) {
        super(buttonId, x, y, widthIn, heightIn, "");
        this.boundSlot = boundSlot;
    }


    public int getBoundSlot() {
        return this.boundSlot;
    };

}
