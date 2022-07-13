package lux.skyblock.refinement.mixins;

import lux.skyblock.refinement.utils.PlayerUtils;
import lux.skyblock.refinement.utils.RenderUtils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiChest.class})
public abstract class MixinGuiChest extends GuiContainer {

    private final ResourceLocation CRAFTING_MENU = new ResourceLocation("refinement:gui/crafting.png");
    private final ResourceLocation DUNGEON_MENU = new ResourceLocation("refinement:gui/dungeon-menu.png");
    private final ResourceLocation DUNGEON_SALVAGE = new ResourceLocation("refinement:gui/dungeon-salvage.png");
    private final ResourceLocation DUNGEON_REPAIR = new ResourceLocation("refinement:gui/dungeon-repair.png");
    private final ResourceLocation REFORGE_GUI = new ResourceLocation("refinement:gui/reforge.png");
    private final ResourceLocation REFORGE_ADVANCED_GUI = new ResourceLocation("refinement:gui/reforge_advanced.png");


    @Shadow private IInventory lowerChestInventory;

    public MixinGuiChest(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Inject(method = {"drawGuiContainerBackgroundLayer"}, at = {@At(value = "INVOKE", target="Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V")}, cancellable = true)
    public void drawCustomGUI(CallbackInfo ci) {
        switch (this.lowerChestInventory.getDisplayName().getUnformattedText()) {
            case "Craft Item":
                mc.getTextureManager().bindTexture(CRAFTING_MENU);
                int x1 = (mc.currentScreen.width - 176) / 2;
                int y1 = (mc.currentScreen.height - 166) / 2;
                GuiUtils.drawTexturedModalRect(x1, y1, 0, 0, 176, 166, 0F);
                ci.cancel();
                break;
            case "Dungeon Blacksmith":
                mc.getTextureManager().bindTexture(DUNGEON_MENU);
                int x2 = (mc.currentScreen.width - 176) / 2;
                int y2 = (mc.currentScreen.height - 129) / 2;
                GuiUtils.drawTexturedModalRect(x2, y2, 0, 0, 176, 129, 0F);
                ci.cancel();
                break;
            case "Salvage Item":
                mc.getTextureManager().bindTexture(DUNGEON_SALVAGE);
                int x3 = (mc.currentScreen.width - 176) / 2;
                int y3 = (mc.currentScreen.height - 166) / 2;
                GuiUtils.drawTexturedModalRect(x3, y3, 0, 0, 176, 166, 0F);
                ci.cancel();
                break;
            case "Repair Dungeon Item":
                mc.getTextureManager().bindTexture(DUNGEON_REPAIR);
                int x4 = (mc.currentScreen.width - 176) / 2;
                int y4 = (mc.currentScreen.height - 166) / 2;
                GuiUtils.drawTexturedModalRect(x4, y4, 0, 0, 176, 166, 0F);
                ci.cancel();
                break;
            case "Reforge Item":
                mc.getTextureManager().bindTexture(REFORGE_GUI);
                int x5 = (mc.currentScreen.width - 176) / 2;
                int y5 = (mc.currentScreen.height - 166) / 2;
                GuiUtils.drawTexturedModalRect(x5, y5, 0, 0, 176, 166, 0F);
                ci.cancel();
                break;
            case "Reforge Item (Advanced)":
                mc.getTextureManager().bindTexture(REFORGE_ADVANCED_GUI);
                int x6 = (mc.currentScreen.width - 176) / 2;
                int y6 = (mc.currentScreen.height - 184) / 2;
                GuiUtils.drawTexturedModalRect(x6, y6, 0, 0, 176, 184, 0F);
                if (PlayerUtils.getSlot(22).getHasStack() && PlayerUtils.getStackInSlot(22).getDisplayName().contains("Error")) {
                    GuiUtils.drawTexturedModalRect(x6 + 80, y6 + 41, 176, 36, 15, 22, 0F);
                }
                ci.cancel();
                break;
            default:
                break;
        }
    }

    @Inject(method = "drawGuiContainerForegroundLayer", at = {@At("HEAD")}, cancellable = true)
    public void drawTitles(CallbackInfo ci) {
        switch (this.lowerChestInventory.getDisplayName().getUnformattedText()) {
            case "Craft Item":
                ci.cancel();
                mc.fontRendererObj.drawString("Craft Item", 8, 34, 4210752);
                mc.fontRendererObj.drawString("Inventory", 8, 100, 4210752);
                break;
            case "Dungeon Blacksmith":
                ci.cancel();
                break;
            case "Salvage Item":
                ci.cancel();
                RenderUtils.drawCenteredString("Salvage Item", 88, 34, 4210752);
                mc.fontRendererObj.drawString("Inventory", 8, 100, 4210752);
                break;
            case "Repair Dungeon Item":
                ci.cancel();
                RenderUtils.drawCenteredString("Repair Item", 88, 34, 4210752);
                mc.fontRendererObj.drawString("Inventory", 8, 100, 4210752);
                break;
            case "Reforge Item":
                ci.cancel();
                RenderUtils.drawCenteredString("Reforge Item", 88, 25, 4210752);
                mc.fontRendererObj.drawString("Inventory", 8, 91, 4210752);
                break;
            case "Reforge Item (Advanced)":
                ci.cancel();
                RenderUtils.drawCenteredString("Reforge Item (Advanced)", 88, 25, 4210752);
                mc.fontRendererObj.drawString("Inventory", 8, 109, 4210752);
                break;
            default:
                break;
        }
    }


}
