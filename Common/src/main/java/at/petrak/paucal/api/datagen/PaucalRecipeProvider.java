package at.petrak.paucal.api.datagen;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

abstract public class PaucalRecipeProvider extends RecipeProvider {
    public final PackOutput packOutput;
    protected final String modid;


    public PaucalRecipeProvider(PackOutput packOutput, String modid) {
        super(packOutput);
        this.packOutput = packOutput;
        this.modid = modid;
    }

    protected ShapedRecipeBuilder ring(RecipeCategory category, ItemLike out, int count, Ingredient outer,
                                       @Nullable Ingredient inner) {
        return ringCornered(category, out, count, outer, outer, inner);
    }

    protected ShapedRecipeBuilder ring(RecipeCategory category, ItemLike out, int count, ItemLike outer,
                                       @Nullable ItemLike inner) {
        return ring(category, out, count, Ingredient.of(outer), ingredientOf(inner));
    }

    protected ShapedRecipeBuilder ring(RecipeCategory category, ItemLike out, int count, TagKey<Item> outer,
                                       @Nullable TagKey<Item> inner) {
        return ring(category, out, count, Ingredient.of(outer), ingredientOf(inner));
    }

    protected ShapedRecipeBuilder ringCornerless(RecipeCategory category, ItemLike out, int count, Ingredient outer,
                                                 @Nullable Ingredient inner) {
        return ringCornered(category, out, count, outer, null, inner);
    }

    protected ShapedRecipeBuilder ringCornerless(RecipeCategory category, ItemLike out, int count, ItemLike outer,
                                                 @Nullable ItemLike inner) {
        return ringCornerless(category, out, count, Ingredient.of(outer), ingredientOf(inner));
    }

    protected ShapedRecipeBuilder ringCornerless(RecipeCategory category, ItemLike out, int count, TagKey<Item> outer,
                                                 @Nullable TagKey<Item> inner) {
        return ringCornerless(category, out, count, Ingredient.of(outer), ingredientOf(inner));
    }

    protected ShapedRecipeBuilder ringAll(RecipeCategory category, ItemLike out, int count, Ingredient outer,
                                          @Nullable Ingredient inner) {
        return ringCornered(category, out, count, outer, outer, inner);
    }

    protected ShapedRecipeBuilder ringAll(RecipeCategory category, ItemLike out, int count, ItemLike outer,
                                          @Nullable ItemLike inner) {
        return ringAll(category, out, count, Ingredient.of(outer), ingredientOf(inner));
    }

    protected ShapedRecipeBuilder ringAll(RecipeCategory category, ItemLike out, int count, TagKey<Item> outer,
                                          @Nullable TagKey<Item> inner) {
        return ringAll(category, out, count, Ingredient.of(outer), ingredientOf(inner));
    }

    protected ShapedRecipeBuilder ringCornered(RecipeCategory category, ItemLike out, int count,
                                               @Nullable Ingredient cardinal, @Nullable Ingredient diagonal,
                                               @Nullable Ingredient inner) {
        if (cardinal == null && diagonal == null && inner == null) {
            throw new IllegalArgumentException("at least one ingredient must be non-null");
        }
        if (inner != null && cardinal == null && diagonal == null) {
            throw new IllegalArgumentException("if inner is non-null, either cardinal or diagonal must not be");
        }

        var builder = ShapedRecipeBuilder.shaped(category, out, count);
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

    protected ShapedRecipeBuilder ringCornered(RecipeCategory category, ItemLike out, int count, @Nullable ItemLike cardinal,
                                               @Nullable ItemLike diagonal, @Nullable ItemLike inner) {
        return ringCornered(category, out, count, ingredientOf(cardinal), ingredientOf(diagonal), ingredientOf(inner));
    }

    protected ShapedRecipeBuilder ringCornered(RecipeCategory category, ItemLike out, int count, @Nullable TagKey<Item> cardinal,
                                               @Nullable TagKey<Item> diagonal, @Nullable TagKey<Item> inner) {
        return ringCornered(category, out, count, ingredientOf(cardinal), ingredientOf(diagonal), ingredientOf(inner));
    }

    protected ShapedRecipeBuilder stack(RecipeCategory category, ItemLike out, int count, Ingredient top, Ingredient bottom) {
        return ShapedRecipeBuilder.shaped(category, out, count)
                .define('T', top)
                .define('B', bottom)
                .pattern("T")
                .pattern("B");
    }

    protected ShapedRecipeBuilder stack(RecipeCategory category, ItemLike out, int count, ItemLike top, ItemLike bottom) {
        return stack(category, out, count, Ingredient.of(top), Ingredient.of(bottom));
    }

    protected ShapedRecipeBuilder stack(RecipeCategory category, ItemLike out, int count, TagKey<Item> top, TagKey<Item> bottom) {
        return stack(category, out, count, Ingredient.of(top), Ingredient.of(bottom));
    }


    protected ShapedRecipeBuilder stick(RecipeCategory category, ItemLike out, int count, Ingredient input) {
        return stack(category, out, count, input, input);
    }

    protected ShapedRecipeBuilder stick(RecipeCategory category, ItemLike out, int count, ItemLike input) {
        return stick(category, out, count, Ingredient.of(input));
    }

    protected ShapedRecipeBuilder stick(RecipeCategory category, ItemLike out, int count, TagKey<Item> input) {
        return stick(category, out, count, Ingredient.of(input));
    }

    /**
     * @param largeSize True for a 3x3, false for a 2x2
     */
    protected void packing(RecipeCategory category, ItemLike free, ItemLike compressed, String freeName, boolean largeSize,
                           Consumer<FinishedRecipe> recipes) {
        var pack = ShapedRecipeBuilder.shaped(category, compressed)
                .define('X', free);
        if (largeSize) {
            pack.pattern("XXX").pattern("XXX").pattern("XXX");
        } else {
            pack.pattern("XX").pattern("XX");
        }
        pack.unlockedBy("has_item", hasItem(free)).save(recipes, modLoc(freeName + "_packing"));

        ShapelessRecipeBuilder.shapeless(category, free, largeSize ? 9 : 4)
                .requires(compressed)
                .unlockedBy("has_item", hasItem(free)).save(recipes, modLoc(freeName + "_unpacking"));
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


    protected static InventoryChangeTrigger.TriggerInstance hasItem(MinMaxBounds.Ints p_176521_, ItemLike p_176522_) {
        return paucalInventoryTrigger(ItemPredicate.Builder.item().of(p_176522_).withCount(p_176521_).build());
    }

    protected static InventoryChangeTrigger.TriggerInstance hasItem(ItemLike p_125978_) {
        return paucalInventoryTrigger(ItemPredicate.Builder.item().of(p_125978_).build());
    }

    protected static InventoryChangeTrigger.TriggerInstance hasItem(TagKey<Item> p_206407_) {
        return paucalInventoryTrigger(ItemPredicate.Builder.item().of(p_206407_).build());
    }

    /**
     * Prefixed with {@code paucal} to avoid collisions when Forge ATs {@link RecipeProvider#inventoryTrigger}.
     */
    protected static InventoryChangeTrigger.TriggerInstance paucalInventoryTrigger(ItemPredicate... $$0) {
        return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.ANY,
                MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, $$0);
    }
}
