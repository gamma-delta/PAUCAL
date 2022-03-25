package at.petrak.paucal.api.contrib;

import at.petrak.paucal.PaucalMod;
import com.electronwill.nightconfig.core.AbstractConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public class Contributor {
    private final UUID uuid;
    private final ContributorType contributorType;
    private final Map<String, Object> otherVals;

    public Contributor(UUID uuid, AbstractConfig cfg) {
        this.uuid = uuid;
        this.otherVals = cfg.valueMap();

        var level = (int) this.otherVals.get("paucal:contributorLevel");
        var isDev = (boolean) this.otherVals.get("paucal:isDev");
        var isCool = (boolean) this.otherVals.get("paucal:isCool");
        this.contributorType = new ContributorType(level, isDev, isCool);
    }
    
    public ContributorType getContributorType() {
        return contributorType;
    }

    @Nullable
    public String getString(String key) {
        Object value = this.otherVals.get(key);
        if (value == null) {
            return null;
        }
        if (value instanceof String x) {
            return x;
        } else {
            PaucalMod.LOGGER.warn("Tried to get non-string value of key '{}' (contributor {})", key, this.uuid);
            return null;
        }
    }

    @Nullable
    public Integer getInt(String key) {
        Object value = this.otherVals.get(key);
        if (value == null) {
            return null;
        }
        if (value instanceof Integer x) {
            return x;
        } else {
            PaucalMod.LOGGER.warn("Tried to get non-int value of key '{}' (contributor {})", key, this.uuid);
            return null;
        }
    }

    @Nullable
    public Float getFloat(String key) {
        Object value = this.otherVals.get(key);
        if (value == null) {
            return null;
        }
        if (value instanceof Float x) {
            return x;
        } else {
            PaucalMod.LOGGER.warn("Tried to get non-float value of key '{}' (contributor {})", key, this.uuid);
            return null;
        }
    }

    @Nullable
    public Object getRaw(String key) {
        return this.otherVals.get(key);
    }
}
