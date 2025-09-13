package chargedcharms;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import technology.roughness.whitenoise.config.WhiteNoiseConfigInitializer;

import chargedcharms.config.ConfigHandler;

public class FabricConfigInitializer implements WhiteNoiseConfigInitializer {

    @Override
    public void onInitializeConfig() {
        ChargedCharms.initConfig();

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            ConfigHandler.init();
        });
    }

}
