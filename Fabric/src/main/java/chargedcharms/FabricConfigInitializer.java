package chargedcharms;

import com.illusivesoulworks.spectrelib.config.SpectreConfigInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import chargedcharms.config.ConfigHandler;

public class FabricConfigInitializer implements SpectreConfigInitializer {

    @Override
    public void onInitializeConfig() {
        ChargedCharms.init();

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            ConfigHandler.init();
        });
    }

}
