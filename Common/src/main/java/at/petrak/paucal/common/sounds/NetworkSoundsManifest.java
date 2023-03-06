package at.petrak.paucal.common.sounds;

import at.petrak.paucal.api.PaucalAPI;
import com.mojang.blaze3d.audio.OggAudioStream;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class NetworkSoundsManifest {
    private static final ConcurrentMap<String, OggAudioStream> NETWORK_SOUNDS = new ConcurrentHashMap<>();

    public static void loadSounds(Iterable<String> names) {
        NETWORK_SOUNDS.clear();

        for (var s : names) {
            try {
                var unstub = PaucalAPI.HEADPAT_AUDIO_URL_STUB + s + ".ogg";
                var url = new URL(unstub);
                var connection = url.openConnection();
                var is = connection.getInputStream();

                var audio = new OggAudioStream(is);
                NETWORK_SOUNDS.put(s, audio);
            } catch (Exception exn) {
                PaucalAPI.LOGGER.warn("Error when loading sound '{}'", s, exn);
            }
        }

        PaucalAPI.LOGGER.info("Loaded {} sounds", NETWORK_SOUNDS.size());
    }

    @Nullable
    public static OggAudioStream getSound(String name) {
        var out = NETWORK_SOUNDS.getOrDefault(name, null);
        if (out == null) {
            PaucalAPI.LOGGER.warn("Tried to load a network sound {} that wasn't found", name);
        }
        return out;
    }
}
