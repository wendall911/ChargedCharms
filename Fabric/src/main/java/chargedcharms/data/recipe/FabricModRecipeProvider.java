package chargedcharms.data.recipe;

import java.util.function.Consumer;

import chargedcharms.common.crafting.recipe.EnchantedTotemChargeRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import chargedcharms.ChargedCharms;
import chargedcharms.common.TagManager;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.data.integration.ModIntegration;
import static chargedcharms.data.recipe.RecipeProviderBase.conditionsFromTag;

public class FabricModRecipeProvider extends FabricRecipeProvider {

    public FabricModRecipeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public String getName() {
        return ChargedCharms.MOD_NAME + " - Fabric Recipes";
    }

    @Override
    protected void generateRecipes(Consumer<FinishedRecipe> consumer) {
        Consumer<FinishedRecipe> bmoWrapped = withConditions(
            consumer,
            DefaultResourceConditions.allModsLoaded(ModIntegration.BMO_MODID)
        );

        ShapedRecipeBuilder.shaped(ChargedCharmsItems.enchantedTotemCharm)
                .define('N', Items.IRON_NUGGET)
                .define('S', TagManager.Items.ENCHANTED_TOTEMS)
                .pattern("NNN")
                .pattern("NSN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromTag(TagManager.Items.ENCHANTED_TOTEMS))
                .save(bmoWrapped);

        RecipeProviderBase.specialRecipe(bmoWrapped, EnchantedTotemChargeRecipe.SERIALIZER);
    }

}