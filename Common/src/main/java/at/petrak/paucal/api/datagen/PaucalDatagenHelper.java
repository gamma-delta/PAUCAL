package at.petrak.paucal.api.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.advancements.AdvancementProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PaucalDatagenHelper {
    /**
     * Returns a factory.
     */
    public static DataProvider.Factory<AdvancementProvider> wrapAdvancementSubProviders(CompletableFuture<HolderLookup.Provider> lookup, PaucalAdvancementSubProvider... providers) {
        return packOut -> new AdvancementProvider(packOut, lookup, List.of(providers));
    }
}
