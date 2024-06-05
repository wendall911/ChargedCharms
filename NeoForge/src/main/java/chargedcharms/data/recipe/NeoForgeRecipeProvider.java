package chargedcharms.data.recipe;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;

import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import chargedcharms.common.crafting.recipe.EnchantedTotemChargeRecipe;
import chargedcharms.common.crafting.recipe.AbsorptionChargeRecipe;
import chargedcharms.common.crafting.recipe.RegenerationChargeRecipe;
import chargedcharms.common.crafting.recipe.SpeedChargeRecipe;
import chargedcharms.common.crafting.recipe.TotemChargeRecipe;
import chargedcharms.data.integration.ModIntegration;

public class NeoForgeRecipeProvider extends RecipeProvider {

    public NeoForgeRecipeProvider(@NotNull final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        RecipeOutput bmoWrapped = recipeOutput.withConditions(
            new ModLoadedCondition(ModIntegration.BMO_MODID),
            new ConfigResourceCondition("disableEnchTotemCharm")
        );

        RecipeProviderBase.regenerationCharm().save(
            recipeOutput.withConditions(new ConfigResourceCondition("disableRegenCharm")));
        RecipeProviderBase.absorptionCharm().save(
            recipeOutput.withConditions(new ConfigResourceCondition("disableAbsorptionCharm")));
        RecipeProviderBase.glowupCharm().save(
            recipeOutput.withConditions(new ConfigResourceCondition("disableGlowupCharm")));
        RecipeProviderBase.totemCharm().save(
            recipeOutput.withConditions(new ConfigResourceCondition("disableTotemCharm")));
        RecipeProviderBase.enchantedTotemCharm().save(bmoWrapped);
        RecipeProviderBase.speedCharm().save(
            recipeOutput.withConditions(new ConfigResourceCondition("disableSpeedCharm")));
        RecipeProviderBase.specialRecipe(
            recipeOutput.withConditions(new ConfigResourceCondition("disableRegenCharm")),
            RegenerationChargeRecipe.SERIALIZER,
            RegenerationChargeRecipe::new
        );
        RecipeProviderBase.specialRecipe(
            recipeOutput.withConditions(new ConfigResourceCondition("disableTotemCharm")),
            TotemChargeRecipe.SERIALIZER,
            TotemChargeRecipe::new
        );
        RecipeProviderBase.specialRecipe(
            recipeOutput.withConditions(new ConfigResourceCondition("disableAbsorptionCharm")),
            AbsorptionChargeRecipe.SERIALIZER,
            AbsorptionChargeRecipe::new
        );
        RecipeProviderBase.specialRecipe(
            bmoWrapped,
            EnchantedTotemChargeRecipe.SERIALIZER,
            EnchantedTotemChargeRecipe::new
        );
        RecipeProviderBase.specialRecipe(
            recipeOutput.withConditions(new ConfigResourceCondition("disableSpeedCharm")),
            SpeedChargeRecipe.SERIALIZER,
            SpeedChargeRecipe::new
        );
    }

}
