package lux.skyblock.refinement;

import lux.skyblock.refinement.commands.SettingsMenu;
import lux.skyblock.refinement.commands.TestCommand;
import lux.skyblock.refinement.features.TrophyFish;
import lux.skyblock.refinement.features.guis.GuiDialogue;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = "refinementsb", name = "Refinement", version = "0.0.1")
public class Refinement {


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        File directory = new File(event.getModConfigurationDirectory(), "refinement");
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new SettingsMenu());
        MinecraftForge.EVENT_BUS.register(new GuiDialogue(null));
        MinecraftForge.EVENT_BUS.register(new TrophyFish());

        ClientCommandHandler.instance.registerCommand(new SettingsMenu());
        ClientCommandHandler.instance.registerCommand(new TestCommand());

        SettingsMenu.configFile.initialize();

    }


}
