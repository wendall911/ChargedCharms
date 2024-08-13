package chargedcharms;

import chargedcharms.config.ConfigHandler;
import com.illusivesoulworks.spectrelib.config.SpectreLibInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import chargedcharms.data.recipe.ConfigResourceCondition;

public class FabricConfigInitializer implements SpectreLibInitializer {

    @Override
    public void onInitializeConfig() {
        ChargedCharms.initConfig();

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            ConfigHandler.init();
        });

        ConfigResourceCondition.init();
    }

}
