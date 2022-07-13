package lux.skyblock.refinement.mixins;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S3EPacketTeams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayClient {
    @Shadow private WorldClient clientWorldController;

    @Inject(method = "handleTeams", at = {@At(value = "INVOKE", target = "Lnet/minecraft/scoreboard/Scoreboard;createTeam(Ljava/lang/String;)Lnet/minecraft/scoreboard/ScorePlayerTeam;")}, cancellable = true)
    public void teamAlreadyExistsFix(S3EPacketTeams packetIn, CallbackInfo ci) {
        if (this.clientWorldController.getScoreboard().getTeam(packetIn.getName()) != null) {
            ci.cancel();
        }
    }
}
