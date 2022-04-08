package at.petrak.paucal.common.misc;

import at.petrak.paucal.PaucalMod;
import at.petrak.paucal.common.advancement.ModAdvancementTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PaucalMod.MOD_ID)
public class NewWorldMessage {
    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent evt) {
        if (evt.getPlayer() instanceof ServerPlayer splayer) {
            ModAdvancementTriggers.BE_CONTRIBUTOR_TRIGGER.trigger(splayer);
        }
    }
}
