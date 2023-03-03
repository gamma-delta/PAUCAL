import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.common.ContributorsManifest;
import org.testng.annotations.Test;

public class ContributorLoadingTest {
    @Test
    public static void fetchContributors() {
        var contributors = ContributorsManifest.fetch();
        PaucalAPI.LOGGER.info("Got {} contributors.", contributors.size());
        contributors.forEach((k, v) -> {
            PaucalAPI.LOGGER.info("{}: {}", k, v);
        });
    }
}
