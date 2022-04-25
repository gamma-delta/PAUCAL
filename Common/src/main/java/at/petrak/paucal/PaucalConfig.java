package at.petrak.paucal;


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
            PaucalMod.LOGGER.warn("CommonConfigAccess was replaced! Old {} New {}",
                common.getClass().getName(), access.getClass().getName());
        }
        common = access;
    }
}
