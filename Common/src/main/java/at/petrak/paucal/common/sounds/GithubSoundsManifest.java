package at.petrak.paucal.common.sounds;

import at.petrak.paucal.api.PaucalAPI;
import com.mojang.blaze3d.audio.OggAudioStream;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GithubSoundsManifest {
    // We store these as OGG-ENCODED to save.
    private static final ConcurrentMap<String, ByteBuffer> GITHUB_SOUNDS = new ConcurrentHashMap<>();

    public static void loadSounds(Iterable<String> names) {
        GITHUB_SOUNDS.clear();

        for (var s : names) {
            try {
                var unstub = PaucalAPI.HEADPAT_AUDIO_URL_STUB + s + ".ogg";
                var url = new URL(unstub);
                var connection = url.openConnection();
                var is = connection.getInputStream();
                var oggBytes = is.readAllBytes();
                GITHUB_SOUNDS.put(s, ByteBuffer.wrap(oggBytes));
            } catch (Exception exn) {
                PaucalAPI.LOGGER.warn("Error when loading github sound '{}'", s, exn);
            }
        }

        PaucalAPI.LOGGER.info("Loaded {} headpat sounds from Github", GITHUB_SOUNDS.size());
    }

    @Nullable
    public static OggAudioStream getSound(String name) {
        var oggBytes = GITHUB_SOUNDS.getOrDefault(name, null);
        if (oggBytes == null) {
            PaucalAPI.LOGGER.warn("Tried to load a github sound {} that wasn't found", name);
            return null;
        }

        // Make a new ogg stream that reads the bytes
        try {
            return new OggAudioStream(new ByteArrayInputStream(oggBytes.array()));
        } catch (IOException e) {
            PaucalAPI.LOGGER.error("The github sound {} is an INVALID OGG FILE. This is Really Bad, what are you " +
                "doing.", name, e);
            return null;
        }
    }
}
