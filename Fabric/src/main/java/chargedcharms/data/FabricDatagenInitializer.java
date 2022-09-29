package chargedcharms.data;

import chargedcharms.data.recipe.RecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import net.minecraft.data.tags.BlockTagsProvider;

public class FabricDatagenInitializer implements DataGeneratorEntrypoint {

    private static BlockTagsProvider blockTagsProvider;

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        blockTagsProvider = new BlockTagsProvider(gen);

        if (System.getProperty("chargedcharms.common_datagen") != null) {
            configureCommonDatagen(gen);
        }
        else {
            configureFabricDatagen(gen);
        }
    }

    public static void configureCommonDatagen(FabricDataGenerator gen) {
        gen.addProvider(true, new CommonItemTagProvider(gen, blockTagsProvider));
        gen.addProvider(true, new FabricItemModelProvider(gen));
        gen.addProvider(true, new RecipeProvider(gen));
    }

    public static void configureFabricDatagen(FabricDataGenerator gen) {
        gen.addProvider(true, new FabricItemTagProvider(gen, blockTagsProvider));
    }

}
