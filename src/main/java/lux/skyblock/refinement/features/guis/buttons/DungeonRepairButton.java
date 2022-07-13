package lux.skyblock.refinement.features.guis.buttons;

import lux.skyblock.refinement.utils.PlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;

public class DungeonRepairButton extends BoundButton {

    private final ResourceLocation DUNGEON_REPAIR = new ResourceLocation("refinement:gui/dungeon-repair.png");

    /**
     * Specifies the button type:
     * <p> 0 - Repair </p>
     * <p> 1 - Back </p>
     * <p> 2 - Error </p>
     */
    public int type;

    public DungeonRepairButton(int buttonId, int x, int y, int boundSlot, int type) {
        super(buttonId, x, y, 20, 18, boundSlot);
        this.type = type;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {

        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;


        if (type == 0 || type == 2) {
            if (PlayerUtils.getSlot(31).getHasStack()) {
                if (PlayerUtils.getStackInSlot(31).getUnlocalizedName().contains("barrier")) {
                    type = 2;
                } else {
                    type = 0;
                }
            }
        }


        mc.getTextureManager().bindTexture(DUNGEON_REPAIR);

        int textureX = 176;
        int textureY = 0;

        textureX += type * 20;

        if (this.hovered && type != 2) {
            textureY += 18;
        }

        this.drawTexturedModalRect(this.xPosition, this.yPosition, textureX, textureY, 20, 18);
    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn) { }

    public int getBoundSlot() {
        return boundSlot;
    }
}
