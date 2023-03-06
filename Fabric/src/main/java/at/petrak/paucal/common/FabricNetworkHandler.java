package at.petrak.paucal.common;

import at.petrak.paucal.common.msg.MsgHeadpatSoundS2C;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.function.Consumer;
import java.util.function.Function;

public class FabricNetworkHandler {
    public static void init() {
    }

    private static <T> ServerPlayNetworking.PlayChannelHandler makeServerBoundHandler(
        Function<FriendlyByteBuf, T> decoder, TriConsumer<T, MinecraftServer, ServerPlayer> handle) {
        return (server, player, _handler, buf, _responseSender) -> handle.accept(decoder.apply(buf), server, player);
    }

    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(MsgHeadpatSoundS2C.ID,
            makeClientBoundHandler(MsgHeadpatSoundS2C::deserialize, MsgHeadpatSoundS2C::handle));
    }

    private static <T> ClientPlayNetworking.PlayChannelHandler makeClientBoundHandler(
        Function<FriendlyByteBuf, T> decoder, Consumer<T> handler) {
        return (_client, _handler, buf, _responseSender) -> handler.accept(decoder.apply(buf));
    }
}
