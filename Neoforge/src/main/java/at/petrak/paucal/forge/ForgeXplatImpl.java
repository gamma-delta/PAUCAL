package at.petrak.paucal.forge;

import at.petrak.paucal.xplat.IXplatAbstractions;
import at.petrak.paucal.xplat.Platform;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public class ForgeXplatImpl implements IXplatAbstractions {
  @Override
  public Platform platform() {
    return Platform.FORGE;
  }

  @Override
  public void sendPacketNearS2C(Vec3 pos, double radius, ServerLevel dimension, CustomPacketPayload packet) {
    PacketDistributor.sendToPlayersNear(dimension, null, pos.x, pos.y, pos.z, radius, packet);
  }

  @Override
  public void sendPacketToPlayerS2C(ServerPlayer target, CustomPacketPayload packet) {
    PacketDistributor.sendToPlayer(target, packet);
  }
}
