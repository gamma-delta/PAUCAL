package at.petrak.paucal.contrib;

import com.electronwill.nightconfig.core.AbstractConfig;

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
}
