package at.petrak.paucal.common;

import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.common.sounds.HeadpatSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class ModSounds {
    private static final List<SoundEvent> SOUNDS = new ArrayList<>();

    // This sound is replaced with the cool custom one
    public static SoundEvent DUMMY = sound(HeadpatSoundInstance.DUMMY_LOCATION);

    private static SoundEvent sound(String name) {
        var sound = SoundEvent.createVariableRangeEvent(new ResourceLocation(PaucalAPI.MOD_ID, name));
        SOUNDS.add(sound);
        return sound;
    }

    public static void init(BiConsumer<SoundEvent, ResourceLocation> r) {
        for (SoundEvent event : SOUNDS) {
            r.accept(event, event.getLocation());
        }
    }
}
