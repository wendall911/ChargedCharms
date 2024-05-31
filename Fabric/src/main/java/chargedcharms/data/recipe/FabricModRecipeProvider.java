package chargedcharms.data.recipe;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;

import net.minecraft.data.recipes.RecipeOutput;

import chargedcharms.ChargedCharms;
import chargedcharms.common.crafting.recipe.AbsorptionChargeRecipe;
import chargedcharms.common.crafting.recipe.EnchantedTotemChargeRecipe;
import chargedcharms.common.crafting.recipe.RegenerationChargeRecipe;
import chargedcharms.common.crafting.recipe.SpeedChargeRecipe;
import chargedcharms.common.crafting.recipe.TotemChargeRecipe;
import chargedcharms.data.integration.ModIntegration;

public class FabricModRecipeProvider extends FabricRecipeProvider {

    public FabricModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return ChargedCharms.MOD_NAME + " - Fabric Recipes";
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        RecipeOutput bmoWrapped = withConditions(
            recipeOutput,
            DefaultResourceConditions.allModsLoaded(ModIntegration.BMO_MODID),
            ConfigResourceCondition.configDisabled("disableEnchTotemCharm")
        );

        RecipeProviderBase.regenerationCharm().save(withConditions(
            recipeOutput,
            ConfigResourceCondition.configDisabled("disableRegenCharm")
        ));
        RecipeProviderBase.absorptionCharm().save(withConditions(
            recipeOutput,
            ConfigResourceCondition.configDisabled("disableAbsorptionCharm")
        ));
        RecipeProviderBase.glowupCharm().save(withConditions(
            recipeOutput,
            ConfigResourceCondition.configDisabled("disableGlowupCharm")
        ));
        RecipeProviderBase.totemCharm().save(withConditions(
            recipeOutput,
            ConfigResourceCondition.configDisabled("disableTotemCharm")
        ));
        RecipeProviderBase.enchantedTotemCharm().save(bmoWrapped);
        RecipeProviderBase.speedCharm().save(withConditions(
            recipeOutput,
            ConfigResourceCondition.configDisabled("disableSpeedCharm")
        ));
        RecipeProviderBase.specialRecipe(withConditions(
            recipeOutput,
            ConfigResourceCondition.configDisabled("disableRegenCharm")
        ), RegenerationChargeRecipe.SERIALIZER, RegenerationChargeRecipe::new);
        RecipeProviderBase.specialRecipe(withConditions(
            recipeOutput,
            ConfigResourceCondition.configDisabled("disableTotemCharm")
        ), TotemChargeRecipe.SERIALIZER, TotemChargeRecipe::new);
        RecipeProviderBase.specialRecipe(withConditions(
            recipeOutput,
            ConfigResourceCondition.configDisabled("disableAbsorptionCharm")
        ), AbsorptionChargeRecipe.SERIALIZER, AbsorptionChargeRecipe::new);
        RecipeProviderBase.specialRecipe(
            bmoWrapped,
            EnchantedTotemChargeRecipe.SERIALIZER,
            EnchantedTotemChargeRecipe::new
        );
        RecipeProviderBase.specialRecipe(withConditions(
            recipeOutput,
            ConfigResourceCondition.configDisabled("disableSpeedCharm")
        ), SpeedChargeRecipe.SERIALIZER, SpeedChargeRecipe::new);
    }

}