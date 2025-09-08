package chargedcharms;

import chargedcharms.config.ConfigHandler;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import technology.roughness.whitenoise.config.WhiteNoiseInitializer;

import chargedcharms.data.recipe.ConfigResourceCondition;

public class FabricConfigInitializer implements WhiteNoiseInitializer {

    @Override
    public void onInitializeConfig() {
        ChargedCharms.initConfig();

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            ConfigHandler.init();
        });

        ConfigResourceCondition.init();
    }

}
