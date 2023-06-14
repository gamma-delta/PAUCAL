package at.petrak.paucal.datagen;

import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.api.datagen.PaucalDatagenHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PaucalAPI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent evt) {
        var gen = evt.getGenerator();
        var lookup = evt.getLookupProvider();

        if (System.getProperty("paucal.xplat_datagen") != null) {
            gen.addProvider(evt.includeServer(), PaucalDatagenHelper.wrapAdvancementSubProviders(lookup,
                new ModAdvancementSubProvider()));
        }
    }
}
