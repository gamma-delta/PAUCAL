package at.petrak.paucal.common.advancement;

import at.petrak.paucal.api.PaucalAPI;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;

import java.util.function.Supplier;

public class ModAdvancementTriggers {
  public static final DeferredRegister<CriterionTrigger<?>> TRIGGER_TYPES =
      DeferredRegister.create(PaucalAPI.MOD_ID, Registries.TRIGGER_TYPE);

  public static final Supplier<BeContributorTrigger> BE_CONTRIBUTOR_TRIGGER =
      TRIGGER_TYPES.register("be_contributor", BeContributorTrigger::new);
}
