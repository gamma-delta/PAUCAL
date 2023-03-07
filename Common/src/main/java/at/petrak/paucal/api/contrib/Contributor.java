package at.petrak.paucal.api.contrib;

import at.petrak.paucal.xplat.IXplatAbstractions;
import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Contributor {
    private final UUID uuid;
    private final int level;
    private final boolean isDev;

    private final float pitchCenter, pitchVariance;
    private final List<HeadpatSpec> headpats;

    private final JsonObject otherVals;

    @ApiStatus.Internal
    public Contributor(UUID uuid, JsonObject cfg) {
        this.uuid = uuid;
        this.otherVals = cfg;

        this.level = this.getInt("paucal:contributor_level", 0);
        this.isDev = this.getBool("paucal:is_dev", false);
        this.pitchCenter = this.getFloat("paucal:pat_pitch", 1f);
        this.pitchVariance = this.getFloat("paucal:pat_variance", 0.5f);

        var patsRaw = this.otherVals.get("paucal:pat_sound");
        this.headpats = HeadpatSpec.loadFromJson(patsRaw);
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

    @ApiStatus.Internal
    public Collection<String> neededGithubSounds() {
        var out = new ArrayList<String>();
        for (var hp : this.headpats) {
            if (hp.type == HeadpatSpec.Type.GITHUB) {
                out.add(hp.location);
            }
        }
        return out;
    }

    /**
     * Logic happens clientside to the <em>patter</em>, the pattee gets a packet like everyone else
     *
     * @param patter
     */
    public boolean doHeadpatSound(Vec3 patteePos, @Nullable Player patter, Level level) {
        if (this.headpats.isEmpty()) {
            return false;
        }

        if (level instanceof ServerLevel slevel) {
            var idx = level.random.nextInt(this.headpats.size());
            var patspec = this.headpats.get(idx);
            var pitch = this.pitchCenter + (float) (Math.random() - 0.5) * this.pitchVariance;

            IXplatAbstractions.INSTANCE.sendPacketNearS2C(patteePos, 64.0, slevel,
                patspec.makePacket(patteePos, pitch, patter));
        } // Otherwise, they will play the sound once they get the packet

        return true;
    }

    // =====

    @Nullable
    public String getString(String key) {
        return this.getString(key, null);
    }

    public String getString(String key, String fallback) {
        return GsonHelper.getAsString(otherVals, key, fallback);
    }

    @Nullable
    public Integer getInt(String key) {
        if (otherVals.has(key)) {
            return GsonHelper.getAsInt(otherVals, key);
        } else {
            return null;
        }
    }

    public int getInt(String key, int fallback) {
        return GsonHelper.getAsInt(otherVals, key, fallback);
    }

    @Nullable
    public Float getFloat(String key) {
        if (otherVals.has(key)) {
            return GsonHelper.getAsFloat(otherVals, key);
        } else {
            return null;
        }
    }

    public float getFloat(String key, float fallback) {
        return GsonHelper.getAsFloat(otherVals, key, fallback);
    }

    @Nullable
    public Boolean getBool(String key) {
        if (otherVals.has(key)) {
            return GsonHelper.getAsBoolean(otherVals, key);
        } else {
            return null;
        }
    }

    public boolean getBool(String key, boolean fallback) {
        return GsonHelper.getAsBoolean(this.otherVals, key, fallback);
    }


    public Set<String> allKeys() {
        return this.otherVals.keySet();
    }

    public JsonObject otherVals() {
        return this.otherVals;
    }
}
