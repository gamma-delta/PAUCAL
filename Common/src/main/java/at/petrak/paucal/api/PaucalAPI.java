package at.petrak.paucal.api;

import at.petrak.paucal.api.contrib.Contributor;
import at.petrak.paucal.common.Contributors;
import com.google.common.base.Suppliers;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.function.Supplier;

public interface PaucalAPI {
    String MOD_ID = "paucal";
    String CONTRIBUTOR_URL = "https://raw.githubusercontent.com/gamma-delta/contributors/main/paucal.toml";

    Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Nullable
    default Contributor getContributor(UUID uuid) {
        return Contributors.getContributor(uuid);
    }

    Supplier<PaucalAPI> INSTANCE = Suppliers.memoize(() -> {
        try {
            return (PaucalAPI) Class.forName("at.petrak.paucal.common.impl.PaucalAPIImpl")
                .getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            LOGGER.warn("Unable to find a PaucalAPIImpl, using a dummy");
            return new PaucalAPI() {
            };
        }
    });

    static PaucalAPI instance() {
        return INSTANCE.get();
    }
}
