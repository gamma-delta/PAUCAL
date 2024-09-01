package at.petrak.paucal.fabric;

import at.petrak.paucal.xplat.PaucalMod;
import at.petrak.paucal.xplat.common.command.ModCommands;
import at.petrak.paucal.xplat.common.misc.NewWorldMessage;
import at.petrak.paucal.xplat.common.misc.PatPat;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.entity.player.Player;

public class FabricPaucalInit implements ModInitializer {
  @Override
  public void onInitialize() {
    PaucalMod.initialize();

    UseEntityCallback.EVENT.register(PatPat::onPat);
    CommandRegistrationCallback.EVENT.register((dp, _registry, _env) -> ModCommands.register(dp));
    ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
      if (entity instanceof Player player) {
        NewWorldMessage.onLogin(player);
      }
    });
  }
}
