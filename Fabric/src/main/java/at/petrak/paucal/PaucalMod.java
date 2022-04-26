package at.petrak.paucal;

import at.petrak.paucal.common.ModSounds;
import at.petrak.paucal.common.advancement.ModAdvancementTriggers;
import at.petrak.paucal.common.misc.PatPat;
import at.petrak.paucal.fabric.FabricPaucalConfig;
import at.petrak.paucal.xplat.IXplatAbstractions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;

public class PaucalMod implements ModInitializer {
    @Override
    public void onInitialize() {
        IXplatAbstractions.INSTANCE.init();

        FabricPaucalConfig.setup();
        
        ModAdvancementTriggers.registerTriggers();

        ModSounds.init(bind(Registry.SOUND_EVENT));

        UseEntityCallback.EVENT.register(PatPat::onPat);
    }

    private static <T> BiConsumer<T, ResourceLocation> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }
}
