package chargedcharms.platform.mixin;

import java.nio.file.Path;

import com.google.gson.JsonObject;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.recipes.RecipeProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RecipeProvider.class)
public interface RecipeProviderForgeAccessor {

    @Invoker("saveAdvancement")
    void callSaveRecipeAdvancement(CachedOutput cache, JsonObject json, Path path);

}
