package chargedcharms.data.recipe;

import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;

import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;

import chargedcharms.ChargedCharms;
import chargedcharms.common.crafting.recipe.AbsorptionChargeRecipe;
import chargedcharms.common.crafting.recipe.EnchantedTotemChargeRecipe;
import chargedcharms.common.crafting.recipe.RegenerationChargeRecipe;
import chargedcharms.common.crafting.recipe.SpeedChargeRecipe;
import chargedcharms.common.crafting.recipe.TotemChargeRecipe;
import chargedcharms.common.crafting.recipe.WaterBreathingChargeRecipe;
import chargedcharms.data.integration.ModIntegration;

public class FabricModRecipeProvider extends FabricRecipeProvider {

    public FabricModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryFuture) {
        super(output, registryFuture);
    }

    @Override
    public @NonNull String getName() {
        return ChargedCharms.MOD_NAME + " - Fabric Recipes";
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(@NonNull Provider registries,
            @NonNull BootstrapContext<Recipe<?>> recipes, @NonNull BootstrapContext<Advancement> advancements) {
        return new RecipeProvider(recipes, advancements) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemRegistry = registries.lookupOrThrow(Registries.ITEM);

                RecipeOutput bmoWrapped = withConditions(
                    output,
                    ResourceConditions.allModsLoaded(ModIntegration.BMO_MODID),
                    new ConfigResourceCondition("disableEnchTotemCharm")
                );

                RecipeProviderBase.regenerationCharm(itemRegistry).save(withConditions(
                    output,
                    new ConfigResourceCondition("disableRegenCharm")
                ));
                RecipeProviderBase.absorptionCharm(itemRegistry).save(withConditions(
                    output,
                    new ConfigResourceCondition("disableAbsorptionCharm")
                ));
                RecipeProviderBase.glowupCharm(itemRegistry).save(withConditions(
                    output,
                    new ConfigResourceCondition("disableGlowupCharm")
                ));
                RecipeProviderBase.totemCharm(itemRegistry).save(withConditions(
                    output,
                    new ConfigResourceCondition("disableTotemCharm")
                ));
                RecipeProviderBase.enchantedTotemCharm(itemRegistry).save(bmoWrapped);
                RecipeProviderBase.speedCharm(itemRegistry).save(withConditions(
                    output,
                    new ConfigResourceCondition("disableSpeedCharm")
                ));
                RecipeProviderBase.waterBreathingCharm(itemRegistry).save(withConditions(
                    output,
                    new ConfigResourceCondition("disableWaterBreathingCharm")
                ));
                RecipeProviderBase.specialRecipe(withConditions(
                    output,
                    new ConfigResourceCondition("disableRegenCharm")
                ), RegenerationChargeRecipe.SERIALIZER, RegenerationChargeRecipe::new);
                RecipeProviderBase.specialRecipe(withConditions(
                    output,
                    new ConfigResourceCondition("disableTotemCharm")
                ), TotemChargeRecipe.SERIALIZER, TotemChargeRecipe::new);
                RecipeProviderBase.specialRecipe(withConditions(
                    output,
                    new ConfigResourceCondition("disableAbsorptionCharm")
                ), AbsorptionChargeRecipe.SERIALIZER, AbsorptionChargeRecipe::new);
                RecipeProviderBase.specialRecipe(
                    bmoWrapped,
                    EnchantedTotemChargeRecipe.SERIALIZER,
                    EnchantedTotemChargeRecipe::new
                );
                RecipeProviderBase.specialRecipe(withConditions(
                    output,
                    new ConfigResourceCondition("disableSpeedCharm")
                ), SpeedChargeRecipe.SERIALIZER, SpeedChargeRecipe::new);
                RecipeProviderBase.specialRecipe(withConditions(
                    output,
                    new ConfigResourceCondition("disableWaterBreathingCharm")
                ), WaterBreathingChargeRecipe.SERIALIZER, WaterBreathingChargeRecipe::new);
            }
        };
    }

}
