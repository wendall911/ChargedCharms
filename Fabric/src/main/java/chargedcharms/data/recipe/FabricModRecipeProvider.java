package chargedcharms.data.recipe;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.CustomRecipe;

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
    public String getName() {
        return ChargedCharms.MOD_NAME + " - Fabric Recipes";
    }

    @Override
    protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput recipeOutput) {
        HolderLookup.RegistryLookup<Item> itemRegistry = registries.lookupOrThrow(Registries.ITEM);
        RecipeOutput bmoWrapped = withConditions(
            recipeOutput,
            ResourceConditions.allModsLoaded(ModIntegration.BMO_MODID),
            new ConfigResourceCondition("disableEnchTotemCharm")
        );

        RecipeProviderBase.regenerationCharm(itemRegistry).save(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableRegenCharm")
        ));
        RecipeProviderBase.absorptionCharm(itemRegistry).save(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableAbsorptionCharm")
        ));
        RecipeProviderBase.glowupCharm(itemRegistry).save(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableGlowupCharm")
        ));
        RecipeProviderBase.totemCharm(itemRegistry).save(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableTotemCharm")
        ));
        RecipeProviderBase.enchantedTotemCharm(itemRegistry).save(bmoWrapped);
        RecipeProviderBase.speedCharm(itemRegistry).save(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableSpeedCharm")
        ));
        RecipeProviderBase.waterBreathingCharm(itemRegistry).save(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableWaterBreathingCharm")
        ));
        RecipeProviderBase.specialRecipe(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableRegenCharm")
        ), (CustomRecipe.Serializer<?>) RegenerationChargeRecipe.SERIALIZER, RegenerationChargeRecipe::new);
        RecipeProviderBase.specialRecipe(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableTotemCharm")
        ), (CustomRecipe.Serializer<?>) TotemChargeRecipe.SERIALIZER, TotemChargeRecipe::new);
        RecipeProviderBase.specialRecipe(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableAbsorptionCharm")
        ), (CustomRecipe.Serializer<?>) AbsorptionChargeRecipe.SERIALIZER, AbsorptionChargeRecipe::new);
        RecipeProviderBase.specialRecipe(
            bmoWrapped,
            (CustomRecipe.Serializer<?>) EnchantedTotemChargeRecipe.SERIALIZER,
            EnchantedTotemChargeRecipe::new
        );
        RecipeProviderBase.specialRecipe(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableSpeedCharm")
        ), (CustomRecipe.Serializer<?>) SpeedChargeRecipe.SERIALIZER, SpeedChargeRecipe::new);
        RecipeProviderBase.specialRecipe(withConditions(
            recipeOutput,
            new ConfigResourceCondition("disableWaterBreathingCharm")
        ), (CustomRecipe.Serializer<?>) WaterBreathingChargeRecipe.SERIALIZER, WaterBreathingChargeRecipe::new);

        return new CommonRecipeProvider(registries, recipeOutput);
    }

}
