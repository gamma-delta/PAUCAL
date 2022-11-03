package at.petrak.paucal.forge.xplat;

import at.petrak.paucal.forge.mixin.ForgeAccessorRecipeProvider;
import at.petrak.paucal.xplat.IXplatAbstractions;
import at.petrak.paucal.xplat.Platform;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class ForgeXplatImpl implements IXplatAbstractions {
    @Override
    public Platform platform() {
        return Platform.FORGE;
    }

    @Override
    public @Nullable SoundEvent getSoundByID(ResourceLocation id) {
        return ForgeRegistries.SOUND_EVENTS.getValue(id);
    }

    @Override
    public void saveRecipeAdvancement(DataGenerator generator, CachedOutput cache, JsonObject json, Path path) {
        // this is dumb
        ((ForgeAccessorRecipeProvider) new RecipeProvider(generator)).paucal$saveRecipeAdvancement(cache, json, path);
    }
}
