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
