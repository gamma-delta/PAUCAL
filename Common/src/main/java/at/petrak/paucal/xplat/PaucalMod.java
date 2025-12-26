package at.petrak.paucal.xplat;

import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.xplat.common.ContributorsManifest;
import at.petrak.paucal.xplat.common.ModRegistries;
import at.petrak.paucal.xplat.common.msg.MsgHeadpatSoundS2C;
import at.petrak.paucal.xplat.common.msg.MsgReloadContributorsS2C;
import dev.architectury.impl.NetworkAggregator;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class PaucalMod {
  public static final Logger LOGGER = LoggerFactory.getLogger(PaucalAPI.MOD_ID);

  public static void initialize() {
    ModRegistries.TRIGGER_TYPES.register();
    ModRegistries.SOUNDS.register();
    ModRegistries.STATS.register();
    PaucalGamerules.init();

    ContributorsManifest.loadContributors();

      registerS2C(MsgHeadpatSoundS2C.TYPE, MsgHeadpatSoundS2C.CODEC, (pkt, buf) -> MsgHeadpatSoundS2C.handle(pkt));
      registerS2C(MsgReloadContributorsS2C.TYPE, MsgReloadContributorsS2C.CODEC, (pkt, buf) -> MsgReloadContributorsS2C.handle(pkt));
  }
    // https://github.com/Buuz135/FindMe/commit/b73519f2d9918e3dfb9b2655178230270dcdd7e0
    private static <T extends CustomPacketPayload> void registerS2C(CustomPacketPayload.Type<T> packetType, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, NetworkManager.NetworkReceiver<T> receiver) {
        if (Platform.getEnvironment().equals(Env.SERVER)) {
            NetworkAggregator.registerS2CType(packetType, codec, List.of());
        } else {
            NetworkAggregator.registerReceiver(NetworkManager.s2c(), packetType, codec, Collections.emptyList(), receiver);
        }
    }
}
