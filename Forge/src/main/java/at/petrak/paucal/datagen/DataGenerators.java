package at.petrak.paucal.datagen;

import at.petrak.paucal.api.PaucalAPI;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = PaucalAPI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent evt) {
        var gen = evt.getGenerator();
        var efh = evt.getExistingFileHelper();

        if (System.getProperty("paucal.xplat_datagen") != null) {
            if (evt.includeServer()) {
                gen.addProvider(new ModAdvancementProvider(gen, efh));
            }
        }
    }
}
