package chargedcharms;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.wispforest.accessories.api.core.AccessoryRegistry;

import technology.roughness.whitenoise.config.WhiteNoiseConfig;
import technology.roughness.whitenoise.config.WhiteNoiseConfigLoader;
import technology.roughness.whitenoise.platform.Services;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.item.ChargedCharmBase;
import chargedcharms.config.ConfigHandler;
import chargedcharms.integration.ChargedCharmAccessory;

import static chargedcharms.platform.Services.PLATFORM;

import static technology.roughness.whitenoise.util.ResourceLocationHelper.loc;

public class ChargedCharms {

    public static final String MODID = "chargedcharms";
    public static final String MOD_NAME = "Charged Charms";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        CharmEffectProviders.init();

        PLATFORM.addItemRegistryCallback(item -> {
            if (item instanceof ChargedCharmBase chargedCharm) {
                AccessoryRegistry.register(item, new ChargedCharmAccessory(chargedCharm));
            }
        });
    }

    public static void initConfig() {
        if (Services.PLATFORM.isPhysicalClient()) {
            WhiteNoiseConfigLoader.add(WhiteNoiseConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC, MODID);
        }
        WhiteNoiseConfig commonConfig = WhiteNoiseConfigLoader.add(WhiteNoiseConfig.Type.COMMON, ConfigHandler.COMMON_SPEC, MODID);
        commonConfig.addLoadListener((config, flag) -> ConfigHandler.init());
    }

    public static ResourceLocation prefix(String path) {
        return loc(MODID, path);
    }

}
