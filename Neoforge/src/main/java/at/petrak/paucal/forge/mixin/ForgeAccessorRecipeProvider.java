package at.petrak.paucal.forge.mixin;

import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.concurrent.CompletableFuture;

@Mixin(RecipeProvider.class)
public interface ForgeAccessorRecipeProvider {
    @Invoker("saveAdvancement")
    CompletableFuture<?> saveAdvancement(CachedOutput output, FinishedRecipe finishedRecipe,
        JsonObject advancementJson);
}
