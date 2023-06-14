package at.petrak.paucal.xplat;

import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.api.msg.PaucalMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ServiceLoader;
import java.util.stream.Collectors;

public interface IXplatAbstractions {
    Platform platform();

    @Nullable SoundEvent getSoundByID(ResourceLocation id);

    void sendPacketToPlayerS2C(ServerPlayer target, PaucalMessage packet);

    void sendPacketNearS2C(Vec3 pos, double radius, ServerLevel dimension, PaucalMessage packet);

    default void init() {
        PaucalAPI.LOGGER.info("Hello PAUCAL! This is {}!", this.platform());
    }

    IXplatAbstractions INSTANCE = find();

    private static IXplatAbstractions find() {
        var providers = ServiceLoader.load(IXplatAbstractions.class).stream().toList();
        if (providers.size() != 1) {
            var names = providers.stream().map(p -> p.type().getName()).collect(Collectors.joining(",", "[", "]"));
            throw new IllegalStateException(
                "There should be exactly one IXplatAbstractions implementation on the classpath. Found: " + names);
        } else {
            var provider = providers.get(0);
            PaucalAPI.LOGGER.debug("Instantiating xplat impl: " + provider.type().getName());
            return provider.get();
        }
    }
}
