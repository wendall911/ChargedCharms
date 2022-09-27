package chargedcharms.platform;

import java.util.ServiceLoader;

import chargedcharms.ChargedCharms;
import chargedcharms.platform.services.IClientPlatform;
import chargedcharms.platform.services.IPlatform;

public class Services {

    public static final IClientPlatform CLIENT_PLATFORM = load(IClientPlatform.class);
    public static final IPlatform PLATFORM = load(IPlatform.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
            .findFirst()
            .orElseThrow(
                () -> new NullPointerException("Failed to load service for " + clazz.getName()));
        ChargedCharms.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);

        return loadedService;
    }

}
