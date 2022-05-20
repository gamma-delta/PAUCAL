package at.petrak.paucal.common;

import at.petrak.paucal.api.PaucalAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class ModSounds {
    private static final List<SoundEvent> SOUNDS = new ArrayList<>();

    public static SoundEvent BEEP = sound("pet.device");
    public static SoundEvent SPLAT = sound("pet.splat");
    public static SoundEvent NEKO = sound("pet.neko");
    public static SoundEvent SLIME = sound("pet.slime");
    public static SoundEvent WIRE = sound("pet.wire");

    private static SoundEvent sound(String name) {
        var sound = new SoundEvent(new ResourceLocation(PaucalAPI.MOD_ID, name));
        SOUNDS.add(sound);
        return sound;
    }

    public static void init(BiConsumer<SoundEvent, ResourceLocation> r) {
        for (SoundEvent event : SOUNDS) {
            r.accept(event, event.getLocation());
        }
    }
}
