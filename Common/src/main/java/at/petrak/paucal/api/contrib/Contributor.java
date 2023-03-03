package at.petrak.paucal.api.contrib;

import at.petrak.paucal.common.network.MsgHeadpatSoundS2C;
import at.petrak.paucal.xplat.IXplatAbstractions;
import com.electronwill.nightconfig.core.AbstractConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Contributor {
    private final UUID uuid;
    private final int level;
    private final boolean isDev;

    private final float pitchCenter, pitchVariance;
    private final HeadpatType headpatType;
    @Nullable
    private final String headpatLoc;

    private final AbstractConfig otherVals;

    @ApiStatus.Internal
    public Contributor(UUID uuid, AbstractConfig cfg) {
        this.uuid = uuid;
        this.otherVals = cfg;

        this.level = (int) this.otherVals.get("paucal:contributor_level");
        this.isDev = (boolean) this.otherVals.get("paucal:is_dev");
        this.pitchCenter = this.getFloat("paucal:pat_pitch", 1f);
        this.pitchVariance = this.getFloat("paucal:pat_variance", 0.5f);

        var headpat = (String) this.otherVals.getOrElse("paucal:pat_sound", null);
        if (headpat == null) {
            this.headpatType = HeadpatType.NONE;
            this.headpatLoc = null;
        } else if (ResourceLocation.isValidResourceLocation(headpat)) {
            this.headpatType = HeadpatType.VANILLA;
            this.headpatLoc = headpat;
        } else {
            this.headpatType = HeadpatType.NETWORK;
            this.headpatLoc = headpat;
        }
    }

    public int getLevel() {
        return level;
    }

    public boolean isDev() {
        return isDev;
    }

    public UUID getUuid() {
        return uuid;
    }

    /**
     * Logic happens clientside to the <em>patter</em>, the pattee gets a packet like everyone else
     *
     * @param patter
     */
    public boolean doHeadpatSound(Vec3 patteePos, @Nullable Player patter, Level level) {
        if (this.headpatLoc == null) return false;

        var pitch = this.pitchCenter + (float) (Math.random() - 0.5) * this.pitchVariance;
        boolean networked = false;
        switch (this.headpatType) {
            case NONE -> {
                return false;
            }
            case VANILLA -> {
                networked = false;
            }
            case NETWORK -> {
                networked = true;
            }
        }
        if (level instanceof ServerLevel slevel) {
            IXplatAbstractions.INSTANCE.sendPacketNearS2C(patteePos, 64.0, slevel,
                new MsgHeadpatSoundS2C(this.headpatLoc, networked,
                    patteePos.x, patteePos.y, patteePos.z, pitch,
                    patter == null ? null : patter.getUUID()));
        } // Otherwise, they will play the sound once they get the packet

        return true;
    }

    @ApiStatus.Internal
    @Nullable
    public String getNetworkHeadpatLoc() {
        return this.headpatType == HeadpatType.NETWORK
            ? this.headpatLoc
            : null;
    }

    // =====

    @Nullable
    public String getString(String key) {
        return this.getString(key, null);
    }

    public String getString(String key, String fallback) {
        return Objects.requireNonNullElse(otherVals.get(key), fallback);
    }

    @Nullable
    public Integer getInt(String key) {
        Number n = otherVals.get(key);
        return n == null ? null : n.intValue();
    }

    public int getInt(String key, int fallback) {
        Number n = otherVals.get(key);
        return n == null ? fallback : n.intValue();
    }

    @Nullable
    public Float getFloat(String key) {
        Number n = otherVals.get(key);
        return n == null ? null : n.floatValue();
    }

    public float getFloat(String key, float fallback) {
        Number n = otherVals.get(key);
        return n == null ? fallback : n.floatValue();
    }

    @Nullable
    public <T> T get(String key) {
        return this.otherVals.get(key);
    }


    public Set<String> allKeys() {
        return this.otherVals.valueMap().keySet();
    }

    public AbstractConfig otherVals() {
        return this.otherVals;
    }

    private enum HeadpatType {
        NONE,
        VANILLA,
        NETWORK,
    }
}
