package at.petrak.paucal.api.datagen;

import at.petrak.paucal.api.mixin.AccessorTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

// ForgeCopy, mostly
abstract public class PaucalItemTagProvider extends TagsProvider<Item> {
    protected final Function<TagKey<Block>, TagBuilder> getBuilder;

    protected PaucalItemTagProvider(PackOutput pack, CompletableFuture<HolderLookup.Provider> lookup, String modid,
        TagsProvider<Block> getBuilder) {
        super(pack, Registries.ITEM, lookup);
        this.getBuilder = ((AccessorTagsProvider<Block>) getBuilder)::paucal$getOrCreateRawBuilder;
    }

    protected void copy(TagKey<Block> from, TagKey<Item> to) {
        var itemTagBuilder = this.getOrCreateRawBuilder(to);
        var blockTagBuilder = this.getBuilder.apply(from);
        // `.build` does not actually mutate anything
        for (TagEntry blockTag : blockTagBuilder.build()) {
            itemTagBuilder.add(blockTag);
        }
    }
}
