package at.petrak.paucal.api.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

/**
 * {@code PaucalBlocklTagProvider}
 */
abstract public class PaucalBlockTagProvider extends TagsProvider<Block> {
    protected PaucalBlockTagProvider(PackOutput out, CompletableFuture<HolderLookup.Provider> provider) {
        super(out, Registries.BLOCK, provider);
    }
}
