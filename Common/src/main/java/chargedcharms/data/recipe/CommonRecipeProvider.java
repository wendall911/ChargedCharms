package chargedcharms.data.recipe;

import java.util.function.Consumer;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;

import chargedcharms.ChargedCharms;

import javax.annotation.Nonnull;

public class CommonRecipeProvider extends RecipeProviderBase {

    public CommonRecipeProvider(@Nonnull final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return ChargedCharms.MOD_NAME + " - Recipes";
    }

    @Override
    protected void registerRecipes(Consumer<FinishedRecipe> consumer) {

    }

}