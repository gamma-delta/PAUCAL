package at.petrak.paucal;

import at.petrak.paucal.common.Contributors;
import at.petrak.paucal.common.ModSounds;
import at.petrak.paucal.common.ModStats;
import at.petrak.paucal.common.advancement.ModAdvancementTriggers;
import at.petrak.paucal.common.command.ModCommands;
import at.petrak.paucal.common.misc.NewWorldMessage;
import at.petrak.paucal.common.misc.PatPat;
import at.petrak.paucal.fabric.FabricPaucalConfig;
import at.petrak.paucal.xplat.IXplatAbstractions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.function.BiConsumer;

public class FabricPaucalInit implements ModInitializer {
    @Override
    public void onInitialize() {
        IXplatAbstractions.INSTANCE.init();

        FabricPaucalConfig.setup();

        ModAdvancementTriggers.registerTriggers();

        ModSounds.init(bind(Registry.SOUND_EVENT));

        UseEntityCallback.EVENT.register(PatPat::onPat);
        CommandRegistrationCallback.EVENT.register((dp, _dedicated) -> ModCommands.register(dp));
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof Player player) {
                NewWorldMessage.onLogin(player);
            }
        });

        Contributors.loadContributors();
        ModStats.register();
    }

    private static <T> BiConsumer<T, ResourceLocation> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }
}
