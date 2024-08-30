package at.petrak.paucal.api.forge.datagen;

import at.petrak.paucal.forge.mixin.ForgeAccessorAdvancementProvider;
import at.petrak.paucal.forge.mixin.ForgeAccessorTagsProvider;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * Inject {@link net.minecraftforge.common.data.ExistingFileHelper ExistingFileHelper}s in when needed.
 */
public final class PaucalForgeDatagenWrappers {
    private PaucalForgeDatagenWrappers() {
    }

    public static <P extends AdvancementProvider> P addEFHToAdvancements(P advs, ExistingFileHelper efh) {
        ((ForgeAccessorAdvancementProvider) advs).paucal$setFileHelper(efh);
        return advs;
    }

    public static <T, P extends TagsProvider<T>> P addEFHToTagProvider(P tags, ExistingFileHelper efh) {
        ((ForgeAccessorTagsProvider) tags).paucal$setExistingFileHelper(efh);
        return tags;
    }
}
