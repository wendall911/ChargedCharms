package chargedcharms.data.recipe;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.worldgen.BootstrapContext;

import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import chargedcharms.common.crafting.recipe.EnchantedTotemChargeRecipe;
import chargedcharms.common.crafting.recipe.AbsorptionChargeRecipe;
import chargedcharms.common.crafting.recipe.RegenerationChargeRecipe;
import chargedcharms.common.crafting.recipe.SpeedChargeRecipe;
import chargedcharms.common.crafting.recipe.TotemChargeRecipe;
import chargedcharms.common.crafting.recipe.WaterBreathingChargeRecipe;
import chargedcharms.data.integration.ModIntegration;

public class NeoForgeRecipeProvider extends RecipeProvider {

    public NeoForgeRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    public void buildRecipes() {
        RecipeOutput bmoWrapped = this.output.withConditions(
                new ModLoadedCondition(ModIntegration.BMO_MODID),
                new ConfigResourceCondition("disableEnchTotemCharm")
        );

        RecipeProviderBase.regenerationCharm(this.items).save(
                this.output.withConditions(new ConfigResourceCondition("disableRegenCharm")));
        RecipeProviderBase.absorptionCharm(this.items).save(
                this.output.withConditions(new ConfigResourceCondition("disableAbsorptionCharm")));
        RecipeProviderBase.glowupCharm(this.items).save(
                this.output.withConditions(new ConfigResourceCondition("disableGlowupCharm")));
        RecipeProviderBase.totemCharm(this.items).save(
                this.output.withConditions(new ConfigResourceCondition("disableTotemCharm")));
        RecipeProviderBase.enchantedTotemCharm(this.items).save(bmoWrapped);
        RecipeProviderBase.speedCharm(this.items).save(
                this.output.withConditions(new ConfigResourceCondition("disableSpeedCharm")));
        RecipeProviderBase.waterBreathingCharm(this.items).save(
                this.output.withConditions(new ConfigResourceCondition("disableWaterBreathingCharm")));
        RecipeProviderBase.specialRecipe(
                this.output.withConditions(new ConfigResourceCondition("disableRegenCharm")),
                RegenerationChargeRecipe.SERIALIZER,
                RegenerationChargeRecipe::new
        );
        RecipeProviderBase.specialRecipe(
                this.output.withConditions(new ConfigResourceCondition("disableTotemCharm")),
                TotemChargeRecipe.SERIALIZER,
                TotemChargeRecipe::new
        );
        RecipeProviderBase.specialRecipe(
                this.output.withConditions(new ConfigResourceCondition("disableAbsorptionCharm")),
                AbsorptionChargeRecipe.SERIALIZER,
                AbsorptionChargeRecipe::new
        );
        RecipeProviderBase.specialRecipe(
                bmoWrapped,
                EnchantedTotemChargeRecipe.SERIALIZER,
                EnchantedTotemChargeRecipe::new
        );
        RecipeProviderBase.specialRecipe(
                this.output.withConditions(new ConfigResourceCondition("disableSpeedCharm")),
                SpeedChargeRecipe.SERIALIZER,
                SpeedChargeRecipe::new
        );
        RecipeProviderBase.specialRecipe(
                this.output.withConditions(new ConfigResourceCondition("disableWaterBreathingCharm")),
                WaterBreathingChargeRecipe.SERIALIZER,
                chargedcharms.common.crafting.recipe.WaterBreathingChargeRecipe::new
        );
    }

}
