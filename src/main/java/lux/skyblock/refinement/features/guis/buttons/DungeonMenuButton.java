package lux.skyblock.refinement.features.guis.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;

public class DungeonMenuButton extends BoundButton {

    private final ResourceLocation DUNGEON_MENU = new ResourceLocation("refinement:gui/dungeon-menu.png");

    public DungeonMenuButton(int buttonId, int x, int y, int boundSlot) {
        super(buttonId, x, y, 36, 36, boundSlot);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

        mc.getTextureManager().bindTexture(DUNGEON_MENU);

        int textureY = 0;

        if (hovered) {
            textureY += 36;

        }

        this.drawTexturedModalRect(this.xPosition, this.yPosition, 176, textureY, 36, 36);
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) {
    }

    public int getBoundSlot() {
        return boundSlot;
    }

}
