package chargedcharms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.config.ConfigHandler;

import technology.roughness.whitenoise.config.WhiteNoiseConfig;
import technology.roughness.whitenoise.config.WhiteNoiseConfigLoader;
import technology.roughness.whitenoise.platform.Services;

public class ChargedCharms {

    public static final String MODID = "chargedcharms";
    public static final String MOD_NAME = "Charged Charms";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void initConfig() {
        if (Services.PLATFORM.isPhysicalClient()) {
            WhiteNoiseConfigLoader.add(WhiteNoiseConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC, MODID);
        }
        WhiteNoiseConfig commonConfig = WhiteNoiseConfigLoader.add(WhiteNoiseConfig.Type.COMMON, ConfigHandler.COMMON_SPEC, MODID);
        commonConfig.addLoadListener((config, flag) -> ConfigHandler.init());
        CharmEffectProviders.init();
    }

}