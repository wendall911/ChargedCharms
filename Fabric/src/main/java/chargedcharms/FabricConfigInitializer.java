package chargedcharms;

import com.illusivesoulworks.spectrelib.config.SpectreLibInitializer;

import chargedcharms.data.recipe.ConfigResourceCondition;

public class FabricConfigInitializer implements SpectreLibInitializer {

    @Override
    public void onInitializeConfig() {
        ChargedCharms.init();
        ConfigResourceCondition.init();
    }

}
