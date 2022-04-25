package at.petrak.paucal.api.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class PaucalItemModelProvider extends ItemModelProvider {
    public PaucalItemModelProvider(DataGenerator generator, String modid,
        ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    protected void simpleItem(Item item) {
        simpleItem(item.getRegistryName());
    }

    protected void simpleItem(ResourceLocation path) {
        singleTexture(path.getPath(), new ResourceLocation("item/generated"),
            "layer0", modLoc("item/" + path.getPath()));
    }

    protected void brandishedItem(Item item) {
        brandishedItem(item.getRegistryName());
    }

    protected void brandishedItem(ResourceLocation path) {
        singleTexture(path.getPath(), new ResourceLocation("item/handheld"),
            "layer0", modLoc("item/" + path.getPath()));
    }
}
