package lux.skyblock.refinement.commands;

import javafx.util.Pair;
import lux.skyblock.refinement.features.guis.GuiDialogue;
import lux.skyblock.refinement.utils.PlayerUtils;
import lux.skyblock.refinement.utils.RenderUtils;
import lux.skyblock.refinement.utils.Utils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Thread.sleep;
import static lux.skyblock.refinement.utils.Utils.mc;

public class TestCommand implements ICommand {


    @Override
    public String getCommandName() { return "testcmd"; }

    @Override
    public String getCommandUsage(ICommandSender sender) { return "/" + getCommandName(); }

    @Override
    public List<String> getCommandAliases() { return new ArrayList<>(Collections.emptyList());
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        switch(args[0]) {
            case "openscreen":
                new Thread(() -> {
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mc.displayGuiScreen(new GuiDialogue(new Pair<>("Luxray997", "Hi!")));
                }).start();
                break;
            case "activateitem":
                RenderUtils.displayItemActivation(PlayerUtils.getStackInSlot(Integer.parseInt(args[1])));
                break;
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) { return true; }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        switch (args.length) {
            case 1:
                return new ArrayList<>(Arrays.asList("openscreen", "activateitem"));
        }
        return new ArrayList<>();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) { return false; }

    @Override
    public int compareTo(@NotNull ICommand o) { return 0; }


}