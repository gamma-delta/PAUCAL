package at.petrak.paucal;


import at.petrak.paucal.api.PaucalAPI;

public class PaucalConfig {
    public interface ConfigAccess {
        boolean allowPats();

        boolean loadContributors();
    }

    private static ConfigAccess common = null;

    public static ConfigAccess common() {
        return common;
    }

    public static void setCommon(ConfigAccess access) {
        if (common != null) {
            PaucalAPI.LOGGER.warn("CommonConfigAccess was replaced! Old {} New {}", common.getClass().getName(),
                access.getClass().getName());
        }
        common = access;
    }
}
