package at.petrak.paucal.api.contrib;

import com.electronwill.nightconfig.core.AbstractConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public class Contributor {
    private final UUID uuid;
    private final int level;
    private final boolean isDev;

    private final AbstractConfig otherVals;

    public Contributor(UUID uuid, AbstractConfig cfg) {
        this.uuid = uuid;
        this.otherVals = cfg;

        this.level = (int) this.otherVals.get("paucal:contributor_level");
        this.isDev = (boolean) this.otherVals.get("paucal:is_dev");
    }

    @Nullable
    public String getString(String key) {
        return otherVals.get(key);
    }

    @Nullable
    public Integer getInt(String key) {
        Number n = otherVals.get(key);
        return n == null ? null : n.intValue();
    }

    @Nullable
    public Float getFloat(String key) {
        Number n = otherVals.get(key);
        return n == null ? null : n.floatValue();
    }

    @Nullable
    public <T> T get(String key) {
        return this.otherVals.get(key);
    }


    public Set<String> allKeys() {
        return this.otherVals.valueMap().keySet();
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

    public AbstractConfig otherVals() {
        return this.otherVals;
    }
}
