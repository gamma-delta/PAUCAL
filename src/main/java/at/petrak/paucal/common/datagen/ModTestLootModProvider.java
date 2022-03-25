package at.petrak.paucal.common.datagen;

import at.petrak.paucal.PaucalMod;
import at.petrak.paucal.api.lootmod.PaucalAddItemModifier;
import at.petrak.paucal.api.lootmod.PaucalLootMods;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

/**
 * Test of add_item
 */
public class ModTestLootModProvider extends GlobalLootModifierProvider {
    public ModTestLootModProvider(DataGenerator gen) {
        super(gen, PaucalMod.MOD_ID);
    }

    @Override
    protected void start() {
        add("test1", PaucalLootMods.ADD_ITEM.get(),
            new PaucalAddItemModifier(Items.DIAMOND, new ResourceLocation("minecraft:blocks/dirt")));
        add("test2", PaucalLootMods.ADD_ITEM.get(),
            new PaucalAddItemModifier(Items.DIAMOND, 4, new ResourceLocation("minecraft:blocks/stone")));
        add("test3", PaucalLootMods.ADD_ITEM.get(), new PaucalAddItemModifier(Items.DIAMOND,
            UniformGenerator.between(0, 4), new ResourceLocation("minecraft:blocks/cobblestone")));
        add("test4", PaucalLootMods.ADD_ITEM.get(), new PaucalAddItemModifier(Items.DIAMOND,
            new LootItemFunction[]{
                ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE).build()
            },
            new LootItemCondition[]{
                LootTableIdCondition.builder(new ResourceLocation("minecraft:blocks/grass_block")).build()
            }
        ));
    }
}
