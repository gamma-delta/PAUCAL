package at.petrak.paucal.fabric.xplat;

import at.petrak.paucal.fabric.FabricPaucalConfig;
import at.petrak.paucal.xplat.IXplatAbstractions;
import at.petrak.paucal.xplat.Platform;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;

public class FabricXplatImpl implements IXplatAbstractions {
    @Override
    public Platform platform() {
        return Platform.FABRIC;
    }

    @Override
    public @Nullable SoundEvent getSoundByID(ResourceLocation id) {
        return Registry.SOUND_EVENT.get(id);
    }
}
