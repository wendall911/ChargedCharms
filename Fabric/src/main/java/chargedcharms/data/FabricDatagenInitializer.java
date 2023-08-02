package chargedcharms.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import net.minecraft.data.tags.BlockTagsProvider;

import chargedcharms.data.recipe.CommonRecipeProvider;
import chargedcharms.data.recipe.FabricModRecipeProvider;

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
        gen.addProvider(new CommonItemTagProvider(gen, blockTagsProvider));
        gen.addProvider(new FabricItemModelProvider(gen));
        gen.addProvider(new CommonRecipeProvider(gen));
    }

    public static void configureFabricDatagen(FabricDataGenerator gen) {
        gen.addProvider(new FabricItemTagProvider(gen, blockTagsProvider));
        gen.addProvider(new FabricModRecipeProvider(gen));
    }

}
