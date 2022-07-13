package lux.skyblock.refinement.mixins;

import lux.skyblock.refinement.features.guis.buttons.*;
import lux.skyblock.refinement.utils.PlayerUtils;
import lux.skyblock.refinement.utils.RenderUtils;
import lux.skyblock.refinement.utils.Utils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiContainer.class})
public abstract class MixinGuiContainer extends GuiScreen {

    @Shadow
    public Container inventorySlots;

    @Shadow protected abstract void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType);

    @Shadow protected abstract void drawItemStack(ItemStack stack, int x, int y, String altText);

    @Inject(method = {"initGui"}, at = {@At("TAIL")})
    public void editSlots(CallbackInfo ci) {
        if (this.inventorySlots instanceof ContainerChest) {
            switch (((ContainerChest) this.inventorySlots).getLowerChestInventory().getDisplayName().getUnformattedText()) {
                case "Craft Item":
                    // move top chest slots off screen:
                    for (int i = 0; i <= 53; i++) {
                        Slot slot = this.inventorySlots.getSlot(i);
                        slot.xDisplayPosition = -1000;

                    }
                    for (int i = 10; i <= 34; i++) {
                        Slot slot = this.inventorySlots.getSlot(i);
                        // crafting slots:
                        if (i % 9 == 1 || i % 9 == 2 || i % 9 == 3) {
                            slot.xDisplayPosition = 17 + ((i % 9) - 1) * 18;
                            slot.yDisplayPosition = 45 + ((i / 9) - 1) * 18;
                            continue;
                        }
                        // quick crafting:
                        if (i % 9 == 7) {
                            slot.xDisplayPosition = 143;
                            slot.yDisplayPosition = 45 + ((i / 9) - 1) * 18;
                        }
                    }
                    //craft slot
                    Slot craftSlot = this.inventorySlots.getSlot(23);
                    craftSlot.xDisplayPosition = 113;
                    craftSlot.yDisplayPosition = 63;

                    // regular inventory:
                    for (int i = 54; i <= 80; i++) {
                        Slot slot = this.inventorySlots.getSlot(i);
                        slot.yDisplayPosition = 18 * ((i - 54) / 9) + 112;
                    }
                    // hotbar:
                    for (int i = 81; i <= 89; i++) {
                        Slot slot = this.inventorySlots.getSlot(i);
                        slot.yDisplayPosition = 170;
                    }
                    break;
                case "Dungeon Blacksmith":
                    for (int i = 0; i <= 89; i++) {
                        Slot slot = this.inventorySlots.getSlot(i);
                        slot.yDisplayPosition = -1000;
                    }
                    int x1 = (mc.currentScreen.width - 176) / 2;
                    int y1 = (mc.currentScreen.height - 129) / 2;
                    this.buttonList.add(new DungeonMenuButton(63420, x1 + 18, y1 + 20, 11));
                    this.buttonList.add(new DungeonMenuButton(63421, x1 + 70, y1 + 20, 13));
                    this.buttonList.add(new DungeonMenuButton(63422, x1 + 122, y1 + 20, 15));
                    this.buttonList.add(new DungeonMenuButton(63423, x1 + 18, y1 + 73, 29));
                    this.buttonList.add(new DungeonMenuButton(63424, x1 + 70, y1 + 73, 31));
                    this.buttonList.add(new DungeonMenuButton(63425, x1 + 122, y1 + 73, 33));
                    break;
                case "Salvage Item":
                    for (int i = 0; i <= 53; i++) {
                        Slot slot = this.inventorySlots.getSlot(i);
                        slot.yDisplayPosition = -1000;
                    }

                    for (int i = 54; i <= 80; i++) {
                        Slot slot = this.inventorySlots.getSlot(i);
                        slot.yDisplayPosition = 18 * ((i - 54) / 9) + 112;
                    }
                    // hotbar:
                    for (int i = 81; i <= 89; i++) {
                        Slot slot = this.inventorySlots.getSlot(i);
                        slot.yDisplayPosition = 170;
                    }

                    Slot salvageSlot = this.inventorySlots.getSlot(22);
                    salvageSlot.yDisplayPosition = 47;

                    int x2 = (mc.currentScreen.width - 176) / 2;
                    int y2 = (mc.currentScreen.height - 166) / 2;
                    // info
                    this.buttonList.add(new DungeonSalvageButton(63426, x2 + 149, y2 + 7, 50, 0));
                    // salvage
                    this.buttonList.add(new DungeonSalvageButton(63427, x2 + 78, y2 + 50, 31, 1));
                    // back
                    this.buttonList.add(new DungeonSalvageButton(63428, x2 + 7, y2 + 7, 48, 2));
                    break;
                case "Repair Dungeon Item":
                    for (int i = 0; i <= 53; i++) {
                        Slot slot = this.inventorySlots.getSlot(i);
                        slot.yDisplayPosition = -1000;
                    }

                    for (int i = 54; i <= 80; i++) {
                        Slot slot = this.inventorySlots.getSlot(i);
                        slot.yDisplayPosition = 18 * ((i - 54) / 9) + 112;
                    }
                    // hotbar:
                    for (int i = 81; i <= 89; i++) {
                        Slot slot = this.inventorySlots.getSlot(i);
                        slot.yDisplayPosition = 170;
                    }

                    Slot repairSlot = this.inventorySlots.getSlot(22);
                    repairSlot.yDisplayPosition = 47;

                    int x3 = (mc.currentScreen.width - 176) / 2;
                    int y3 = (mc.currentScreen.height - 166) / 2;

                    // back
                    this.buttonList.add(new DungeonRepairButton(63429, x3 + 7, y3 + 7, 48, 1));
                    // salvage
                    this.buttonList.add(new DungeonRepairButton(63430, x3 + 78, y3 + 50, 31, 0));
                    break;
                case "Reforge Item":
                    for (int i = 0; i <= 44; i++) {
                        Slot slot = this.inventorySlots.getSlot(i);
                        slot.yDisplayPosition = -1000;
                    }

                    for (int i = 45; i <= 71; i++) {
                        Slot slot = this.inventorySlots.getSlot(i);
                        slot.yDisplayPosition = 18 * ((i - 45) / 9) + 103;
                    }
                    // hotbar:
                    for (int i = 72; i <= 80; i++) {
                        Slot slot = this.inventorySlots.getSlot(i);
                        slot.yDisplayPosition = 161;
                    }

                    this.inventorySlots.getSlot(13).yDisplayPosition = 38;

                    int x4 = (mc.currentScreen.width - 176) / 2;
                    int y4 = (mc.currentScreen.height - 166) / 2;

                    // reforge
                    this.buttonList.add(new ReforgeButton(63431, x4 + 78, y4 + 50, 22, 0));
                    // go back
                    this.buttonList.add(new ReforgeButton(63432, x4 + 7, y4 + 7, 39, 1));
                    break;
                case "Reforge Item (Advanced)":
                    for (int i = 0; i <= 53; i++) {
                        Slot slot = this.inventorySlots.getSlot(i);
                        slot.yDisplayPosition = -1000;
                    }

                    for (int i = 54; i <= 80; i++) {
                        Slot slot = this.inventorySlots.getSlot(i);
                        slot.yDisplayPosition = 18 * ((i - 54) / 9) + 121;
                    }
                    // hotbar:
                    for (int i = 81; i <= 89; i++) {
                        Slot slot = this.inventorySlots.getSlot(i);
                        slot.yDisplayPosition = 179;
                    }

                    Slot itemReforge = this.inventorySlots.getSlot(29);
                    itemReforge.xDisplayPosition = 29;
                    itemReforge.yDisplayPosition = 38;

                    Slot reforgeStone = this.inventorySlots.getSlot(33);
                    reforgeStone.xDisplayPosition = 131;
                    reforgeStone.yDisplayPosition = 38;

                    Slot result = this.inventorySlots.getSlot(13);
                    result.yDisplayPosition = 88;

                    int x5 = (mc.currentScreen.width - 176) / 2;
                    int y5 = (mc.currentScreen.height - 184) / 2;
                    // reforge
                    this.buttonList.add(new ReforgeAdvancedButton(63433, x5 + 78, y5 + 18, 22, 0));
                    // back
                    this.buttonList.add(new ReforgeAdvancedButton(63434, x5 + 7, y5 + 68, 48, 1));
                    // basic reforging
                    this.buttonList.add(new ReforgeAdvancedButton(63435, x5 + 149, y5 + 68, 52, 2));
                    break;
                default:
                    break;
            }
        }
    }

    @Inject(method = "drawScreen", at = {@At("TAIL")})
    public void renderExtraTooltips(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (!(this.inventorySlots instanceof ContainerChest)) return;
        switch (((ContainerChest) this.inventorySlots).getLowerChestInventory().getDisplayName().getUnformattedText()) {
            case "Dungeon Blacksmith":
            case "Salvage Item":
            case "Repair Dungeon Item":
            case "Reforge Item":
            case "Reforge Item (Advanced)":
                for (GuiButton button : this.buttonList) {
                    if (button instanceof BoundButton)
                        if (button.isMouseOver()) {
                            if (Utils.mc.thePlayer.inventory.getItemStack() == null && PlayerUtils.getSlot(((BoundButton) button).getBoundSlot()) != null && PlayerUtils.getSlot(((BoundButton) button).getBoundSlot()).getHasStack()) {
                                ItemStack stack = PlayerUtils.getStackInSlot(((BoundButton) button).getBoundSlot());
                                RenderUtils.renderToolTip(stack, mouseX, mouseY);
                            }
                        }
                }
                break;
            default:
                break;
        }
    }

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiContainer;drawItemStack(Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", ordinal = 0))
    public void cancelHeldItemRender(GuiContainer instance, ItemStack stack, int x, int y, String altText) {
        if (this.inventorySlots instanceof ContainerChest) {
            String text = ((ContainerChest) this.inventorySlots).getLowerChestInventory().getDisplayName().getUnformattedText();
            String name = stack.getDisplayName();
            switch (text) {
                case "Dungeon Blacksmith":
                    return;
                case "Salvage Item":
                    if (name.contains("Salvage Item") || name.contains("Essence Guide") || name.contains("Go Back") || name.contains("Error"))
                        return;
                    break;
                case "Repair Dungeon item":
                    if (name.contains("Repair Dungeon Item") || name.contains("Go Back") || name.contains("Error"))
                        return;
                    break;
                case "Reforge Item (Advanced)":
                    if (name.contains("Basic Reforging"))
                        return;
                case "Reforge Item":
                    if (name.contains("Reforge Item") || name.contains("Go Back") || name.contains("Error"))
                        return;
                    break;
            }
        }

        drawItemStack(stack, x, y, altText);
    }


    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button instanceof BoundButton) {
            int boundSlot = ((BoundButton) button).getBoundSlot();
            this.handleMouseClick(null, boundSlot, 0, 0);
        }
    }
}

