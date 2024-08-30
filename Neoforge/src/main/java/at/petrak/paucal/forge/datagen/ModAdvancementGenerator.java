package at.petrak.paucal.forge.datagen;

import at.petrak.paucal.xplat.common.ModRegistries;
import at.petrak.paucal.xplat.common.advancement.BeContributorTrigger;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Optional;
import java.util.function.Consumer;

import static at.petrak.paucal.xplat.api.PaucalAPI.modLoc;

public class ModAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
  @Override
  public void generate(HolderLookup.Provider arg, Consumer<AdvancementHolder> consumer,
      ExistingFileHelper existingFileHelper) {
    Advancement.Builder.advancement()
        .addCriterion("on_login", ModRegistries.BE_CONTRIBUTOR_TRIGGER.get().createCriterion(
            new BeContributorTrigger.Instance(
                Optional.empty(),
                MinMaxBounds.Ints.atLeast(1), null)))
        .rewards(AdvancementRewards.Builder.function(modLoc("welcome")))
        .save(consumer, "paucal:be_patron");


  }
}
