package at.petrak.paucal.api;

import com.google.common.base.Suppliers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public interface PaucalAPI {
    String MOD_ID = "paucal";
    String CONTRIBUTOR_URL = "https://raw.githubusercontent.com/gamma-delta/contributors/main/paucal.toml";

    Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    Supplier<PaucalAPI> INSTANCE = Suppliers.memoize(() -> {
        try {
            return (PaucalAPI) Class.forName("at.petrak.paucal.common.impl.PaucalAPIImpl")
                .getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            LOGGER.warn("Unable to find PaucalAPIImpl, using a dummy");
            return new PaucalAPI() {
            };
        }
    });

    static PaucalAPI instance() {
        return INSTANCE.get();
    }
}
