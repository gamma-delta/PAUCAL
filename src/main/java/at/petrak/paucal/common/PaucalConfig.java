package at.petrak.paucal.common;

import net.minecraftforge.common.ForgeConfigSpec;

public class PaucalConfig {
    public static ForgeConfigSpec.BooleanValue allowPats;
    public static ForgeConfigSpec.BooleanValue loadContributors;

    public PaucalConfig(ForgeConfigSpec.Builder builder) {
        allowPats = builder.comment("Whether to allow patting players with a shift-right-click.")
            .define("allowPats", true);
        loadContributors = builder.comment("Whether to load contributor info from the internet.",
                "If false, no one will appear as a contributor.")
            .define("loadContributors", true);
    }
}
