package at.petrak.paucal.api.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public abstract class PaucalRecipeProvider extends RecipeProvider {
    protected final String modid;

    public PaucalRecipeProvider(DataGenerator pGenerator, String modid) {
        super(pGenerator);
        this.modid = modid;
    }

    protected ShapedRecipeBuilder ring(ItemLike out, int count, Ingredient outer, @Nullable Ingredient inner) {
        return ringCornered(out, count, outer, outer, inner);
    }

    protected ShapedRecipeBuilder ring(ItemLike out, int count, ItemLike outer, @Nullable ItemLike inner) {
        return ring(out, count, Ingredient.of(outer), ingredientOf(inner));
    }

    protected ShapedRecipeBuilder ring(ItemLike out, int count, TagKey<Item> outer, @Nullable TagKey<Item> inner) {
        return ring(out, count, Ingredient.of(outer), ingredientOf(inner));
    }

    protected ShapedRecipeBuilder ringCornerless(ItemLike out, int count, Ingredient outer,
        @Nullable Ingredient inner) {
        return ringCornered(out, count, outer, null, inner);
    }

    protected ShapedRecipeBuilder ringCornerless(ItemLike out, int count, ItemLike outer, @Nullable ItemLike inner) {
        return ringCornerless(out, count, Ingredient.of(outer), ingredientOf(inner));
    }

    protected ShapedRecipeBuilder ringCornerless(ItemLike out, int count, TagKey<Item> outer,
        @Nullable TagKey<Item> inner) {
        return ringCornerless(out, count, Ingredient.of(outer), ingredientOf(inner));
    }

    protected ShapedRecipeBuilder ringAll(ItemLike out, int count, Ingredient outer, @Nullable Ingredient inner) {
        return ringCornered(out, count, outer, outer, inner);
    }

    protected ShapedRecipeBuilder ringAll(ItemLike out, int count, ItemLike outer, @Nullable ItemLike inner) {
        return ringAll(out, count, Ingredient.of(outer), ingredientOf(inner));
    }

    protected ShapedRecipeBuilder ringAll(ItemLike out, int count, TagKey<Item> outer, @Nullable TagKey<Item> inner) {
        return ringAll(out, count, Ingredient.of(outer), ingredientOf(inner));
    }

    protected ShapedRecipeBuilder ringCornered(ItemLike out, int count, @Nullable Ingredient cardinal,
        @Nullable Ingredient diagonal, @Nullable Ingredient inner) {
        if (cardinal == null && diagonal == null && inner == null) {
            throw new IllegalArgumentException("at least one ingredient must be non-null");
        }
        if (inner != null && cardinal == null && diagonal == null) {
            throw new IllegalArgumentException("if inner is non-null, either cardinal or diagonal must not be");
        }

        var builder = ShapedRecipeBuilder.shaped(out, count);
        var C = ' ';
        if (cardinal != null) {
            builder.define('C', cardinal);
            C = 'C';
        }
        var D = ' ';
        if (diagonal != null) {
            builder.define('D', diagonal);
            D = 'D';
        }
        var I = ' ';
        if (inner != null) {
            builder.define('I', inner);
            I = 'I';
        }

        builder
            .pattern(String.format("%c%c%c", D, C, D))
            .pattern(String.format("%c%c%c", C, I, C))
            .pattern(String.format("%c%c%c", D, C, D));

        return builder;
    }

    protected ShapedRecipeBuilder ringCornered(ItemLike out, int count, @Nullable ItemLike cardinal,
        @Nullable ItemLike diagonal, @Nullable ItemLike inner) {
        return ringCornered(out, count, ingredientOf(cardinal), ingredientOf(diagonal), ingredientOf(inner));
    }

    protected ShapedRecipeBuilder ringCornered(ItemLike out, int count, @Nullable TagKey<Item> cardinal,
        @Nullable TagKey<Item> diagonal, @Nullable TagKey<Item> inner) {
        return ringCornered(out, count, ingredientOf(cardinal), ingredientOf(diagonal), ingredientOf(inner));
    }

    protected ShapedRecipeBuilder stack(ItemLike out, int count, Ingredient top, Ingredient bottom) {
        return ShapedRecipeBuilder.shaped(out, count)
            .define('T', top)
            .define('B', bottom)
            .pattern("T")
            .pattern("B");
    }

    protected ShapedRecipeBuilder stack(ItemLike out, int count, ItemLike top, ItemLike bottom) {
        return stack(out, count, Ingredient.of(top), Ingredient.of(bottom));
    }

    protected ShapedRecipeBuilder stack(ItemLike out, int count, TagKey<Item> top, TagKey<Item> bottom) {
        return stack(out, count, Ingredient.of(top), Ingredient.of(bottom));
    }


    protected ShapedRecipeBuilder stick(ItemLike out, int count, Ingredient input) {
        return stack(out, count, input, input);
    }

    protected ShapedRecipeBuilder stick(ItemLike out, int count, ItemLike input) {
        return stick(out, count, Ingredient.of(input));
    }

    protected ShapedRecipeBuilder stick(ItemLike out, int count, TagKey<Item> input) {
        return stick(out, count, Ingredient.of(input));
    }

    /**
     * @param largeSize True for a 3x3, false for a 2x2
     */
    protected void packing(ItemLike free, ItemLike compressed, String freeName, boolean largeSize,
        Consumer<FinishedRecipe> recipes) {
        var pack = ShapedRecipeBuilder.shaped(compressed)
            .define('X', free);
        if (largeSize) {
            pack.pattern("XXX").pattern("XXX").pattern("XXX");
        } else {
            pack.pattern("XX").pattern("XX");
        }
        pack.unlockedBy("has_item", has(free)).save(recipes, modLoc(freeName + "_packing"));

        ShapelessRecipeBuilder.shapeless(free, largeSize ? 9 : 4)
            .requires(compressed)
            .unlockedBy("has_item", has(free)).save(recipes, modLoc(freeName + "_unpacking"));
    }

    protected ResourceLocation modLoc(String path) {
        return new ResourceLocation(modid, path);
    }

    @Nullable
    protected Ingredient ingredientOf(@Nullable ItemLike item) {
        return item == null ? null : Ingredient.of(item);
    }

    @Nullable
    protected Ingredient ingredientOf(@Nullable TagKey<Item> item) {
        return item == null ? null : Ingredient.of(item);
    }
}
