package lux.skyblock.refinement.mixins;

import io.netty.channel.ChannelHandlerContext;
import lux.skyblock.refinement.events.PacketReceivedEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager {

    @Inject(method = {"channelRead0"}, at = {@At("HEAD")}, cancellable = true)
    private void onPacketReceived(ChannelHandlerContext context, Packet<?> packet, CallbackInfo ci) {
        PacketReceivedEvent event = new PacketReceivedEvent(packet);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) ci.cancel();
        // bruh
    }
}
