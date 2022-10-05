package chargedcharms;

import com.illusivesoulworks.spectrelib.config.SpectreConfigInitializer;

import chargedcharms.data.recipe.ConfigResourceCondition;

public class FabricConfigInitializer implements SpectreConfigInitializer {

    @Override
    public void onInitialize() {
        ChargedCharms.init();
        ConfigResourceCondition.init();
    }

}
