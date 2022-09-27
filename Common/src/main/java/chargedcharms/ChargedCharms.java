package chargedcharms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chargedcharms.common.CharmProviders;

public class ChargedCharms {

    public static final String MODID = "chargedcharms";
    public static final String MOD_NAME = "Charged Charms";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        CharmProviders.init();
    }

}
