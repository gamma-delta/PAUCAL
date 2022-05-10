package at.petrak.paucal.forge.xplat;

import at.petrak.paucal.xplat.IXplatAbstractions;
import at.petrak.paucal.xplat.Platform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class ForgeXplatImpl implements IXplatAbstractions {
    @Override
    public Platform platform() {
        return Platform.FORGE;
    }

    @Override
    public @Nullable SoundEvent getSoundByID(ResourceLocation id) {
        return ForgeRegistries.SOUND_EVENTS.getValue(id);
    }
}
