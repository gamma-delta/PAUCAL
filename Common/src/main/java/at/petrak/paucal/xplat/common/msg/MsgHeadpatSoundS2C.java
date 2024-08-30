package at.petrak.paucal.xplat.common.msg;

import at.petrak.paucal.xplat.api.PaucalAPI;
import at.petrak.paucal.xplat.common.sounds.HeadpatSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;

import static at.petrak.paucal.xplat.api.PaucalAPI.modLoc;

public record MsgHeadpatSoundS2C(String soundName, boolean isGithub, Vec3 pos,
                                 float pitch, Optional<UUID> patter) implements CustomPacketPayload {
  public static final Type<MsgHeadpatSoundS2C> TYPE = new Type<>(modLoc("pat"));

  public static final StreamCodec<RegistryFriendlyByteBuf, MsgHeadpatSoundS2C> CODEC = StreamCodec.composite(
      ByteBufCodecs.STRING_UTF8, MsgHeadpatSoundS2C::soundName,
      ByteBufCodecs.BOOL, MsgHeadpatSoundS2C::isGithub,
      PaucalAPI.Codices.VEC3, MsgHeadpatSoundS2C::pos,
      ByteBufCodecs.FLOAT, MsgHeadpatSoundS2C::pitch,
      ByteBufCodecs.optional(UUIDUtil.STREAM_CODEC), MsgHeadpatSoundS2C::patter,
      MsgHeadpatSoundS2C::new
  );

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  public static void handle(MsgHeadpatSoundS2C self) {
    Minecraft.getInstance().execute(new Runnable() {
      @Override
      public void run() {
        var sound = new HeadpatSoundInstance(self.soundName, self.isGithub, self.pos,
            self.pitch, SoundInstance.createUnseededRandom());

        var minecraft = Minecraft.getInstance();
        var player = minecraft.player;
        if (player != null && !player.getUUID().equals(self.patter.orElse(null))) {
          minecraft.getSoundManager().play(sound);
        }
      }
    });
  }
}
