package at.petrak.paucal.api.msg;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

// https://github.com/gamma-delta/HexMod/blob/main/Common/src/main/java/at/petrak/hexcasting/common/network/IMessage.java
public interface PaucalMessage {
    default FriendlyByteBuf toBuf() {
        var ret = new FriendlyByteBuf(Unpooled.buffer());
        serialize(ret);
        return ret;
    }

    void serialize(FriendlyByteBuf buf);

    /**
     * Forge auto-assigns incrementing integers, Fabric requires us to declare an ID
     * These are sent using vanilla's custom plugin channel system and thus are written to every single packet.
     * So this ID tends to be more terse.
     */
    ResourceLocation getFabricId();
}
