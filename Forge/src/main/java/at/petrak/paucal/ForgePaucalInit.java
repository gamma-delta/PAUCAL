package at.petrak.paucal;

import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.common.Contributors;
import at.petrak.paucal.common.ModSounds;
import at.petrak.paucal.common.advancement.ModAdvancementTriggers;
import at.petrak.paucal.common.command.ModCommands;
import at.petrak.paucal.common.misc.NewWorldMessage;
import at.petrak.paucal.common.misc.PatPat;
import at.petrak.paucal.forge.ForgePaucalConfig;
import at.petrak.paucal.xplat.IXplatAbstractions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

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

        bind(ForgeRegistries.SOUND_EVENTS, ModSounds::init);

        var evBus = MinecraftForge.EVENT_BUS;
        evBus.addListener((PlayerInteractEvent.EntityInteract evt) -> {
            var result = PatPat.onPat(evt.getPlayer(), evt.getWorld(), evt.getHand(), evt.getTarget(), null);
            if (result == InteractionResult.SUCCESS) {
                evt.setCanceled(true);
                evt.setCancellationResult(InteractionResult.SUCCESS);
            }
        });
        evBus.addListener((RegisterCommandsEvent evt) -> {
            ModCommands.register(evt.getDispatcher());
        });
        evBus.addListener((PlayerEvent.PlayerLoggedInEvent evt) -> {
            NewWorldMessage.onLogin(evt.getPlayer());
        });
        
        Contributors.loadContributors();
    }

    private static <T extends IForgeRegistryEntry<T>> void bind(IForgeRegistry<T> registry,
        Consumer<BiConsumer<T, ResourceLocation>> source) {
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(registry.getRegistrySuperType(),
            (RegistryEvent.Register<T> event) -> {
                IForgeRegistry<T> forgeRegistry = event.getRegistry();
                source.accept((t, rl) -> {
                    t.setRegistryName(rl);
                    forgeRegistry.register(t);
                });
            });
    }
}
