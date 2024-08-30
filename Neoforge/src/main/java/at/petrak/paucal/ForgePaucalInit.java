package at.petrak.paucal;

import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.common.ContributorsManifest;
import at.petrak.paucal.common.command.ModCommands;
import at.petrak.paucal.common.misc.NewWorldMessage;
import at.petrak.paucal.common.misc.PatPat;
import net.minecraft.world.InteractionResult;
import net.neoforged.bus.EventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@Mod(PaucalAPI.MOD_ID)
public class ForgePaucalInit {
  public ForgePaucalInit(EventBus modBus) {
    PaucalMod.initialize();

    var evBus = NeoForge.EVENT_BUS;

    // We have to do these at some point when the registries are still open
    modBus.addListener((FMLLoadCompleteEvent evt) -> {
      // Config is loaded later in 1.19 forg, hopefully the loadcomplete is late enough
      ContributorsManifest.loadContributors();
    });

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
