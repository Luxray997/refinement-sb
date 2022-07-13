package lux.skyblock.refinement.mixins;

import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static lux.skyblock.refinement.utils.Utils.mc;

@Mixin({GuiMainMenu.class})
public class MixinGuiMainMenu {
    @Inject(method = {"drawScreen"}, at = {@At("TAIL")})
    public void drawString(CallbackInfo ci) {
        mc.fontRendererObj.drawString("mixins working...", 5, 5, 0);
    }
}
