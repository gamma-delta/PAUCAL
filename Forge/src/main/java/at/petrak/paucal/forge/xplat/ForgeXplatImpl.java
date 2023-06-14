package at.petrak.paucal.forge.xplat;

import at.petrak.paucal.api.msg.PaucalMessage;
import at.petrak.paucal.common.msg.ForgePacketHandler;
import at.petrak.paucal.xplat.IXplatAbstractions;
import at.petrak.paucal.xplat.Platform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
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

    @Override
    public void sendPacketNearS2C(Vec3 pos, double radius, ServerLevel dimension, PaucalMessage packet) {
        ForgePacketHandler.getNetwork().send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(
            pos.x, pos.y, pos.z, radius * radius, dimension.dimension()
        )), packet);
    }

    @Override
    public void sendPacketToPlayerS2C(ServerPlayer target, PaucalMessage packet) {
        ForgePacketHandler.getNetwork().send(PacketDistributor.PLAYER.with(() -> target), packet);
    }
}
