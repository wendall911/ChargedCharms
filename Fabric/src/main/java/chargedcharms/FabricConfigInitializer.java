package chargedcharms;

import com.illusivesoulworks.spectrelib.config.SpectreConfigInitializer;

public class FabricConfigInitializer implements SpectreConfigInitializer {

    @Override
    public void onInitializeConfig() {
        ChargedCharms.init();
    }

}
