package at.petrak.paucal.api.contrib;

import at.petrak.paucal.PaucalConfig;
import at.petrak.paucal.api.PaucalAPI;
import com.moandjiezana.toml.Toml;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import net.minecraft.DefaultUncaughtExceptionHandler;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Contributors {
    private static Map<UUID, Contributor> CONTRIBUTORS = Object2ObjectMaps.emptyMap();
    private static boolean startedLoading = false;

    @Nullable
    public static Contributor getContributor(UUID uuid) {
        return CONTRIBUTORS.get(uuid);
    }

    public static void loadContributors() {
        if (!startedLoading) {
            startedLoading = true;

            if (!PaucalConfig.common().loadContributors()) {
                PaucalAPI.LOGGER.info("Contributors disabled in the config!");
                return;
            }

            var thread = new Thread(Contributors::fetchAndPopulate);
            thread.setName("PAUCAL Contributors Loading Thread");
            thread.setDaemon(true);
            thread.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(PaucalAPI.LOGGER));
            thread.start();
        }
    }

    private static void fetchAndPopulate() {
        CONTRIBUTORS = fetch();
    }

    public static Map<UUID, Contributor> fetch() {
        Toml config;
        try {
            var url = new URL(PaucalAPI.CONTRIBUTOR_URL);
            config = new Toml().read(url.openStream());
        } catch (IOException exn) {
            PaucalAPI.LOGGER.warn("Couldn't load contributors from Github: {}", exn.getMessage());
            PaucalAPI.LOGGER.warn("Oh well :(");
            return Object2ObjectMaps.emptyMap();
        }

        var out = new HashMap<UUID, Contributor>();

        for (var entry : config.entrySet()) {
            try {
                var subtable = (Toml) entry.getValue();
                UUID uuid = UUID.fromString(entry.getKey());
                var contributor = new Contributor(uuid, subtable.toMap());
                out.put(uuid, contributor);
            } catch (Exception exn) {
                PaucalAPI.LOGGER.warn("Exception when loading contributor '{}': {}", entry.getKey(), exn.getMessage());
                // adn try again with the next one
            }
        }

        return out;
    }
}
