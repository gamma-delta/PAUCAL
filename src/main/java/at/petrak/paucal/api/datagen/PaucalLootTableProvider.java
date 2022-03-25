package at.petrak.paucal.api.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

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

    protected abstract void makeLootTables(Map<Block, LootTable.Builder> lootTables);

    protected void dropSelfTable(Map<Block, LootTable.Builder> lootTables, Supplier<Block>... blocks) {
        for (var blockSupp : blocks) {
            var block = blockSupp.get();
            dropSelfTable(block.getRegistryName().getPath(), block, lootTables);
        }
    }

    protected void dropSelfTable(Map<Block, LootTable.Builder> lootTables, Block... blocks) {
        for (var block : blocks) {
            dropSelfTable(block.getRegistryName().getPath(), block, lootTables);
        }
    }

    protected void dropSelfTable(String name, Block block, Map<Block, LootTable.Builder> lootTables) {
        var pool = LootPool.lootPool()
            .name(name)
            .setRolls(ConstantValue.exactly(1))
            .add(LootItem.lootTableItem(block));
        var loot = LootTable.lootTable().withPool(pool);

        lootTables.put(block, loot);
    }

    @Override
    public void run(HashCache cache) {
        var lootTables = new HashMap<Block, LootTable.Builder>();
        this.makeLootTables(lootTables);

        var tables = new HashMap<ResourceLocation, LootTable>();
        for (var entry : lootTables.entrySet()) {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParamSet(LootContextParamSets.BLOCK).build());
        }

        var outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                DataProvider.save(GSON, cache, LootTables.serialize(lootTable), path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
