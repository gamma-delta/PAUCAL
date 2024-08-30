package at.petrak.paucal;

import at.petrak.paucal.common.ContributorsManifest;
import at.petrak.paucal.common.ModRegistries;
import at.petrak.paucal.common.ModStats;
import at.petrak.paucal.common.msg.MsgHeadpatSoundS2C;
import at.petrak.paucal.common.msg.MsgReloadContributorsS2C;
import dev.architectury.networking.NetworkManager;

public class PaucalMod {
  public static void initialize() {
    ModRegistries.TRIGGER_TYPES.register();
    ModRegistries.SOUNDS.register();

    ContributorsManifest.loadContributors();
    ModStats.register();

    NetworkManager.registerReceiver(NetworkManager.c2s(), MsgHeadpatSoundS2C.TYPE, MsgHeadpatSoundS2C.CODEC,
        (pkt, buf) -> MsgHeadpatSoundS2C.handle(pkt));
    NetworkManager.registerReceiver(NetworkManager.c2s(), MsgReloadContributorsS2C.TYPE, MsgReloadContributorsS2C.CODEC,
        (pkt, buf) -> MsgReloadContributorsS2C.handle(pkt));
  }
}
