package at.petrak.paucal.api.forge.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class PaucalBlockStateAndModelProvider extends BlockStateProvider {
  public PaucalBlockStateAndModelProvider(PackOutput out, String modId, ExistingFileHelper efh) {
    super(out, modId, efh);
  }

  protected void blockAndItem(Block block, BlockModelBuilder model) {
    simpleBlock(block);
    simpleBlockItem(block, model);
  }

  protected void cubeBlockAndItem(Block block, String name) {
    blockAndItem(block, models().cubeAll(name, modLoc("block/" + name)));
  }
}
