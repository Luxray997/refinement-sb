package lux.skyblock.refinement.events;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PacketReceivedEvent extends Event {

    public Packet<?> packet;

    public PacketReceivedEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public boolean isCancelable()
    {
        return true;
    }

}
