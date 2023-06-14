package at.petrak.paucal.datagen;

import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.api.datagen.PaucalAdvancementSubProvider;
import at.petrak.paucal.common.advancement.BeContributorTrigger;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;

import java.util.function.Consumer;

public class ModAdvancementSubProvider extends PaucalAdvancementSubProvider {
    public ModAdvancementSubProvider() {
        super(PaucalAPI.MOD_ID);
    }

    @Override
    public void generate(HolderLookup.Provider provider, Consumer<Advancement> consumer) {
        Advancement.Builder.advancement()
            .addCriterion("on_login", new BeContributorTrigger.Instance(EntityPredicate.Composite.ANY,
                MinMaxBounds.Ints.atLeast(1), null))
            .rewards(AdvancementRewards.Builder.function(modLoc("welcome")))
            .save(consumer, prefix("be_patron"));
    }
}
