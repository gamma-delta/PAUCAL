package at.petrak.paucal.api.datagen;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;

/**
 * {@code PaucalBlocklTagProvider}
 */
abstract public class PaucalBlockTagProvider extends TagsProvider<Block> {
    protected PaucalBlockTagProvider(DataGenerator gen) {
        super(gen, Registry.BLOCK);
    }
}
