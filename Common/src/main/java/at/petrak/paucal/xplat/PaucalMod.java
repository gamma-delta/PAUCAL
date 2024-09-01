package at.petrak.paucal.xplat;

import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.xplat.common.ContributorsManifest;
import at.petrak.paucal.xplat.common.ModRegistries;
import at.petrak.paucal.xplat.common.msg.MsgHeadpatSoundS2C;
import at.petrak.paucal.xplat.common.msg.MsgReloadContributorsS2C;
import dev.architectury.networking.NetworkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaucalMod {
  public static final Logger LOGGER = LoggerFactory.getLogger(PaucalAPI.MOD_ID);

  public static void initialize() {
    ModRegistries.TRIGGER_TYPES.register();
    ModRegistries.SOUNDS.register();
    ModRegistries.STATS.register();
    PaucalGamerules.init();

    ContributorsManifest.loadContributors();

    NetworkManager.registerReceiver(NetworkManager.s2c(), MsgHeadpatSoundS2C.TYPE, MsgHeadpatSoundS2C.CODEC,
        (pkt, buf) -> MsgHeadpatSoundS2C.handle(pkt));
    NetworkManager.registerReceiver(NetworkManager.s2c(), MsgReloadContributorsS2C.TYPE, MsgReloadContributorsS2C.CODEC,
        (pkt, buf) -> MsgReloadContributorsS2C.handle(pkt));
  }
}
