package chargedcharms.data.recipe;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;

import chargedcharms.mixin.RecipeProviderAccessor;
import chargedcharms.platform.Services;

public abstract class RecipeProviderBase implements DataProvider {

    private final DataGenerator generator;

    protected RecipeProviderBase(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(CachedOutput cache) throws IOException {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> recipes = Sets.newHashSet();

        registerRecipes((provider) -> {
            if (recipes.add(provider.getId())) {
                JsonObject advancement = provider.serializeAdvancement();

                RecipeProviderAccessor.callSaveRecipe(cache, provider.serializeRecipe(), path.resolve(
                        "data/"
                        + provider.getId().getNamespace()
                        + "/recipes/"
                        + provider.getId().getPath()
                        + ".json"
                ));

                if (advancement != null) {
                    Services.PLATFORM.saveRecipeAdvancement(this.generator, cache, advancement, path.resolve(
                        "data/"
                        + provider.getId().getNamespace()
                        + "/advancements/"
                        + provider.getAdvancementId().getPath()
                        + ".json"
                    ));
                }
            }
            else {
                throw new IllegalStateException("Duplicate recipe " + provider.getId());
            }
        });
    }

    protected abstract void registerRecipes(Consumer<FinishedRecipe> consumer);

}
