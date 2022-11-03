package at.petrak.paucal.xplat;

import at.petrak.paucal.api.PaucalAPI;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public interface IXplatAbstractions {
    Platform platform();

    @Nullable SoundEvent getSoundByID(ResourceLocation id);

    // This is technically not needed over xplat but so I have to rewrite less ... i'll implement it here
    default ResourceLocation getID(Block block) {
        return Registry.BLOCK.getKey(block);
    }

    default ResourceLocation getID(Item item) {
        return Registry.ITEM.getKey(item);
    }

    void saveRecipeAdvancement(DataGenerator generator, CachedOutput cache, JsonObject json, Path path);

    default void init() {
        PaucalAPI.LOGGER.info("Hello PAUCAL! This is {}!", this.platform());
    }

    IXplatAbstractions INSTANCE = find();

    private static IXplatAbstractions find() {
        var providers = ServiceLoader.load(IXplatAbstractions.class).stream().toList();
        if (providers.size() != 1) {
            var names = providers.stream().map(p -> p.type().getName()).collect(Collectors.joining(",", "[", "]"));
            throw new IllegalStateException(
                "There should be exactly one IXplatAbstractions implementation on the classpath. Found: " + names);
        } else {
            var provider = providers.get(0);
            PaucalAPI.LOGGER.debug("Instantiating xplat impl: " + provider.type().getName());
            return provider.get();
        }
    }
}
