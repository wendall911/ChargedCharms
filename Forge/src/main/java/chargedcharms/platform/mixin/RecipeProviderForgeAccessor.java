package chargedcharms.platform.mixin;

import java.nio.file.Path;

import com.google.gson.JsonObject;

import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.RecipeProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RecipeProvider.class)
public interface RecipeProviderForgeAccessor {

    @Invoker("saveAdvancement")
    void callSaveRecipeAdvancement(HashCache cache, JsonObject json, Path path);

}
