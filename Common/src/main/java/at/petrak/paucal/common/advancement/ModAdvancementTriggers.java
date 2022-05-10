package at.petrak.paucal.common.advancement;

import at.petrak.paucal.api.mixin.AccessorCriteriaTriggers;

public class ModAdvancementTriggers {
    public static final BeContributorTrigger BE_CONTRIBUTOR_TRIGGER = new BeContributorTrigger();

    public static void registerTriggers() {
        AccessorCriteriaTriggers.paucal$register(BE_CONTRIBUTOR_TRIGGER);
    }
}
