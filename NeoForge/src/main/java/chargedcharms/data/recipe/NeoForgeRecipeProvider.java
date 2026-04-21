package chargedcharms.data.recipe;

import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Item;

import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import chargedcharms.ChargedCharms;
import chargedcharms.common.crafting.recipe.EnchantedTotemChargeRecipe;
import chargedcharms.common.crafting.recipe.AbsorptionChargeRecipe;
import chargedcharms.common.crafting.recipe.RegenerationChargeRecipe;
import chargedcharms.common.crafting.recipe.SpeedChargeRecipe;
import chargedcharms.common.crafting.recipe.TotemChargeRecipe;
import chargedcharms.common.crafting.recipe.WaterBreathingChargeRecipe;
import chargedcharms.data.integration.ModIntegration;

public class NeoForgeRecipeProvider extends RecipeProvider.Runner {

    public NeoForgeRecipeProvider(@NonNull final PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    public @NonNull String getName() {
        return ChargedCharms.MOD_NAME + " - NeoForge Recipes";
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider provider, @NonNull RecipeOutput recipeOutput) {
        return new VanillaRecipeProvider(provider, recipeOutput) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemRegistry = registries.lookupOrThrow(Registries.ITEM);

                RecipeOutput bmoWrapped = recipeOutput.withConditions(
                        new ModLoadedCondition(ModIntegration.BMO_MODID),
                        new ConfigResourceCondition("disableEnchTotemCharm")
                );

                RecipeProviderBase.regenerationCharm(itemRegistry).save(
                        recipeOutput.withConditions(new ConfigResourceCondition("disableRegenCharm")));
                RecipeProviderBase.absorptionCharm(itemRegistry).save(
                        recipeOutput.withConditions(new ConfigResourceCondition("disableAbsorptionCharm")));
                RecipeProviderBase.glowupCharm(itemRegistry).save(
                        recipeOutput.withConditions(new ConfigResourceCondition("disableGlowupCharm")));
                RecipeProviderBase.totemCharm(itemRegistry).save(
                        recipeOutput.withConditions(new ConfigResourceCondition("disableTotemCharm")));
                RecipeProviderBase.enchantedTotemCharm(itemRegistry).save(bmoWrapped);
                RecipeProviderBase.speedCharm(itemRegistry).save(
                        recipeOutput.withConditions(new ConfigResourceCondition("disableSpeedCharm")));
                RecipeProviderBase.waterBreathingCharm(itemRegistry).save(
                        recipeOutput.withConditions(new ConfigResourceCondition("disableWaterBreathingCharm")));
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
                RecipeProviderBase.specialRecipe(
                        recipeOutput.withConditions(new ConfigResourceCondition("disableWaterBreathingCharm")),
                        WaterBreathingChargeRecipe.SERIALIZER,
                        chargedcharms.common.crafting.recipe.WaterBreathingChargeRecipe::new
                );
            }
        };
    }

}
