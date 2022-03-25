package at.petrak.paucal.api.lootmod;

import at.petrak.paucal.PaucalMod;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PaucalLootMods {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODS = DeferredRegister.create(
        ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, PaucalMod.MOD_ID);

    public static final RegistryObject<PaucalAddItemModifier.Serializer> ADD_ITEM = LOOT_MODS.register(
        "add_item", PaucalAddItemModifier.Serializer::new);
}
