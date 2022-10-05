package chargedcharms.data.recipe;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;

import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import chargedcharms.ChargedCharms;
import chargedcharms.common.crafting.recipe.EnchantedTotemChargeRecipe;
import chargedcharms.common.crafting.recipe.AbsorptionChargeRecipe;
import chargedcharms.common.crafting.recipe.RegenerationChargeRecipe;
import chargedcharms.common.crafting.recipe.TotemChargeRecipe;
import chargedcharms.data.integration.ModIntegration;

public class ForgeRecipeProvider extends RecipeProviderBase {

    public ForgeRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return ChargedCharms.MOD_NAME + " - Forge Recipes";
    }

    @Override
    protected void registerRecipes(Consumer<FinishedRecipe> consumer) {
        Consumer<FinishedRecipe> bmoWrapped = withConditions(consumer, new ModLoadedCondition(ModIntegration.BMO_MODID), new ConfigResourceCondition("disableEnchTotemCharm"));

        regenerationCharm().save(withConditions(consumer, new ConfigResourceCondition(("disableRegenCharm"))));
        absorptionCharm().save(withConditions(consumer, new ConfigResourceCondition(("disableAbsorptionCharm"))));
        glowupCharm().save(withConditions(consumer, new ConfigResourceCondition(("disableGlowupCharm"))));
        totemCharm().save(withConditions(consumer, new ConfigResourceCondition(("disableTotemCharm"))));
        enchantedTotemCharm().save(bmoWrapped);

        specialRecipe(withConditions(consumer, new ConfigResourceCondition(("disableRegenCharm"))), RegenerationChargeRecipe.SERIALIZER);
        specialRecipe(withConditions(consumer, new ConfigResourceCondition(("disableTotemCharm"))), TotemChargeRecipe.SERIALIZER);
        specialRecipe(withConditions(consumer, new ConfigResourceCondition(("disableAbsorptionCharm"))), AbsorptionChargeRecipe.SERIALIZER);
        specialRecipe(bmoWrapped, EnchantedTotemChargeRecipe.SERIALIZER);
    }

    private static Consumer<FinishedRecipe> withConditions(Consumer<FinishedRecipe> consumer, ICondition... conditions) {
        ConsumerWrapperBuilder builder = ConsumerWrapperBuilder.wrap();

        for (ICondition condition : conditions) {
            builder.addCondition(condition);
        }

        return builder.build(consumer);
    }

}