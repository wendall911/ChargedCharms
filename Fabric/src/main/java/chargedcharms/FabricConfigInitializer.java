package chargedcharms;

import com.illusivesoulworks.spectrelib.config.SpectreConfigInitializer;

import chargedcharms.data.recipe.ConfigResourceCondition;

public class FabricConfigInitializer implements SpectreConfigInitializer {

    @Override
    public void onInitializeConfig() {
        ChargedCharms.init();
    }

}
