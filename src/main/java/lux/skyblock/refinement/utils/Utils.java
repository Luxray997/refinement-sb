package lux.skyblock.refinement.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;

import java.util.StringTokenizer;

public class Utils {

    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
    public static final WorldClient theWorld = Minecraft.getMinecraft().theWorld;

    public static int wordCount(String s) {
        StringTokenizer st = new StringTokenizer(s);
        return st.countTokens();
    }
}
