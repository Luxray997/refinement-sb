package lux.skyblock.refinement.utils.chat;

public class ChatUtils {

    public static Chat identifyChatElements(String chat, ChatType type) {
        switch (type) {
            case HYPIXEL:
                return new HypixelChat(chat);
            default:
                throw new IllegalArgumentException();
        }
    }

}

