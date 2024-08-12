package chargedcharms;

import com.illusivesoulworks.spectrelib.config.SpectreLibInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import chargedcharms.data.recipe.ConfigResourceCondition;

public class FabricConfigInitializer implements SpectreLibInitializer {

    @Override
    public void onInitializeConfig() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            ChargedCharms.initConfig();
        });

        ConfigResourceCondition.init();
    }

}
