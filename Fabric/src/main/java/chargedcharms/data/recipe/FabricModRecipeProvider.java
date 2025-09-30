package chargedcharms.data.recipe;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;

import chargedcharms.ChargedCharms;
import chargedcharms.common.crafting.recipe.AbsorptionChargeRecipe;
import chargedcharms.common.crafting.recipe.EnchantedTotemChargeRecipe;
import chargedcharms.common.crafting.recipe.RegenerationChargeRecipe;
import chargedcharms.common.crafting.recipe.SpeedChargeRecipe;
import chargedcharms.common.crafting.recipe.TotemChargeRecipe;
import chargedcharms.common.crafting.recipe.WaterBreathingChargeRecipe;
import chargedcharms.data.integration.ModIntegration;

public class FabricModRecipeProvider extends FabricRecipeProvider {

    public FabricModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryFuture) {
        super(output, registryFuture);
    }

    @Override
    public @NotNull String getName() {
        return ChargedCharms.MOD_NAME + " - Fabric Recipes";
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        RecipeOutput bmoWrapped = withConditions(
            recipeOutput,
            ResourceConditions.allModsLoaded(ModIntegration.BMO_MODID),
            new ConfigResourceCondition("disableEnchTotemCharm")
        );

        RecipeProviderBase.regenerationCharm().save(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableRegenCharm")
        ));
        RecipeProviderBase.absorptionCharm().save(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableAbsorptionCharm")
        ));
        RecipeProviderBase.glowupCharm().save(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableGlowupCharm")
        ));
        RecipeProviderBase.totemCharm().save(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableTotemCharm")
        ));
        RecipeProviderBase.enchantedTotemCharm().save(bmoWrapped);
        RecipeProviderBase.speedCharm().save(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableSpeedCharm")
        ));
        RecipeProviderBase.waterBreathingCharm().save(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableWaterBreathingCharm")
        ));
        RecipeProviderBase.specialRecipe(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableRegenCharm")
        ), RegenerationChargeRecipe.SERIALIZER, RegenerationChargeRecipe::new);
        RecipeProviderBase.specialRecipe(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableTotemCharm")
        ), TotemChargeRecipe.SERIALIZER, TotemChargeRecipe::new);
        RecipeProviderBase.specialRecipe(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableAbsorptionCharm")
        ), AbsorptionChargeRecipe.SERIALIZER, AbsorptionChargeRecipe::new);
        RecipeProviderBase.specialRecipe(
            bmoWrapped,
            EnchantedTotemChargeRecipe.SERIALIZER,
            EnchantedTotemChargeRecipe::new
        );
        RecipeProviderBase.specialRecipe(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableSpeedCharm")
        ), SpeedChargeRecipe.SERIALIZER, SpeedChargeRecipe::new);
        RecipeProviderBase.specialRecipe(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableWaterBreathingCharm")
        ), WaterBreathingChargeRecipe.SERIALIZER, WaterBreathingChargeRecipe::new);
    }

}
