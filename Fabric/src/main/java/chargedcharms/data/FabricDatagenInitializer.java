package chargedcharms.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import chargedcharms.data.integration.accessories.EntityDataProvider;
import chargedcharms.data.integration.accessories.SlotDataProvider;
import chargedcharms.data.integration.DynamicLightsDataProvider;
import chargedcharms.data.recipe.FabricModRecipeProvider;

public class FabricDatagenInitializer implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        FabricDataGenerator.Pack pack = gen.createPack();

        if (System.getProperty("chargedcharms.common_datagen") != null) {
            configureCommonDatagen(pack);
        }
        else {
            configureFabricDatagen(pack);
        }
    }

    public static void configureCommonDatagen(FabricDataGenerator.Pack pack) {
        pack.addProvider(ChargedCharmsItemTagProvider::new);
        pack.addProvider((dataOutput, registryFuture) -> new ChargedCharmsItemModelProvider(dataOutput));
        pack.addProvider(DynamicLightsDataProvider::new);
        pack.addProvider(ChargedCharmsLanguageProvider::new);
        pack.addProvider((dataOutput, registryFuture) -> new SlotDataProvider(dataOutput));
        pack.addProvider((dataOutput, registryFuture) -> new EntityDataProvider(dataOutput));
    }

    public static void configureFabricDatagen(FabricDataGenerator.Pack pack) {
        pack.addProvider(FabricModRecipeProvider::new);
    }

}
