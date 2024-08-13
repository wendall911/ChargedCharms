package chargedcharms;

import chargedcharms.config.ConfigHandler;
import com.illusivesoulworks.spectrelib.config.SpectreConfigInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import chargedcharms.data.recipe.ConfigResourceCondition;

public class FabricConfigInitializer implements SpectreConfigInitializer {

    @Override
    public void onInitialize() {
        ChargedCharms.init();

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            ConfigHandler.init();
        });

        ConfigResourceCondition.init();
    }

}
