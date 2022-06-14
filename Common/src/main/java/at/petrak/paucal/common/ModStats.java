package at.petrak.paucal.common;

import at.petrak.paucal.api.PaucalAPI;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

public class ModStats {
    public static ResourceLocation PLAYERS_PATTED;
    public static ResourceLocation HEADPATS_GOTTEN;

    public static void register() {
        PLAYERS_PATTED = makeCustomStat("players_patted", StatFormatter.DEFAULT);
        HEADPATS_GOTTEN = makeCustomStat("headpats_gotten", StatFormatter.DEFAULT);
    }

    private static ResourceLocation makeCustomStat(String pKey, StatFormatter pFormatter) {
        ResourceLocation resourcelocation = new ResourceLocation(PaucalAPI.MOD_ID, pKey);
        Registry.register(Registry.CUSTOM_STAT, pKey, resourcelocation);
        Stats.CUSTOM.get(resourcelocation, pFormatter);
        return resourcelocation;
    }
}
