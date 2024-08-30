package at.petrak.paucal.api.forge.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.world.level.block.Block;

public abstract class PaucalBlockStateAndModelProvider extends FabricModelProvider {
  public PaucalBlockStateAndModelProvider(FabricDataOutput out) {
    super(out);
  }

  protected void blockAndItem(Block block, BlockModelBuilder model) {
    simpleBlock(block);
    simpleBlockItem(block, model);
  }

  protected void cubeBlockAndItem(Block block, String name) {
    blockAndItem(block, models().cubeAll(name, modLoc("block/" + name)));
  }
}
