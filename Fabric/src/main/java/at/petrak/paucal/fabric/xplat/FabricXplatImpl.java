package at.petrak.paucal.fabric.xplat;

import at.petrak.paucal.xplat.IXplatAbstractions;
import at.petrak.paucal.xplat.Platform;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class FabricXplatImpl implements IXplatAbstractions {
    @Override
    public Platform platform() {
        return Platform.FABRIC;
    }

    @Override
    public @Nullable SoundEvent getSoundByID(ResourceLocation id) {
        return Registry.SOUND_EVENT.get(id);
    }

    @Override
    public ResourceLocation getID(Block block) {
        return Registry.BLOCK.getKey(block);
    }

    @Override
    public ResourceLocation getID(Item item) {
        return Registry.ITEM.getKey(item);
    }

    @Override
    public void saveRecipeAdvancement(DataGenerator generator, CachedOutput cache, JsonObject json, Path path) {
        RecipeProvider.saveAdvancement(cache, json, path);
    }
}
