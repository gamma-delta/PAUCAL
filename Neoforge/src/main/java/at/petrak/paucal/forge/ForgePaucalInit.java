package at.petrak.paucal.forge;

import at.petrak.paucal.xplat.PaucalMod;
import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.xplat.common.command.ModCommands;
import at.petrak.paucal.xplat.common.misc.NewWorldMessage;
import at.petrak.paucal.xplat.common.misc.PatPat;
import net.minecraft.world.InteractionResult;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@Mod(PaucalAPI.MOD_ID)
public class ForgePaucalInit {
  public ForgePaucalInit(IEventBus modBus) {
    PaucalMod.initialize();

    var evBus = NeoForge.EVENT_BUS;

    evBus.addListener((PlayerInteractEvent.EntityInteract evt) -> {
      var result = PatPat.onPat(evt.getEntity(), evt.getLevel(), evt.getHand(), evt.getTarget(), null);
      if (result == InteractionResult.SUCCESS) {
        evt.setCanceled(true);
        evt.setCancellationResult(InteractionResult.SUCCESS);
      }
    });
    evBus.addListener((RegisterCommandsEvent evt) -> {
      ModCommands.register(evt.getDispatcher());
    });
    evBus.addListener((PlayerEvent.PlayerLoggedInEvent evt) -> {
      NewWorldMessage.onLogin(evt.getEntity());
    });
  }
}
