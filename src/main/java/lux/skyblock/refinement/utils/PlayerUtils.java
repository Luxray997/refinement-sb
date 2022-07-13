package lux.skyblock.refinement.utils;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class PlayerUtils {
    public static Slot getSlot(int slot) {
        return Utils.mc.thePlayer.openContainer.getSlot(slot);
    }

    public static ItemStack getStackInSlot(int slot) {
        return Utils.mc.thePlayer.openContainer.getSlot(slot).getStack();
    }

    public static int findStack(String... itemNames) throws NoSuchElementException {
        System.out.println("starting search");
        ArrayList<String> itemNameList = new ArrayList<>(Arrays.asList(itemNames));
        for (int i = 0; i < Utils.mc.thePlayer.openContainer.inventorySlots.size(); i++) {
            Slot slot = Utils.mc.thePlayer.openContainer.getSlot(i);
            if (slot.getHasStack()) {
                for (String itemName : itemNameList) {
                    if (StringUtils.stripControlCodes(slot.getStack().getDisplayName()).contains(itemName)) {
                        System.out.println("found in slot " + i);
                        return i;
                    }
                }
            }
        }
        throw new NoSuchElementException("No object with names: " + Arrays.toString(itemNames));
    }
}
