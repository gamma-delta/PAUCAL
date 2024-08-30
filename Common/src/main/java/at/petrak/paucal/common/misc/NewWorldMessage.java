package at.petrak.paucal.common.misc;

import at.petrak.paucal.common.ModRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class NewWorldMessage {
  public static void onLogin(Player player) {
    if (player instanceof ServerPlayer splayer) {
      ModRegistries.BE_CONTRIBUTOR_TRIGGER.get().trigger(splayer);
    }
  }
}
