package at.petrak.paucal.xplat.common.msg;

import at.petrak.paucal.xplat.api.PaucalAPI;
import at.petrak.paucal.xplat.common.ContributorsManifest;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import static at.petrak.paucal.xplat.api.PaucalAPI.modLoc;

// The command is only run on the server of course so we need to the clients to know too
public record MsgReloadContributorsS2C() implements CustomPacketPayload {
  public static final Type<MsgReloadContributorsS2C> TYPE = new Type<>(modLoc("rlpat"));

  public static final StreamCodec<RegistryFriendlyByteBuf, MsgReloadContributorsS2C> CODEC =
      StreamCodec.unit(new MsgReloadContributorsS2C());

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  public static void handle(MsgReloadContributorsS2C self) {
    Minecraft.getInstance().execute(new Runnable() {
      @Override
      public void run() {
        PaucalAPI.LOGGER.info("Ordered by server to reload contributors");
        ContributorsManifest.loadContributors();
      }
    });
  }
}
