package at.petrak.paucal.common.advancement;

import at.petrak.paucal.PaucalMod;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = PaucalMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModAdvancementTriggers {
    public static final BeContributorTrigger BE_CONTRIBUTOR_TRIGGER = new BeContributorTrigger();

    @SubscribeEvent
    public static void registerTriggers(FMLCommonSetupEvent evt) {
        evt.enqueueWork(() -> {
            CriteriaTriggers.register(BE_CONTRIBUTOR_TRIGGER);
        });
    }
}
