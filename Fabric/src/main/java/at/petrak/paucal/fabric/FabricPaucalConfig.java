package at.petrak.paucal.fabric;

import at.petrak.paucal.PaucalConfig;
import at.petrak.paucal.api.PaucalAPI;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;

@Config(name = PaucalAPI.MOD_ID)
public class FabricPaucalConfig implements ConfigData {
    public static void setup() {
        AutoConfig.register(FabricPaucalConfig.class, JanksonConfigSerializer::new);

        var configs = AutoConfig.getConfigHolder(FabricPaucalConfig.class).getConfig();
        PaucalConfig.setCommon(configs.commonInstance);
    }

    @ConfigEntry.Gui.CollapsibleObject
    CommonCfg commonInstance = new CommonCfg();

    private static class CommonCfg implements PaucalConfig.ConfigAccess, ConfigData {
        boolean allowPats = true;
        boolean loadContributors = true;

        /*
        public ConfigTree configure(ConfigTreeBuilder bob) {
            bob
                .beginValue("allowPats", ConfigTypes.BOOLEAN, true)
                .withComment("Whether to allow patting players with a shift-right-click.")
                .finishValue(allowPats::mirror)

                .beginValue("loadContributors", ConfigTypes.BOOLEAN, true)
                .withComment("Whether to load contributor info from the internet.\n" +
                    "If false, no one will appear as a contributor.")
                .finishValue(loadContributors::mirror);

            return bob.build();
        }
         */

        @Override
        public boolean allowPats() {
            return allowPats;
        }

        @Override
        public boolean loadContributors() {
            return loadContributors;
        }
    }
}
