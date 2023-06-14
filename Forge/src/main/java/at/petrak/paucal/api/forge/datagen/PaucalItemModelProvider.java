package at.petrak.paucal.api.forge.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public abstract class PaucalItemModelProvider extends ItemModelProvider {
    public PaucalItemModelProvider(PackOutput output, String modid,
                                   ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    protected void simpleItem(Item item) {
        simpleItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
    }

    protected void simpleItem(ResourceLocation path) {
        singleTexture(path.getPath(), new ResourceLocation("item/generated"),
            "layer0", modLoc("item/" + path.getPath()));
    }

    protected void brandishedItem(Item item) {
        brandishedItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
    }

    protected void brandishedItem(ResourceLocation path) {
        singleTexture(path.getPath(), new ResourceLocation("item/handheld"),
            "layer0", modLoc("item/" + path.getPath()));
    }
}
