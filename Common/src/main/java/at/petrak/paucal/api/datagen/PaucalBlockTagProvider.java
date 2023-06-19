package at.petrak.paucal.api.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

/**
 * {@code PaucalBlocklTagProvider}
 */
abstract public class PaucalBlockTagProvider extends TagsProvider<Block> {
    protected PaucalBlockTagProvider(PackOutput packOut, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOut, Registries.BLOCK, lookupProvider);
    }

    void add(TagAppender<Block> appender, Block... blocks) {
        for (Block block : blocks) {
            appender.add(BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow());
        }
    }
}
