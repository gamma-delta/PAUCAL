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
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static at.petrak.paucal.api.PaucalAPI.modLoc;

public class HeadpatSoundInstance implements SoundInstance {

    // We need a dummy sound instance to get Minecraft with the program; duck-impling the CompletableFuture
    // below should prevent MC from playing the sound but just in case the supplied ogg is silent
    public static final String DUMMY_LOCATION = "dummy_headpat";

    protected final boolean isGithub;
    protected final String soundName;
    @Nullable
    protected OggAudioStream stream;

    protected final double x, y, z;
    protected final float pitch;

    protected Sound dummySound;

    protected final RandomSource random;

    public HeadpatSoundInstance(String name, boolean isGithub, double x, double y, double z, float pitch,
        RandomSource random) {
        this.soundName = name;
        this.isGithub = isGithub;
        if (this.isGithub) {
            this.stream = GithubSoundsManifest.getSound(this.soundName);
        } else {
            this.stream = null;
        }

        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.random = random;
    }

    @Override
    public ResourceLocation getLocation() {
        return modLoc(DUMMY_LOCATION);
    }

    @Nullable
    @Override
    public WeighedSoundEvents resolve(SoundManager manager) {
        var weightedSounds = manager.getSoundEvent(modLoc(DUMMY_LOCATION));
        if (weightedSounds == null) {
            this.dummySound = SoundManager.EMPTY_SOUND;
        } else {
            this.dummySound = weightedSounds.getSound(this.random);
        }

        return weightedSounds;
    }

    @Override
    public Sound getSound() {
        return this.dummySound;
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
        return this.getXplatAudioStreamCommon(soundBuffers, looping);
    }

    // and fabric
    public CompletableFuture<AudioStream> getAudioStream(SoundBufferLibrary soundBuffers, ResourceLocation id,
        boolean looping) {
        return this.getXplatAudioStreamCommon(soundBuffers, looping);
    }

    protected CompletableFuture<AudioStream> getXplatAudioStreamCommon(SoundBufferLibrary soundBuffers,
        boolean looping) {
        if (this.isGithub) {
            if (this.stream == null) {
                return soundBuffers.getStream(modLoc(DUMMY_LOCATION), looping);
            }

            var loadTheWholeStreamNow = new ImmediateAudioStream(this.stream);
            return CompletableFuture.completedFuture(loadTheWholeStreamNow);
        } else {
            var decompose = new ResourceLocation(this.soundName);
            var actualSoundPath = new ResourceLocation(decompose.getNamespace(),
                "sounds/" + decompose.getPath() + ".ogg");
            return soundBuffers.getStream(actualSoundPath, looping);
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
