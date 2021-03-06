package at.petrak.paucal.common.misc;

import at.petrak.paucal.PaucalConfig;
import at.petrak.paucal.common.Contributors;
import at.petrak.paucal.common.ModStats;
import at.petrak.paucal.xplat.IXplatAbstractions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class PatPat {
    public static InteractionResult onPat(Player player, Level world, InteractionHand hand, Entity entity,
        @Nullable EntityHitResult hitResult) {
        if (!PaucalConfig.common().allowPats()) {
            // you philistine
            return InteractionResult.PASS;
        }

        if (player.getItemInHand(hand).isEmpty()
            && player.isDiscrete() && hand == InteractionHand.MAIN_HAND
            && entity instanceof Player target) {
            if (player.getLevel() instanceof ServerLevel sworld) {
                var pos = target.getEyePosition();
                sworld.sendParticles(ParticleTypes.HEART, pos.x, pos.y + 0.5, pos.z, 1, 0, 0, 0, 0.1);
            } else {
                player.swing(hand);
            }

            tryPlayPatSound(target.getUUID(), target.getEyePosition(), player, world);

            player.awardStat(ModStats.PLAYERS_PATTED);
            target.awardStat(ModStats.HEADPATS_GOTTEN);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    /**
     * @return True if the pat happened successfully, false otherwise
     */
    public static boolean tryPlayPatSound(UUID pattee, Vec3 patteePos, @Nullable Player patter, Level world) {
        var contributor = Contributors.getContributor(pattee);
        if (contributor != null) {
            var soundKeyStr = contributor.getString("paucal:patSound");
            if (soundKeyStr != null) {
                var soundKey = new ResourceLocation(soundKeyStr);
                var sound = IXplatAbstractions.INSTANCE.getSoundByID(soundKey);
                if (sound != null) {
                    var pitchCenter = Objects.requireNonNullElse(contributor.getFloat("paucal:patPitchCenter"), 1f);
                    var pitchVariance = Objects.requireNonNullElse(contributor.getFloat("paucal:patPitchVariance"),
                        0.5f);
                    world.playSound(patter,
                        patteePos.x, patteePos.y, patteePos.z,
                        sound, SoundSource.PLAYERS,
                        1f, pitchCenter + (float) (Math.random() - 0.5) * pitchVariance);
                    return true;
                }
            }
        }
        return false;
    }
}
