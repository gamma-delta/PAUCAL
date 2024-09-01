package at.petrak.paucal.forge.datagen;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class PaucalDatagen {
  @SubscribeEvent
  public static void onInitializeDataGenerator(GatherDataEvent evt) {
    var gen = evt.getGenerator();
    if (System.getProperty("paucal.xplat_datagen") != null) {
      gen.addProvider(evt.includeServer(),
          new AdvancementProvider(gen.getPackOutput(), evt.getLookupProvider(), evt.getExistingFileHelper(),
              List.of(new ModAdvancementGenerator())));
    }
  }
}
