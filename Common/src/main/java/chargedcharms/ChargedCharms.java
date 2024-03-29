package chargedcharms;

import com.illusivesoulworks.spectrelib.config.SpectreConfig;
import com.illusivesoulworks.spectrelib.config.SpectreConfigLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.config.ConfigHandler;
import chargedcharms.platform.Services;

public class ChargedCharms {

    public static final String MODID = "chargedcharms";
    public static final String MOD_NAME = "Charged Charms";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        if (Services.PLATFORM.isPhysicalClient()) {
            SpectreConfigLoader.add(SpectreConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC, MODID);
        }
        SpectreConfig commonConfig = SpectreConfigLoader.add(SpectreConfig.Type.COMMON, ConfigHandler.COMMON_SPEC, MODID);
        commonConfig.addLoadListener((config, flag) -> ConfigHandler.init());
        CharmEffectProviders.init();
    }

}