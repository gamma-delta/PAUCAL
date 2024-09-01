package at.petrak.paucal.forge.datagen;

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

import static at.petrak.paucal.api.PaucalAPI.modLoc;

public class ModAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
  @Override
  public void generate(HolderLookup.Provider arg, Consumer<AdvancementHolder> consumer,
                       ExistingFileHelper existingFileHelper) {
    // turn off telemetry with recipeAdvancement
    Advancement.Builder.recipeAdvancement()
        .addCriterion("on_login", new BeContributorTrigger.Instance(
            Optional.empty(),
            MinMaxBounds.Ints.atLeast(1),
            Optional.empty()).criterion())
        .rewards(AdvancementRewards.Builder.function(modLoc("welcome")))
        .save(consumer, "paucal:be_patron");
  }
}
