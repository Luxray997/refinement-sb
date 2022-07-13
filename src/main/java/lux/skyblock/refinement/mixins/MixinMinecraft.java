package lux.skyblock.refinement.mixins;

import lux.skyblock.refinement.accessors.AccessorEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Shadow public EntityRenderer entityRenderer;

    @Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V", at = {@At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/gui/achievement/GuiAchievement;clearAchievements()V")})
    public void resetData(WorldClient worldClientIn, String loadingMessage, CallbackInfo ci) {
        ((AccessorEntityRenderer) this.entityRenderer).resetData();
    }
}
