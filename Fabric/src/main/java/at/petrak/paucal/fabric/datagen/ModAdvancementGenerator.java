package at.petrak.paucal.fabric.datagen;

import at.petrak.paucal.common.advancement.BeContributorTrigger;
import at.petrak.paucal.common.advancement.ModAdvancementTriggers;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static at.petrak.paucal.api.PaucalAPI.modLoc;

public class ModAdvancementGenerator extends FabricAdvancementProvider {
  protected ModAdvancementGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(output, registryLookup);
  }

  @Override
  public void generateAdvancement(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
    Advancement.Builder.advancement()
        .addCriterion("on_login", ModAdvancementTriggers.BE_CONTRIBUTOR_TRIGGER.get().createCriterion(
            new BeContributorTrigger.Instance(
                Optional.empty(),
                MinMaxBounds.Ints.atLeast(1), null)))
        .rewards(AdvancementRewards.Builder.function(modLoc("welcome")))
        .save(consumer, "paucal:be_patron");
  }
}
