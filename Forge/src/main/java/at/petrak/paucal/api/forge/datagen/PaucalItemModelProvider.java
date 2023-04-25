package at.petrak.paucal.api.forge.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class PaucalItemModelProvider extends ItemModelProvider {
    public PaucalItemModelProvider(PackOutput out, String modid,
                                   ExistingFileHelper existingFileHelper) {
        super(out, modid, existingFileHelper);
    }

    protected void simpleItem(Item item) {
        simpleItem(ForgeRegistries.ITEMS.getKey(item));
    }

    protected void simpleItem(ResourceLocation path) {
        singleTexture(path.getPath(), new ResourceLocation("item/generated"),
            "layer0", modLoc("item/" + path.getPath()));
    }

    protected void brandishedItem(Item item) {
        brandishedItem(ForgeRegistries.ITEMS.getKey(item));
    }

    protected void brandishedItem(ResourceLocation path) {
        singleTexture(path.getPath(), new ResourceLocation("item/handheld"),
            "layer0", modLoc("item/" + path.getPath()));
    }
}
