package chargedcharms.data.recipe;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;

import chargedcharms.ChargedCharms;

public class CommonRecipeProvider extends RecipeProviderBase {

    public CommonRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return ChargedCharms.MOD_NAME + " - Recipes";
    }

    @Override
    protected void registerRecipes(Consumer<FinishedRecipe> consumer) {

    }

}