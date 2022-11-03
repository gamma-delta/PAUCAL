package at.petrak.paucal;

import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.common.Contributors;
import at.petrak.paucal.common.ModSounds;
import at.petrak.paucal.common.ModStats;
import at.petrak.paucal.common.advancement.ModAdvancementTriggers;
import at.petrak.paucal.common.command.ModCommands;
import at.petrak.paucal.common.misc.NewWorldMessage;
import at.petrak.paucal.common.misc.PatPat;
import at.petrak.paucal.forge.ForgePaucalConfig;
import at.petrak.paucal.xplat.IXplatAbstractions;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mod(PaucalAPI.MOD_ID)
public class ForgePaucalInit {
    public ForgePaucalInit() {
        IXplatAbstractions.INSTANCE.init();

        var specPair = new ForgeConfigSpec.Builder().configure(ForgePaucalConfig::new);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, specPair.getRight());
        PaucalConfig.setCommon(specPair.getLeft());

        ModAdvancementTriggers.registerTriggers();

        bind(Registry.SOUND_EVENT_REGISTRY, ModSounds::init);

        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        var evBus = MinecraftForge.EVENT_BUS;

        // We have to do these at some point when the registries are still open
        modBus.addListener((RegisterEvent event) -> {
            if (event.getRegistryKey().equals(Registry.ITEM_REGISTRY)) {
                ModStats.register();
            }
        });
        modBus.addListener((FMLLoadCompleteEvent evt) -> {
            // Config is loaded later in 1.19 forg, hopefully the loadcomplete is late enough
            Contributors.loadContributors();
        });

        evBus.addListener((PlayerInteractEvent.EntityInteract evt) -> {
            var result = PatPat.onPat(evt.getEntity(), evt.getLevel(), evt.getHand(), evt.getTarget(), null);
            if (result == InteractionResult.SUCCESS) {
                evt.setCanceled(true);
                evt.setCancellationResult(InteractionResult.SUCCESS);
            }
        });
        evBus.addListener((RegisterCommandsEvent evt) -> {
            ModCommands.register(evt.getDispatcher());
        });
        evBus.addListener((PlayerEvent.PlayerLoggedInEvent evt) -> {
            NewWorldMessage.onLogin(evt.getEntity());
        });
    }

    private static <T> void bind(ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        // No more generics; this now fires for EVERY registry event.
        FMLJavaModLoadingContext.get().getModEventBus().addListener((RegisterEvent event) -> {
            // if it's the right registry,
            if (registry.equals(event.getRegistryKey())) {
                // then give it to our abstracted mechanisms
                source.accept((t, rl) -> event.register(registry, rl, () -> t));
            }
        });
    }
}
