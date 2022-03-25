package at.petrak.paucal.api.lootmod;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.Deserializers;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// https://github.com/vectorwing/FarmersDelight/blob/1.18.1/src/main/java/vectorwing/farmersdelight/common/loot/modifier/AddItemModifier.java
// but a little dazzled up
public class PaucalAddItemModifier extends LootModifier {
    private static final Gson GSON = Deserializers.createFunctionSerializer().create();

    private final Item addedItem;
    private final LootItemFunction[] funcs;

    public PaucalAddItemModifier(Item addedItem, LootItemFunction[] funcs, LootItemCondition[] conditions) {
        super(conditions);
        this.addedItem = addedItem;
        this.funcs = funcs;
    }

    public PaucalAddItemModifier(Item addedItem, int count, LootItemCondition[] conditions) {
        this(addedItem, new LootItemFunction[]{
            SetItemCountFunction.setCount(ConstantValue.exactly(count)).build()
        }, conditions);
    }

    public PaucalAddItemModifier(Item addedItem, NumberProvider count, LootItemCondition[] conditions) {
        this(addedItem, new LootItemFunction[]{
            SetItemCountFunction.setCount(count).build()
        }, conditions);
    }

    public PaucalAddItemModifier(Item addedItem, LootItemCondition[] conditions) {
        this(addedItem, 1, conditions);
    }

    public PaucalAddItemModifier(Item addedItem, ResourceLocation target) {
        this(addedItem, 1, target);
    }

    public PaucalAddItemModifier(Item addedItem, int count, ResourceLocation target) {
        this(addedItem, ConstantValue.exactly(count), new LootItemCondition[]{
            LootTableIdCondition.builder(target).build()
        });
    }

    public PaucalAddItemModifier(Item addedItem, NumberProvider count, ResourceLocation target) {
        this(addedItem, new LootItemFunction[]{
            SetItemCountFunction.setCount(count).build()
        }, new LootItemCondition[]{
            LootTableIdCondition.builder(target).build()
        });
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        var out = new ItemStack(this.addedItem);
        for (var func : this.funcs) {
            out = func.apply(out, context);
        }
        generatedLoot.add(out);
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<PaucalAddItemModifier> {
        @Override
        public PaucalAddItemModifier read(ResourceLocation location, JsonObject json, LootItemCondition[] conditions) {
            var addedItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(json, "item")));

            var functionElements = json.get("functions");
            var functions = GSON.fromJson(functionElements, LootItemFunction[].class);

            return new PaucalAddItemModifier(addedItem, functions, conditions);
        }

        @Override
        public JsonObject write(PaucalAddItemModifier instance) {
            var json = makeConditions(instance.conditions);
            json.addProperty("item", instance.addedItem.getRegistryName().toString());
            var functions = GSON.toJsonTree(instance.funcs);
            json.add("functions", functions);
            return json;
        }
    }
}
