package at.petrak.paucal.api.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class PaucalBlockStateAndModelProvider extends BlockStateProvider {
    public PaucalBlockStateAndModelProvider(DataGenerator gen, String modid,
        ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    protected void blockAndItem(Block block, BlockModelBuilder model) {
        simpleBlock(block, model);
        simpleBlockItem(block, model);
    }

    protected void cubeBlockAndItem(Block block, String name) {
        blockAndItem(block, models().cubeAll(name, modLoc("block/" + name)));
    }
}
