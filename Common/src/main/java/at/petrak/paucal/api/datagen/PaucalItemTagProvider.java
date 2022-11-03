package at.petrak.paucal.api.datagen;

import at.petrak.paucal.api.mixin.AccessorTagsProvider;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

// ForgeCopy, mostly
abstract public class PaucalItemTagProvider extends TagsProvider<Item> {
    protected final Function<TagKey<Block>, TagBuilder> getBuilder;

    protected PaucalItemTagProvider(DataGenerator gen, TagsProvider<Block> getBuilder) {
        super(gen, Registry.ITEM);
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
