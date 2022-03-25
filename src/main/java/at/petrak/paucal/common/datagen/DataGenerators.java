package at.petrak.paucal.common.datagen;

import at.petrak.paucal.PaucalMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

/**
 * Test class for data generators
 */
@Mod.EventBusSubscriber(modid = PaucalMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    // I MUST REMEMBER TO TURN THIS OFF BEFORE PUSHING ANYTHING
    public static final boolean doTestDatagen = false;

    @SubscribeEvent
    public static void gatherData(GatherDataEvent evt) {
        var gen = evt.getGenerator();
        var efh = evt.getExistingFileHelper();
        if (doTestDatagen) {
            if (evt.includeServer()) {
                gen.addProvider(new ModTestLootModProvider(gen));
            }
        }
    }
}
