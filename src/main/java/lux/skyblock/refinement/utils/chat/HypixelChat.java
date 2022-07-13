package lux.skyblock.refinement.utils.chat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HypixelChat extends Chat {

    private static final Pattern hypixelChatPattern = Pattern.compile("(§.)?(?<tag>\\[.*\\])? ?(?<sender>[a-zA-Z§0-9 ]*)(§f)?: (?<message>.*)");

    public String tag;

    public HypixelChat(String chat) {
        Matcher matcher = hypixelChatPattern.matcher(chat);
        if (!matcher.matches()) return;
        this.tag = matcher.group("tag");
        this.sender = matcher.group("sender");
        this.message = matcher.group("message");
    }

    public static boolean isHypixelChat(String chat) {
        return hypixelChatPattern.matcher(chat).matches();
    }

    public static HypixelChat getChat(String chat) {
        return new HypixelChat(chat);
    }
}
