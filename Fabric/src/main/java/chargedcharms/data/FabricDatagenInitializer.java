package chargedcharms.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import chargedcharms.data.recipe.CommonRecipeProvider;
import chargedcharms.data.recipe.FabricModRecipeProvider;

public class FabricDatagenInitializer implements DataGeneratorEntrypoint {

    private static FabricTagProvider.BlockTagProvider fabricBlockTagProvider;
    private static BlockTagProvider commonBlockTagProvider;

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        FabricDataGenerator.Pack pack = gen.createPack();
        fabricBlockTagProvider = pack.addProvider(FabricBlockTagProvider::new);

        if (System.getProperty("chargedcharms.common_datagen") != null) {
            configureCommonDatagen(pack);
        }
        else {
            configureFabricDatagen(pack);
        }
    }

    public static void configureCommonDatagen(FabricDataGenerator.Pack pack) {
        pack.addProvider((dataOutput, registryFuture) -> new CommonItemTagProvider(dataOutput, registryFuture, commonBlockTagProvider));
        pack.addProvider((dataOutput, registryFuture) -> new FabricItemModelProvider(dataOutput));
        pack.addProvider((dataOutput, registryFuture) -> new CommonRecipeProvider(dataOutput));
    }

    public static void configureFabricDatagen(FabricDataGenerator.Pack pack) {
        pack.addProvider((dataOutput, registryFuture) -> new FabricItemTagProvider(dataOutput, registryFuture, fabricBlockTagProvider));
        pack.addProvider((dataOutput, registryFuture) -> new FabricModRecipeProvider(dataOutput));
    }

}
