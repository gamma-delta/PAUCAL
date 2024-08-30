package at.petrak.paucal.xplat.common;

import at.petrak.paucal.xplat.api.PaucalAPI;
import at.petrak.paucal.xplat.common.advancement.BeContributorTrigger;
import at.petrak.paucal.xplat.common.sounds.HeadpatSoundInstance;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.StatFormatter;

import static at.petrak.paucal.xplat.api.PaucalAPI.modLoc;

public class ModRegistries {
  public static final DeferredRegister<CriterionTrigger<?>> TRIGGER_TYPES =
      DeferredRegister.create(PaucalAPI.MOD_ID, Registries.TRIGGER_TYPE);
  public static final RegistrySupplier<BeContributorTrigger> BE_CONTRIBUTOR_TRIGGER =
      TRIGGER_TYPES.register("be_contributor", BeContributorTrigger::new);

  public static final DeferredRegister<SoundEvent> SOUNDS =
      DeferredRegister.create(PaucalAPI.MOD_ID, Registries.SOUND_EVENT);
  public static final RegistrySupplier<SoundEvent> HEADPAT_SOUND =
      SOUNDS.register("headpat",
          () -> SoundEvent.createVariableRangeEvent(modLoc(HeadpatSoundInstance.DUMMY_LOCATION)));

  public static final DeferredRegister<ResourceLocation> STATS =
      DeferredRegister.create(PaucalAPI.MOD_ID, Registries.CUSTOM_STAT);
  public static final RegistrySupplier<ResourceLocation> PLAYERS_PATTED =
      makeCustomStat("players_patted", StatFormatter.DEFAULT);
  public static final RegistrySupplier<ResourceLocation> HEADPATS_GOTTEN =
      makeCustomStat("headpats_gotten", StatFormatter.DEFAULT);

  private static RegistrySupplier<ResourceLocation> makeCustomStat(String pKey, StatFormatter pFormatter) {
    ResourceLocation rl = PaucalAPI.modLoc(pKey);
    var out = STATS.register(pKey, () -> {
      return rl;
    });
    return out;
  }
}
