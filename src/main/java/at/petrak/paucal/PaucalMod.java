package at.petrak.paucal;

import at.petrak.paucal.api.contrib.Contributors;
import at.petrak.paucal.api.lootmod.PaucalLootMods;
import at.petrak.paucal.common.PaucalConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(PaucalMod.MOD_ID)
public class PaucalMod {
    public static final String MOD_ID = "paucal";
    public static final String CONTRIBUTOR_URL = "https://raw.githubusercontent.com/gamma-delta/PAUCAL/main/contributors.toml";

    public static final Logger LOGGER = LoggerFactory.getLogger(PaucalMod.class);

    private static final ForgeConfigSpec CONFIG_SPEC;

    static {
        var specPair = new ForgeConfigSpec.Builder().configure(PaucalConfig::new);
        CONFIG_SPEC = specPair.getRight();
    }

    public PaucalMod() {
        Contributors.loadContributors();

        var modbus = FMLJavaModLoadingContext.get().getModEventBus();

        PaucalLootMods.LOOT_MODS.register(modbus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG_SPEC);
    }
}
