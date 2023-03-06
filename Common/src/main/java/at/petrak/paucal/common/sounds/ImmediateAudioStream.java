package at.petrak.paucal.common.sounds;

import com.mojang.blaze3d.audio.OggAudioStream;
import net.minecraft.client.sounds.AudioStream;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ImmediateAudioStream implements AudioStream {
    protected final OggAudioStream backing;
    protected ByteBuffer lazyRead;

    public ImmediateAudioStream(OggAudioStream backing) {
        this.backing = backing;
        this.lazyRead = null;
    }

    @Override
    public AudioFormat getFormat() {
        return this.backing.getFormat();
    }

    @Override
    public ByteBuffer read(int size) throws IOException {
        if (this.lazyRead == null) {
            this.lazyRead = this.backing.readAll();
            this.backing.close();
        }

        var maxSize = Math.min(size, this.lazyRead.remaining());
        var out = this.lazyRead.slice(0, maxSize);
        this.lazyRead.position(this.lazyRead.position() + maxSize);
        return out;
    }

    @Override
    public void close() throws IOException {
        this.lazyRead.clear();
    }
}
