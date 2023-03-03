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

    public static final ResourceLocation DUMMY_LOCATION = modLoc("headpat_dummy");
    public static final ResourceLocation SILENCE = new ResourceLocation("meta", "missing_sound");

    protected final String soundName;
    @Nullable
    protected final OggAudioStream stream;
    protected final double x, y, z;
    protected final RandomSource random;
    protected final float pitchCenter, pitchVariance;

    public HeadpatSoundInstance(String name, double x, double y, double z, RandomSource random, float pitchCenter,
        float pitchVariance) {
        this.soundName = name;
        this.stream = NetworkSoundsManifest.getSound(this.soundName);
        this.x = x;
        this.y = y;
        this.z = z;
        this.random = random;
        this.pitchCenter = pitchCenter;
        this.pitchVariance = pitchVariance;
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
        return this.pitchCenter + ((this.random.nextFloat() - 0.5f * this.pitchVariance));
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
        if (this.stream == null) {
            return soundBuffers.getStream(SILENCE, looping);
        } else {
            return CompletableFuture.completedFuture(this.stream);
        }
    }

    // and fabric
    public CompletableFuture<AudioStream> getAudioStream(SoundBufferLibrary loader, ResourceLocation id,
        boolean repeatInstantly) {
        if (this.stream == null) {
            return loader.getStream(SILENCE, repeatInstantly);
        } else {
            return CompletableFuture.completedFuture(this.stream);
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
