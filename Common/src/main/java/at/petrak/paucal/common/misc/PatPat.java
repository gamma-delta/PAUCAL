package at.petrak.paucal.common.misc;

import at.petrak.paucal.PaucalConfig;
import at.petrak.paucal.common.ContributorsManifest;
import at.petrak.paucal.common.ModStats;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

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
            if (player.level() instanceof ServerLevel sworld) {
                var pos = target.getEyePosition();
                sworld.sendParticles(ParticleTypes.HEART, pos.x, pos.y + 0.5, pos.z, 1, 0, 0, 0, 0.1);
            } else {
                player.swing(hand);
            }

            tryPlayPatSound(target.getUUID(), target.getEyePosition(), player, world);

            player.awardStat(ModStats.PLAYERS_PATTED);
            target.awardStat(ModStats.HEADPATS_GOTTEN);

            if (target.isOnFire()) {
                target.clearFire();
                if (player.level() instanceof ServerLevel sworld) {
                    var pos = target.getEyePosition();
                    sworld.sendParticles(ParticleTypes.SMOKE, pos.x, pos.y + 0.5, pos.z, 10, 0, 0, 0, 0.1);
                }
                player.level().playSound(player, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1f, 1f);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    /**
     * @return True if the pat happened successfully, false otherwise
     */
    public static boolean tryPlayPatSound(UUID pattee, Vec3 patteePos, @Nullable Player patter, Level world) {
        var contributor = ContributorsManifest.getContributor(pattee);
        if (contributor != null) {
            return contributor.doHeadpatSound(patteePos, patter, world);
        }

        return false;
    }
}
