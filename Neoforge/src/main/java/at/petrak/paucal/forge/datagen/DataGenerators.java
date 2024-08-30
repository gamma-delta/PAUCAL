package at.petrak.paucal.forge.datagen;

import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.api.datagen.PaucalDatagenHelper;
import at.petrak.paucal.datagen.ModAdvancementSubProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = PaucalAPI.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
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
