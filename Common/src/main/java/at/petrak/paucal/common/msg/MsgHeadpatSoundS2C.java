package at.petrak.paucal.common.msg;

import at.petrak.paucal.api.msg.PaucalMessage;
import at.petrak.paucal.common.sounds.HeadpatSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

import static at.petrak.paucal.api.PaucalAPI.modLoc;

public record MsgHeadpatSoundS2C(String soundName, boolean isNetwork, double x, double y, double z,
                                 float pitch, @Nullable UUID patter) implements PaucalMessage {
    public static final ResourceLocation ID = modLoc("pat");

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeUtf(this.soundName);
        buf.writeBoolean(this.isNetwork);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeFloat(this.pitch);
        buf.writeBoolean(this.patter != null);
        if (this.patter != null)
            buf.writeUUID(this.patter);
    }

    public static MsgHeadpatSoundS2C deserialize(FriendlyByteBuf buf) {
        var sound = buf.readUtf();
        var isNetwork = buf.readBoolean();
        var x = buf.readDouble();
        var y = buf.readDouble();
        var z = buf.readDouble();
        var pitch = buf.readFloat();
        var hasUUID = buf.readBoolean();
        var patter = hasUUID
            ? buf.readUUID()
            : null;
        return new MsgHeadpatSoundS2C(sound, isNetwork, x, y, z, pitch, patter);
    }

    public static void handle(MsgHeadpatSoundS2C self) {
        Minecraft.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                var sound = new HeadpatSoundInstance(self.soundName, self.isNetwork, self.x, self.y, self.z,
                    self.pitch, SoundInstance.createUnseededRandom());

                var minecraft = Minecraft.getInstance();
                var player = minecraft.player;
                if (player != null && !player.getUUID().equals(self.patter)) {
                    minecraft.getSoundManager().play(sound);
                }
            }
        });
    }
}
