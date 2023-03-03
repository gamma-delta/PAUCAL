package at.petrak.paucal.common.impl;

import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.api.msg.PaucalMessage;
import at.petrak.paucal.xplat.IXplatAbstractions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class PaucalAPIImpl implements PaucalAPI {
    @Override
    public void sendPacketToPlayerC2S(ServerPlayer target, PaucalMessage packet) {
        IXplatAbstractions.INSTANCE.sendPacketToPlayerS2C(target, packet);
    }

    @Override
    public void sendPacketNearC2S(Vec3 pos, double radius, ServerLevel dimension, PaucalMessage packet) {
        IXplatAbstractions.INSTANCE.sendPacketNearS2C(pos, radius, dimension, packet);
    }
}
