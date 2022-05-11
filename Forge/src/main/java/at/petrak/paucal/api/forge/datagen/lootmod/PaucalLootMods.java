package at.petrak.paucal.api.forge.datagen.lootmod;

import at.petrak.paucal.api.PaucalAPI;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PaucalLootMods {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODS = DeferredRegister.create(
        ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, PaucalAPI.MOD_ID);

    public static final RegistryObject<PaucalAddItemModifier.Serializer> ADD_ITEM = LOOT_MODS.register(
        "add_item", PaucalAddItemModifier.Serializer::new);
}
