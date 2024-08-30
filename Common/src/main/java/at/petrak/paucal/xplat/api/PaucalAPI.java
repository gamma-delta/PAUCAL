package at.petrak.paucal.xplat.api;

import at.petrak.paucal.xplat.api.contrib.Contributor;
import at.petrak.paucal.xplat.common.ContributorsManifest;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public final class PaucalAPI {
  public static final String MOD_ID = "paucal";
  public static final String CONTRIBUTOR_URL =
      "https://raw.githubusercontent.com/gamma-delta/contributors/main/paucal/contributors-v01.json5";
  public static final String HEADPAT_AUDIO_URL_STUB =
      "https://raw.githubusercontent.com/gamma-delta/contributors/main/paucal/headpat-sounds/";

  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

  @Nullable
  public static Contributor getContributor(UUID uuid) {
    return ContributorsManifest.getContributor(uuid);
  }

  public static ResourceLocation modLoc(String s) {
    return ResourceLocation.tryBuild(MOD_ID, s);
  }

  public static class Codices {
    public static StreamCodec<ByteBuf, Vec3> VEC3 = StreamCodec.of(
        (stream, v) -> {
          stream.writeDouble(v.x);
          stream.writeDouble(v.y);
          stream.writeDouble(v.x);
        },
        (stream) -> new Vec3(stream.readDouble(), stream.readDouble(), stream.readDouble())
    );
  }
}
