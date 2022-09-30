package chargedcharms.data.recipe;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import chargedcharms.ChargedCharms;
import chargedcharms.common.crafting.recipe.EnchantedTotemChargeRecipe;
import chargedcharms.common.TagManager;
import chargedcharms.common.item.ChargedCharmsItems;
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
        Consumer<FinishedRecipe> bmoWrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.BMO_MODID));

        ShapedRecipeBuilder.shaped(ChargedCharmsItems.enchantedTotemCharm)
                .define('N', Items.IRON_NUGGET)
                .define('S', TagManager.Items.ENCHANTED_TOTEMS)
                .pattern("NNN")
                .pattern("NSN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromTag(TagManager.Items.ENCHANTED_TOTEMS))
                .save(bmoWrapped);

        specialRecipe(bmoWrapped, EnchantedTotemChargeRecipe.SERIALIZER);
    }

    private static Consumer<FinishedRecipe> withCondition(Consumer<FinishedRecipe> consumer, ICondition... conditions) {
        ConsumerWrapperBuilder builder = ConsumerWrapperBuilder.wrap();

        for (ICondition condition : conditions) {
            builder.addCondition(condition);
        }

        return builder.build(consumer);
    }

}