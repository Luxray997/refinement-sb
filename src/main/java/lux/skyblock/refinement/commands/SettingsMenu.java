package lux.skyblock.refinement.commands;

import lux.skyblock.refinement.Config;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static lux.skyblock.refinement.utils.Utils.mc;

public class SettingsMenu implements ICommand {

    private static GuiScreen screen = null;
    public static Config configFile = Config.INSTANCE;

    @Override
    public String getCommandName() { return "refinement"; }

    @Override
    public String getCommandUsage(ICommandSender sender) { return "/" + getCommandName(); }

    @Override
    public List<String> getCommandAliases() { return new ArrayList<>(Arrays.asList("rf", "rfm"));
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        screen = configFile.gui();
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) { return true; }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) { return new ArrayList<>(); }

    @Override
    public boolean isUsernameIndex(String[] args, int index) { return false; }

    @Override
    public int compareTo(@NotNull ICommand o) { return 0; }


    @SubscribeEvent
    public void renderSettingsMenu(TickEvent.ClientTickEvent event) {
        if (screen != null) {
            try {
                mc.displayGuiScreen(screen);
            } catch (Exception e) {
                e.printStackTrace();
            }
            screen = null;
        }
    }
}
