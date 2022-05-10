package at.petrak.paucal.api.contrib;

import com.electronwill.nightconfig.core.AbstractConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public class Contributor {
    private final UUID uuid;
    private final ContributorType contributorType;
    private final AbstractConfig otherVals;

    public Contributor(UUID uuid, AbstractConfig cfg) {
        this.uuid = uuid;
        this.otherVals = cfg;

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

    public UUID getUuid() {
        return uuid;
    }

    public AbstractConfig otherVals() {
        return this.otherVals;
    }
}
