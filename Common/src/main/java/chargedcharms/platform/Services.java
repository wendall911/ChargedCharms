package chargedcharms.platform;

import chargedcharms.ChargedCharms;
import chargedcharms.platform.services.IClientPlatform;
import chargedcharms.platform.services.IPlatform;
import technology.roughness.whitenoise.platform.ServicesBase;

public class Services extends ServicesBase {

    public static final IClientPlatform CLIENT_PLATFORM = load(ChargedCharms.LOGGER, IClientPlatform.class);
    public static final IPlatform PLATFORM = load(ChargedCharms.LOGGER, IPlatform.class);

}
