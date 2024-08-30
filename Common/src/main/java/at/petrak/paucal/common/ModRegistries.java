package at.petrak.paucal.common;

import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.common.advancement.BeContributorTrigger;
import at.petrak.paucal.common.sounds.HeadpatSoundInstance;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

import static at.petrak.paucal.api.PaucalAPI.modLoc;

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
}
