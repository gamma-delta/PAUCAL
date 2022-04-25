package at.petrak.paucal.fabric;

import at.petrak.paucal.PaucalConfig;
import at.petrak.paucal.api.PaucalAPI;
import io.github.fablabsmc.fablabs.api.fiber.v1.builder.ConfigTreeBuilder;
import io.github.fablabsmc.fablabs.api.fiber.v1.exception.ValueDeserializationException;
import io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.FiberSerialization;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonValueSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.PropertyMirror;

import java.io.*;
import java.nio.file.*;

public class FabricPaucalConfig {
    private static Common COMMON;

    private static void writeDefaultConfig(ConfigTree config, Path path, JanksonValueSerializer serializer) {
        try (OutputStream s = new BufferedOutputStream(
            Files.newOutputStream(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW))) {
            FiberSerialization.serialize(config, s, serializer);
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException e) {
            PaucalAPI.LOGGER.error("Error writing default config", e);
        }
    }

    private static void setupConfig(ConfigTree config, Path p, JanksonValueSerializer serializer) {
        writeDefaultConfig(config, p, serializer);

        try (InputStream s = new BufferedInputStream(
            Files.newInputStream(p, StandardOpenOption.READ, StandardOpenOption.CREATE))) {
            FiberSerialization.deserialize(config, s, serializer);
        } catch (IOException | ValueDeserializationException e) {
            PaucalAPI.LOGGER.error("Error loading config from {}", p, e);
        }
    }

    public static void setup() {
        try {
            Files.createDirectory(Paths.get("config"));
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException e) {
            PaucalAPI.LOGGER.warn("Failed to make config dir", e);
        }

        var serializer = new JanksonValueSerializer(false);
        var common = COMMON.configure(ConfigTree.builder());
        setupConfig(common, Paths.get("config", PaucalAPI.MOD_ID + "-common.json5"), serializer);
        PaucalConfig.setCommon(COMMON);
    }

    private static final class Common implements PaucalConfig.ConfigAccess {
        private final PropertyMirror<Boolean> allowPats = PropertyMirror.create(ConfigTypes.BOOLEAN);
        private final PropertyMirror<Boolean> loadContributors = PropertyMirror.create(ConfigTypes.BOOLEAN);

        public ConfigTree configure(ConfigTreeBuilder bob) {
            bob
                .beginValue("allowPats", ConfigTypes.BOOLEAN, true)
                .withComment("Whether to allow patting players with a shift-right-click.")
                .finishValue(allowPats::mirror)

                .beginValue("loadContributors", ConfigTypes.BOOLEAN, true)
                .withComment("Whether to load contributor info from the internet.\n" +
                    "If false, no one will appear as a contributor.")
                .finishValue(loadContributors::mirror);

            return bob.build();
        }

        @Override
        public boolean allowPats() {
            return allowPats.getValue();
        }

        @Override
        public boolean loadContributors() {
            return loadContributors.getValue();
        }
    }
}
