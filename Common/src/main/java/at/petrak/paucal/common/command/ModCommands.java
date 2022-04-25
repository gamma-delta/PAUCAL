package at.petrak.paucal.common.command;

import at.petrak.paucal.PaucalMod;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PaucalMod.MOD_ID)
public class ModCommands {
    @SubscribeEvent
    public static void register(RegisterCommandsEvent evt) {
        var dp = evt.getDispatcher();
        CommandGetContributorInfo.register(dp);
    }
}
