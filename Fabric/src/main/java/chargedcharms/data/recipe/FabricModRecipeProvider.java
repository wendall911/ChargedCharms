package chargedcharms.data.recipe;

import java.util.function.Consumer;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;

import net.minecraft.data.recipes.FinishedRecipe;

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
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        Consumer<FinishedRecipe> bmoWrapped = withConditions(consumer, DefaultResourceConditions.allModsLoaded(ModIntegration.BMO_MODID), ConfigResourceCondition.configDisabled("disableEnchTotemCharm"));

        RecipeProviderBase.regenerationCharm().save(withConditions(consumer, ConfigResourceCondition.configDisabled("disableRegenCharm")));
        RecipeProviderBase.absorptionCharm().save(withConditions(consumer, ConfigResourceCondition.configDisabled("disableAbsorptionCharm")));
        RecipeProviderBase.glowupCharm().save(withConditions(consumer, ConfigResourceCondition.configDisabled("disableGlowupCharm")));
        RecipeProviderBase.totemCharm().save(withConditions(consumer, ConfigResourceCondition.configDisabled("disableTotemCharm")));
        RecipeProviderBase.enchantedTotemCharm().save(bmoWrapped);
        RecipeProviderBase.speedCharm().save(withConditions(consumer, ConfigResourceCondition.configDisabled("disableSpeedCharm")));

        RecipeProviderBase.specialRecipe(withConditions(consumer, ConfigResourceCondition.configDisabled("disableRegenCharm")), RegenerationChargeRecipe.SERIALIZER);
        RecipeProviderBase.specialRecipe(withConditions(consumer, ConfigResourceCondition.configDisabled("disableTotemCharm")), TotemChargeRecipe.SERIALIZER);
        RecipeProviderBase.specialRecipe(withConditions(consumer, ConfigResourceCondition.configDisabled("disableAbsorptionCharm")), AbsorptionChargeRecipe.SERIALIZER);
        RecipeProviderBase.specialRecipe(bmoWrapped, EnchantedTotemChargeRecipe.SERIALIZER);
        RecipeProviderBase.specialRecipe(withConditions(consumer, ConfigResourceCondition.configDisabled("disableSpeedCharm")), SpeedChargeRecipe.SERIALIZER);
    }

}