package at.petrak.paucal.api;

import net.minecraft.resources.ResourceLocation;

public final class PaucalAPI {
  public static final String MOD_ID = "paucal";
  public static final String CONTRIBUTOR_URL =
      "https://raw.githubusercontent.com/gamma-delta/contributors/main/paucal/contributors-v01.json5";
  public static final String HEADPAT_AUDIO_URL_STUB =
      "https://raw.githubusercontent.com/gamma-delta/contributors/main/paucal/headpat-sounds/";

  public static ResourceLocation modLoc(String s) {
    return ResourceLocation.tryBuild(MOD_ID, s);
  }
}
