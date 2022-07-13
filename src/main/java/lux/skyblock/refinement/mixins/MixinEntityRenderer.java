package lux.skyblock.refinement.mixins;

import lux.skyblock.refinement.accessors.AccessorEntityRenderer;
import lux.skyblock.refinement.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer implements IResourceManagerReloadListener, AccessorEntityRenderer {

    @Shadow private Minecraft mc;

    @Shadow public abstract void setupOverlayRendering();

    @Shadow @Final private MapItemRenderer theMapItemRenderer;

    @Inject(method = "updateCameraAndRender", at = {@At(value = "INVOKE", target= "Lnet/minecraft/client/gui/GuiIngame;renderGameOverlay(F)V")})
    public void drawActivatedItem(float partialTicks, long nanoTime, CallbackInfo ci) {
        this.setupOverlayRendering();
        final ScaledResolution sr = new ScaledResolution(this.mc);
        int i1 = sr.getScaledWidth();
        int j1 = sr.getScaledHeight();
        RenderUtils.renderItemActivation(i1, j1, partialTicks);
    }

    @Inject(method = "updateRenderer", at = {@At("TAIL")})
    public void updateActivatedItem(CallbackInfo ci) {
        if (RenderUtils.itemActivationTicks > 0)
        {
            --RenderUtils.itemActivationTicks;

            if (RenderUtils.itemActivationTicks == 0)
            {
                RenderUtils.itemActivationItem = null;
            }
        }
    }

    @Override
    public void resetData() {
        RenderUtils.itemActivationItem = null;
        this.theMapItemRenderer.clearLoadedMaps();
    }
}
