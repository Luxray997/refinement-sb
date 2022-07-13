package lux.skyblock.refinement.features;

import lux.skyblock.refinement.utils.PlayerUtils;
import lux.skyblock.refinement.utils.RenderUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static java.lang.Thread.sleep;

public class TrophyFish {

    private static boolean uniqueFish;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {

        if (event.message.getUnformattedText().startsWith("NEW DISCOVERY")) {
            uniqueFish = true;
            return;
        }


        if (uniqueFish) {
            uniqueFish = false;
            String[] msg = event.message.getUnformattedText().split(" ");
            String fishName = "";
            for (int i=5; i<msg.length; i++) {
                fishName = fishName.concat(msg[i] + " ");
            }
            fishName = fishName.trim().substring(0, fishName.length() - 2);
            String finalFishName = fishName;
            new Thread(() -> {
                try { sleep(250); } catch (InterruptedException e) { e.printStackTrace(); }

                RenderUtils.displayItemActivation(PlayerUtils.getStackInSlot(PlayerUtils.findStack(finalFishName)));
            }).start();

        }
    }
}
