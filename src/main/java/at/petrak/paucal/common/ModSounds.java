package at.petrak.paucal.common;

import at.petrak.paucal.PaucalMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
        DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, PaucalMod.MOD_ID);

    public static final RegistryObject<SoundEvent> SPLAT = sound("splat");

    private static RegistryObject<SoundEvent> sound(String name) {
        return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(PaucalMod.MOD_ID, name)));
    }
}
