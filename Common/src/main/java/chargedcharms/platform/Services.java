package chargedcharms.platform;

import chargedcharms.ChargedCharms;
import chargedcharms.platform.services.IClientPlatform;
import chargedcharms.platform.services.IPlatform;
import chargedcharms.platform.services.IREIHelper;

public class Services extends technology.roughness.whitenoise.platform.ServicesBase {

    public static final IClientPlatform CLIENT_PLATFORM = load(ChargedCharms.LOGGER, IClientPlatform.class);
    public static final IPlatform PLATFORM = load(ChargedCharms.LOGGER, IPlatform.class);
    public static final IREIHelper REI_HELPER = loadConditional(ChargedCharms.LOGGER, IREIHelper.class, "roughlyenoughitems");

}
