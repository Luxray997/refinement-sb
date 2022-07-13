package lux.skyblock.refinement.features.guis;

import javafx.util.Pair;
import lux.skyblock.refinement.events.PacketReceivedEvent;
import lux.skyblock.refinement.utils.RenderUtils;
import lux.skyblock.refinement.utils.Utils;
import lux.skyblock.refinement.utils.chat.HypixelChat;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.play.server.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static lux.skyblock.refinement.utils.chat.HypixelChat.getChat;
import static lux.skyblock.refinement.utils.chat.HypixelChat.isHypixelChat;

public class GuiDialogue extends GuiScreen {

    private String[] options = new String[2];

    private static int ticksUntilContinue = 0;

    private final List<Pair<String, String>> npcChatQueue;

    private final ResourceLocation DIALOGUE_GUI = new ResourceLocation("refinement:gui/dialogue.png");

    private final int textureWidth = 256;
    private final int textureHeight = 89;

    private static PositionedSoundRecord lastSound;

    private static String optionText;

    public GuiDialogue(Pair<String, String> initialText) {
        super();
        npcChatQueue = new ArrayList<>();
        npcChatQueue.add(0, initialText);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0 && ticksUntilContinue == 0) {
            this.advanceDialogue();
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void advanceDialogue() {
        if (npcChatQueue.size() <= 1) {
            mc.setIngameFocus();
            npcChatQueue.remove(0);
            return;
        }
        npcChatQueue.remove(0);
        initGui();
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void initGui() {
        // reloads whenever click occurs
        ticksUntilContinue = Utils.wordCount(npcChatQueue.get(0).getValue()) * 9;

        if (lastSound != null) {
            Utils.mc.getSoundHandler().playSound(lastSound);
        }

        if (options[0] != null && options[1] != null) {
            this.buttonList.add(new GuiButton(0, (this.width - (textureWidth / 2) - 98) / 2, this.height - 30, 98, 20, options[0]));
            this.buttonList.add(new GuiButton(1, (this.width + (textureWidth / 2) - 98) / 2, this.height - 30, 98, 20, options[1]));
        }

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        // Draw background
        this.mc.getTextureManager().bindTexture(DIALOGUE_GUI);
        int x = (this.width - this.textureWidth) / 2;
        int y = this.height - this.textureHeight - 40;
        this.drawTexturedModalRect(x, y, 0, 0, 256, 89);

        // Draw text
        RenderUtils.drawCenteredStringWithShadow(npcChatQueue.get(0).getKey(), this.width / 2, y + 5);
        RenderUtils.drawSplitStringWithShadow(npcChatQueue.get(0).getValue(), x + 5, y + 21, textureWidth - 10);
        if (ticksUntilContinue <= 0) {
            mc.fontRendererObj.drawString("Click anywhere to continue...", x + 5, y + textureHeight - 12, Color.GRAY.getRGB());
        }

        super.drawScreen(mouseX, mouseY, partialTicks);


    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String msgUnformatted = event.message.getUnformattedText();
        String msgFormatted = event.message.getFormattedText();
        if (isHypixelChat(msgFormatted)) {
            HypixelChat chat = getChat(msgFormatted);
            if (chat.tag == null || !chat.tag.equals("[NPC]")) return;
            if (Utils.mc.currentScreen instanceof GuiDialogue) {
                ((GuiDialogue) Utils.mc.currentScreen).npcChatQueue.add(new Pair<>(chat.sender, chat.message));
            } else {
                Utils.mc.displayGuiScreen(new GuiDialogue(new Pair<>(chat.sender, chat.message)));
            }

            event.setCanceled(true);
        }
        if (msgFormatted.contains("§eSelect an option:")) {
            //optionText = npcChatQueue.get(npcChatQueue.size() - 1).getValue();
            System.out.println(event.message.getChatStyle().toString());
        }




        // TODO: add options
        // §eSelect an option: §a§l[YES] §c§l[NO]
    }


    @SubscribeEvent
    public void onReceivePacket(PacketReceivedEvent event) {
        if (event.packet instanceof S29PacketSoundEffect) {
            S29PacketSoundEffect packet = (S29PacketSoundEffect) event.packet;
            String soundName = packet.getSoundName();
            if (soundName.equals("mob.villager.idle") || soundName.equals("mob.villager.yes") || soundName.equals("mob.villager.haggle")) {
                lastSound = PositionedSoundRecord.create(new ResourceLocation(soundName), packet.getPitch());
                if (Utils.mc.currentScreen instanceof GuiDialogue) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (ticksUntilContinue > 0) {
            ticksUntilContinue--;
        }
    }

}
