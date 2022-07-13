package lux.skyblock.refinement;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Comparator;

public class Config extends Vigilant {
    public static Config INSTANCE;

    static {
        try {
            INSTANCE = new Config();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    // general
    @Property(
            type = PropertyType.SWITCH,
            name = "name",
            description = "description",
            category = "category",
            subcategory = "subcategory"
    )
    public boolean testSwitch;

    public Config() throws NoSuchFieldException {
        super(new File("./config/refinement/config.toml"), "Refinement", new JVMAnnotationPropertyCollector(), new ConfigSorting());
        initialize();
    }

    public static class ConfigSorting extends SortingBehavior {
        @NotNull
        @Override
        public Comparator<Category> getCategoryComparator() {
            return (o1, o2) -> {
                if(o1.getName().equals("SkyblockPlayer")) {
                    return -1;
                } else if(o2.getName().equals("SkyblockPlayer")) {
                    return 1;
                } else {
                    return o1.getName().compareTo(o2.getName());
                }
            };
        }
    }
}
