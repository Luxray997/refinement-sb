package lux.skyblock.refinement.utils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.awt.*;
import java.util.List;
import java.util.Random;

import static lux.skyblock.refinement.utils.Utils.mc;

public class RenderUtils {
    public static int DEFAULT_COLOR = 16777215;

    private static final Random random = new Random();
    public static ItemStack itemActivationItem;
    public static int itemActivationTicks;
    public static float itemActivationOffX;
    public static float itemActivationOffY;

    public static void drawSplitStringWithShadow(String str, int x, int y, int wrapWidth) {
        mc.fontRendererObj.drawSplitString(StringUtils.stripControlCodes(str), x + 1, y + 1, wrapWidth, new Color(50,50,50).getRGB());
        mc.fontRendererObj.drawSplitString(str, x, y, wrapWidth, DEFAULT_COLOR);
    }

    public static void drawCenteredString(String str, int x, int y) {
        int strWidth = mc.fontRendererObj.getStringWidth(str);
        mc.fontRendererObj.drawString(str, x - strWidth / 2, y, DEFAULT_COLOR);
    }

    public static void drawCenteredString(String str, int x, int y, int color) {
        int strWidth = mc.fontRendererObj.getStringWidth(str);
        mc.fontRendererObj.drawString(str, x - strWidth / 2, y, color);
    }

    public static void drawCenteredStringWithShadow (String str, int x, int y) {
        int strWidth = mc.fontRendererObj.getStringWidth(str);
        mc.fontRendererObj.drawStringWithShadow(str, (float) (x - strWidth / 2), y, DEFAULT_COLOR);
    }


    public static void renderToolTip(ItemStack stack, int x, int y) {
        List<String> list = stack.getTooltip(Utils.mc.thePlayer, Utils.mc.gameSettings.advancedItemTooltips);

        for (int i = 0; i < list.size(); ++i) {
            if (i == 0) {
                list.set(i, stack.getRarity().rarityColor + list.get(i));
            } else {
                list.set(i, EnumChatFormatting.GRAY + list.get(i));
            }
        }

        GuiUtils.drawHoveringText(list, x, y, mc.displayWidth, mc.displayHeight, -1, mc.fontRendererObj);

    }

    // taken from minecraft 1.11
    public static void displayItemActivation(ItemStack item) {
        itemActivationItem = item;
        itemActivationTicks = 40;
        itemActivationOffX = random.nextFloat() * 2.0F - 1.0F;
        itemActivationOffY = random.nextFloat() * 2.0F - 1.0F;
    }

    // taken from minecraft 1.11
    public static void renderItemActivation(int int1, int int2, float partialTicks) {
        if (itemActivationItem != null && itemActivationTicks > 0) {
            int i = 40 - itemActivationTicks;
            float f = ((float)i + partialTicks) / 40.0F;
            float f1 = f * f;
            float f2 = f * f1;
            float f3 = 10.25F * f2 * f1 + -24.95F * f1 * f1 + 25.5F * f2 + -13.8F * f1 + 4.0F * f;
            float f4 = f3 * (float)Math.PI;
            float f5 = itemActivationOffX * (float)(int1 / 4);
            float f6 = itemActivationOffY * (float)(int2 / 4);
            GlStateManager.enableAlpha();
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            GlStateManager.enableDepth();
            GlStateManager.disableCull();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.translate((float)(int1 / 2) + f5 * MathHelper.abs(MathHelper.sin(f4 * 2.0F)), (float)(int2 / 2) + f6 * MathHelper.abs(MathHelper.sin(f4 * 2.0F)), -50.0F);
            float f7 = 50.0F + 175.0F * MathHelper.sin(f4);
            GlStateManager.scale(f7, -f7, f7);
            GlStateManager.rotate(900.0F * MathHelper.abs(MathHelper.sin(f4)), 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(6.0F * MathHelper.cos(f * 8.0F), 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(6.0F * MathHelper.cos(f * 8.0F), 0.0F, 0.0F, 1.0F);
            if (itemActivationItem.getItem() instanceof ItemSkull || itemActivationItem.getItem() instanceof ItemBlock)
                Utils.mc.getRenderItem().renderItem(itemActivationItem, ItemCameraTransforms.TransformType.GUI);
            else
                Utils.mc.getRenderItem().renderItem(itemActivationItem, (ItemCameraTransforms.TransformType) null);
            // none: backwards item and skull
            // gui: backwards and flat item
            // fixed: backwards item, skull
            // ground: backwards all
            // first person: front all but weird perspective on item
            // head: backwards all
            // third person: no
            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.enableCull();
            GlStateManager.disableDepth();
        }
    }
}
