package chargedcharms.data.recipe;

import net.minecraft.core.HolderLookup;
import org.jetbrains.annotations.NotNull;

import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;

import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import chargedcharms.common.crafting.recipe.EnchantedTotemChargeRecipe;
import chargedcharms.common.crafting.recipe.AbsorptionChargeRecipe;
import chargedcharms.common.crafting.recipe.RegenerationChargeRecipe;
import chargedcharms.common.crafting.recipe.SpeedChargeRecipe;
import chargedcharms.common.crafting.recipe.TotemChargeRecipe;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.data.integration.ModIntegration;

import java.util.concurrent.CompletableFuture;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public class ForgeRecipeProvider extends RecipeProvider {

    public ForgeRecipeProvider(@NotNull final PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        ConditionalRecipe.builder()
            .condition(new ConfigResourceCondition("disableRegenCharm"))
            .recipe(RecipeProviderBase.regenerationCharm()::save)
            .advancement(prefix("recipes/misc/" + ChargedCharmsItems.regenerationCharmId))
            .save(recipeOutput, prefix(ChargedCharmsItems.regenerationCharmId));
        ConditionalRecipe.builder()
            .condition(new ConfigResourceCondition("disableAbsorptionCharm"))
            .recipe(RecipeProviderBase.absorptionCharm()::save)
            .advancement(prefix("recipes/misc/" + ChargedCharmsItems.absorptionCharmId))
            .save(recipeOutput, prefix(ChargedCharmsItems.absorptionCharmId));
        ConditionalRecipe.builder()
            .condition(new ConfigResourceCondition("disableGlowupCharm"))
            .recipe(RecipeProviderBase.glowupCharm()::save)
            .advancement(prefix("recipes/misc/" + ChargedCharmsItems.glowupCharmId))
            .save(recipeOutput, prefix(ChargedCharmsItems.glowupCharmId));
        ConditionalRecipe.builder()
            .condition(new ConfigResourceCondition("disableTotemCharm"))
            .recipe(RecipeProviderBase.totemCharm()::save)
            .advancement(prefix("recipes/misc/" + ChargedCharmsItems.totemCharmId))
            .save(recipeOutput, prefix(ChargedCharmsItems.totemCharmId));
        ConditionalRecipe.builder()
            .mainCondition(new ModLoadedCondition(ModIntegration.BMO_MODID))
            .condition(new ConfigResourceCondition("disableEnchTotemCharm"))
            .recipe(RecipeProviderBase.enchantedTotemCharm()::save)
            .advancement(prefix("recipes/misc/" + ChargedCharmsItems.enchantedTotemCharmId))
            .save(recipeOutput, prefix(ChargedCharmsItems.enchantedTotemCharmId));
        ConditionalRecipe.builder()
            .condition(new ConfigResourceCondition("disableSpeedCharm"))
            .recipe(RecipeProviderBase.speedCharm()::save)
            .advancement(prefix("recipes/misc/" + ChargedCharmsItems.speedCharmId))
            .save(recipeOutput, prefix(ChargedCharmsItems.speedCharmId));

        RecipeProviderBase.specialRecipe(
            recipeOutput,
            RegenerationChargeRecipe.SERIALIZER,
            RegenerationChargeRecipe::new
        );
        RecipeProviderBase.specialRecipe(
            recipeOutput,
            TotemChargeRecipe.SERIALIZER,
            TotemChargeRecipe::new
        );
        RecipeProviderBase.specialRecipe(
            recipeOutput,
            AbsorptionChargeRecipe.SERIALIZER,
            AbsorptionChargeRecipe::new
        );
        RecipeProviderBase.specialRecipe(
            recipeOutput,
            EnchantedTotemChargeRecipe.SERIALIZER,
            EnchantedTotemChargeRecipe::new
        );
        RecipeProviderBase.specialRecipe(
            recipeOutput,
            SpeedChargeRecipe.SERIALIZER,
            SpeedChargeRecipe::new
        );

    }

}
