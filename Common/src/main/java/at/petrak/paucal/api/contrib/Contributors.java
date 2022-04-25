package at.petrak.paucal.api.contrib;

import at.petrak.paucal.PaucalConfig;
import at.petrak.paucal.PaucalMod;
import at.petrak.paucal.api.PaucalAPI;
import com.electronwill.nightconfig.core.AbstractConfig;
import com.electronwill.nightconfig.core.UnmodifiableCommentedConfig;
import com.electronwill.nightconfig.toml.TomlParser;
import net.minecraft.DefaultUncaughtExceptionHandler;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Contributors {
    private static final ConcurrentHashMap<UUID, Contributor> CONTRIBUTORS = new ConcurrentHashMap<>();
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

            var thread = new Thread(Contributors::fetch);
            thread.setName("PAUCAL Contributors Loading Thread");
            thread.setDaemon(true);
            thread.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(PaucalAPI.LOGGER));
            thread.start();
        }
    }

    private static void fetch() {
        UnmodifiableCommentedConfig config;
        try {
            var url = new URL(PaucalAPI.CONTRIBUTOR_URL);
            config = new TomlParser().parse(url).unmodifiable();
        } catch (IOException exn) {
            PaucalMod.LOGGER.warn("Couldn't load contributors from Github: {}", exn.getMessage());
            PaucalMod.LOGGER.warn("Oh well :(");
            return;
        }

        var keys = config.valueMap().keySet();
        for (var key : keys) {
            try {
                AbstractConfig rawEntry = config.get(key);
                UUID uuid = UUID.fromString(key);
                var contributor = new Contributor(uuid, rawEntry);
                CONTRIBUTORS.put(uuid, contributor);
            } catch (Exception exn) {
                PaucalMod.LOGGER.warn("Exception when loading contributor '{}': {}", key, exn.getMessage());
            }
        }
    }
}
