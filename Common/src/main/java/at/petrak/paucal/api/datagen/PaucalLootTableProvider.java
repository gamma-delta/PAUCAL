package at.petrak.paucal.api.datagen;

import at.petrak.paucal.api.PaucalAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

// https://github.com/XuulMedia/Flint-Age/blob/4638289130ef80dafe9b6a3fdcb461a72688100f/src/main/java/xuul/flint/datagen/BaseLootTableProvider.java
// yoink
public abstract class PaucalLootTableProvider extends LootTableProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    protected final DataGenerator generator;

    public PaucalLootTableProvider(DataGenerator pGenerator) {
        super(pGenerator);
        this.generator = pGenerator;
    }

    protected abstract void makeLootTables(Map<Block, LootTable.Builder> blockTables,
        Map<ResourceLocation, LootTable.Builder> lootTables);

    protected LootPool.Builder dropThisPool(ItemLike item, int count) {
        return dropThisPool(item, ConstantValue.exactly(count));
    }

    protected LootPool.Builder dropThisPool(ItemLike item, NumberProvider count) {
        return LootPool.lootPool()
            .setRolls(count)
            .add(LootItem.lootTableItem(item));
    }

    @SafeVarargs
    protected final void dropSelf(Map<Block, LootTable.Builder> lootTables, Supplier<? extends Block>... blocks) {
        for (var blockSupp : blocks) {
            var block = blockSupp.get();
            dropSelf(block, lootTables);
        }
    }

    protected void dropSelf(Map<Block, LootTable.Builder> lootTables, Block... blocks) {
        for (var block : blocks) {
            dropSelf(block, lootTables);
        }
    }

    protected void dropSelf(Block block, Map<Block, LootTable.Builder> lootTables) {
        var table = LootTable.lootTable().withPool(dropThisPool(block, 1));
        lootTables.put(block, table);
    }

    protected void dropThis(Block block, ItemLike drop, Map<Block, LootTable.Builder> lootTables) {
        var table = LootTable.lootTable().withPool(dropThisPool(drop, 1));
        lootTables.put(block, table);
    }

    protected void dropThis(Block block, ItemLike drop, NumberProvider count,
        Map<Block, LootTable.Builder> lootTables) {
        var table = LootTable.lootTable().withPool(dropThisPool(drop, count));
        lootTables.put(block, table);
    }

    @Override
    public void run(CachedOutput cache) {
        var blockTables = new HashMap<Block, LootTable.Builder>();
        var lootTables = new HashMap<ResourceLocation, LootTable.Builder>();
        this.makeLootTables(blockTables, lootTables);

        for (var entry : blockTables.entrySet()) {
            var old = lootTables.put(entry.getKey().getLootTable(),
                entry.getValue().setParamSet(LootContextParamSets.BLOCK));
            if (old != null) {
                PaucalAPI.LOGGER.warn("Whoopsy, {} clobbered a loot table '{}': {}",
                    this.getClass().getSimpleName(), entry.getKey(), old);
            }
        }

        var outputFolder = this.generator.getOutputFolder();
        lootTables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                DataProvider.saveStable(cache, LootTables.serialize(lootTable.build()), path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
