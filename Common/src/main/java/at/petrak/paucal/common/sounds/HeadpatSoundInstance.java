package at.petrak.paucal.common.sounds;

import com.mojang.blaze3d.audio.OggAudioStream;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.AudioStream;
import net.minecraft.client.sounds.SoundBufferLibrary;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static at.petrak.paucal.api.PaucalAPI.modLoc;

public class HeadpatSoundInstance implements SoundInstance {

    public static final ResourceLocation DUMMY_LOCATION = modLoc("dummy_headpat");

    protected final boolean isNetwork;
    protected final String soundName;
    @Nullable
    protected final OggAudioStream stream;

    protected final double x, y, z;
    protected float pitch;

    public HeadpatSoundInstance(String name, boolean isNetwork, double x, double y, double z, float pitch) {
        this.soundName = name;
        this.stream = NetworkSoundsManifest.getSound(this.soundName);
        this.isNetwork = isNetwork;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
    }

    @Override
    public ResourceLocation getLocation() {
        return DUMMY_LOCATION;
    }

    @Nullable
    @Override
    public WeighedSoundEvents resolve(SoundManager manager) {
        return manager.getSoundEvent(DUMMY_LOCATION);
    }

    @Override
    public Sound getSound() {
        return SoundManager.EMPTY_SOUND;
    }

    @Override
    public float getPitch() {
        return this.pitch;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }

    // Duck-implement Forge ...
    public CompletableFuture<AudioStream> getStream(SoundBufferLibrary soundBuffers, Sound sound, boolean looping) {
        if (this.isNetwork) {
            return this.stream == null
                ? soundBuffers.getStream(DUMMY_LOCATION, looping)
                : CompletableFuture.completedFuture(this.stream);
        } else {
            var loc = new ResourceLocation(this.soundName);
            return soundBuffers.getStream(loc, looping);
        }
    }

    // and fabric
    public CompletableFuture<AudioStream> getAudioStream(SoundBufferLibrary soundBuffers, ResourceLocation id,
        boolean looping) {
        if (this.isNetwork) {
            return this.stream == null
                ? soundBuffers.getStream(DUMMY_LOCATION, looping)
                : CompletableFuture.completedFuture(this.stream);
        } else {
            var loc = new ResourceLocation(this.soundName);
            return soundBuffers.getStream(loc, looping);
        }
    }

    //

    @Override
    public float getVolume() {
        return 1f;
    }

    @Override
    public Attenuation getAttenuation() {
        return Attenuation.LINEAR;
    }

    @Override
    public SoundSource getSource() {
        return SoundSource.PLAYERS;
    }

    @Override
    public boolean isLooping() {
        return false;
    }

    @Override
    public boolean isRelative() {
        return false;
    }

    @Override
    public int getDelay() {
        return 0;
    }
}
