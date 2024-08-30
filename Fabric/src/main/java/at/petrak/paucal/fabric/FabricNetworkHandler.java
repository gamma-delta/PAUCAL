package at.petrak.paucal.fabric;

import at.petrak.paucal.xplat.common.msg.MsgHeadpatSoundS2C;
import at.petrak.paucal.xplat.common.msg.MsgReloadContributorsS2C;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class FabricNetworkHandler {
  public static void init() {
    PayloadTypeRegistry.playS2C().register(MsgHeadpatSoundS2C.TYPE, MsgHeadpatSoundS2C.CODEC);
    PayloadTypeRegistry.playS2C().register(MsgReloadContributorsS2C.TYPE, MsgReloadContributorsS2C.CODEC);
  }

  public static void initClient() {
    ClientPlayNetworking.registerReceiver(MsgHeadpatSoundS2C.TYPE,
        (pkt, ctx) -> MsgHeadpatSoundS2C.handle(pkt));
    ClientPlayNetworking.registerReceiver(MsgReloadContributorsS2C.TYPE,
        (pkt, ctx) -> MsgReloadContributorsS2C.handle(pkt));
  }
}
