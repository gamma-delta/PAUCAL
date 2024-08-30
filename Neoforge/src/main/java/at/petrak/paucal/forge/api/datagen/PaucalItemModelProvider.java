package at.petrak.paucal.forge.api.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class PaucalItemModelProvider extends ItemModelProvider {
  public PaucalItemModelProvider(PackOutput output, String modid,
      ExistingFileHelper existingFileHelper) {
    super(output, modid, existingFileHelper);
  }

  protected void simpleItem(Item item) {
    simpleItem(BuiltInRegistries.ITEM.getKey(item));
  }

  protected void simpleItem(ResourceLocation path) {
    singleTexture(path.getPath(), ResourceLocation.parse("item/generated"),
        "layer0", modLoc("item/" + path.getPath()));
  }

  protected void brandishedItem(Item item) {
    brandishedItem(BuiltInRegistries.ITEM.getKey(item));
  }

  protected void brandishedItem(ResourceLocation path) {
    singleTexture(path.getPath(), ResourceLocation.parse("item/handheld"),
        "layer0", modLoc("item/" + path.getPath()));
  }
}
