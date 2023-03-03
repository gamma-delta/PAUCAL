package at.petrak.paucal.api;

import at.petrak.paucal.api.contrib.Contributor;
import at.petrak.paucal.api.msg.PaucalMessage;
import at.petrak.paucal.common.ContributorsManifest;
import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.function.Supplier;

public interface PaucalAPI {
    String MOD_ID = "paucal";
    String CONTRIBUTOR_URL =
        "https://raw.githubusercontent.com/gamma-delta/contributors/main/paucal/contributors-v1.toml";
    String HEADPAT_AUDIO_URL_STUB =
        "https://raw.githubusercontent.com/gamma-delta/contributors/main/paucal/headpat-sounds/";

    Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Nullable
    default Contributor getContributor(UUID uuid) {
        return ContributorsManifest.getContributor(uuid);
    }

    default void sendPacketToPlayerC2S(ServerPlayer target, PaucalMessage packet) {
    }

    default void sendPacketNearC2S(Vec3 pos, double radius, ServerLevel dimension, PaucalMessage packet) {
    }


    Supplier<PaucalAPI> INSTANCE = Suppliers.memoize(() -> {
        try {
            return (PaucalAPI) Class.forName("at.petrak.paucal.common.impl.PaucalAPIImpl")
                .getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            LOGGER.warn("Unable to find a PaucalAPIImpl, using a dummy");
            return new PaucalAPI() {
            };
        }
    });

    static PaucalAPI instance() {
        return INSTANCE.get();
    }

    static ResourceLocation modLoc(String s) {
        return new ResourceLocation(MOD_ID, s);
    }
}
