package chargedcharms;

import com.illusivesoulworks.spectrelib.config.SpectreConfig;
import com.illusivesoulworks.spectrelib.config.SpectreConfigLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chargedcharms.common.CharmProviders;
import chargedcharms.config.ConfigHandler;

public class ChargedCharms {

    public static final String MODID = "chargedcharms";
    public static final String MOD_NAME = "Charged Charms";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        SpectreConfigLoader.add(SpectreConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC, MODID);
        SpectreConfigLoader.add(SpectreConfig.Type.COMMON, ConfigHandler.COMMON_SPEC, MODID);
        CharmProviders.init();
    }

}
