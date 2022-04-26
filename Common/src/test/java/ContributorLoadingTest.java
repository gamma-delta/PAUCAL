import at.petrak.paucal.api.PaucalAPI;
import at.petrak.paucal.api.contrib.Contributors;
import org.testng.annotations.Test;

public class ContributorLoadingTest {
    @Test
    public static void fetchContributors() {
        var contributors = Contributors.fetch();
        PaucalAPI.LOGGER.info("Got {} contributors.", contributors.size());
        contributors.forEach((k, v) -> {
            PaucalAPI.LOGGER.info("{}: {}", k, v);
        });
    }
}
