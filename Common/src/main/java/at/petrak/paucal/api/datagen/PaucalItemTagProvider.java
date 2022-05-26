package at.petrak.paucal.api.datagen;

import at.petrak.paucal.api.mixin.AccessorTagsProvider;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

// ForgeCopy, mostly
abstract public class PaucalItemTagProvider extends TagsProvider<Item> {
    protected final Function<TagKey<Block>, Tag.Builder> blockTags;

    protected PaucalItemTagProvider(DataGenerator gen, TagsProvider<Block> blockTags) {
        super(gen, Registry.ITEM);
        this.blockTags = ((AccessorTagsProvider<Block>) blockTags)::paucal$getOrCreateRawBuilder;
    }

    protected void copy(TagKey<Block> from, TagKey<Item> to) {
        Tag.Builder tag$builder = this.getOrCreateRawBuilder(to);
        Tag.Builder tag$builder1 = this.blockTags.apply(from);
        tag$builder1.getEntries().forEach(tag$builder::add);
    }
}
