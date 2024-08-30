package at.petrak.paucal.forge;

import at.petrak.paucal.PaucalConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public class ForgePaucalConfig implements PaucalConfig.ConfigAccess {
    private static ForgeConfigSpec.BooleanValue allowPats;
    private static ForgeConfigSpec.BooleanValue loadContributors;

    public ForgePaucalConfig(ForgeConfigSpec.Builder builder) {
        allowPats = builder.comment("Whether to allow patting players with a shift-right-click.")
            .define("allowPats", true);
        loadContributors = builder.comment("Whether to load contributor info from the internet.",
                "If false, no one will appear as a contributor.")
            .define("loadContributors", true);
    }

    @Override
    public boolean allowPats() {
        return allowPats.get();
    }

    @Override
    public boolean loadContributors() {
        return loadContributors.get();
    }
}
