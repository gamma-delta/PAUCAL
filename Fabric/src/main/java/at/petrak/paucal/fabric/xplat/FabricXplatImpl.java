package at.petrak.paucal.fabric.xplat;

import at.petrak.paucal.api.msg.PaucalMessage;
import at.petrak.paucal.xplat.IXplatAbstractions;
import at.petrak.paucal.xplat.Platform;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public class FabricXplatImpl implements IXplatAbstractions {
    @Override
    public Platform platform() {
        return Platform.FABRIC;
    }

    @Override
    public @Nullable SoundEvent getSoundByID(ResourceLocation id) {
        return Registry.SOUND_EVENT.get(id);
    }

    @Override
    public void saveRecipeAdvancement(DataGenerator generator, CachedOutput cache, JsonObject json, Path path) {
        RecipeProvider.saveAdvancement(cache, json, path);
    }

    @Override
    public void sendPacketToPlayerS2C(ServerPlayer target, PaucalMessage packet) {
        ServerPlayNetworking.send(target, packet.getFabricId(), packet.toBuf());
    }

    @Override
    public void sendPacketNearS2C(Vec3 pos, double radius, ServerLevel dimension, PaucalMessage packet) {
        var pkt = ServerPlayNetworking.createS2CPacket(packet.getFabricId(), packet.toBuf());
        var nears = PlayerLookup.around(dimension, pos, radius);
        for (var p : nears) {
            p.connection.send(pkt);
        }
    }
}
