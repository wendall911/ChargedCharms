package chargedcharms.data.recipe;

import java.util.function.Consumer;

import chargedcharms.common.crafting.recipe.AbsorptionChargeRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import chargedcharms.ChargedCharms;
import chargedcharms.common.crafting.recipe.RegenerationChargeRecipe;
import chargedcharms.common.crafting.recipe.TotemChargeRecipe;
import chargedcharms.common.item.ChargedCharmsItems;

public class CommonRecipeProvider extends RecipeProviderBase {

    public CommonRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return ChargedCharms.MOD_NAME + " - Recipes";
    }

    @Override
    protected void registerRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ChargedCharmsItems.regenerationCharm)
                .define('N', Items.IRON_NUGGET)
                .define('S', Items.COOKED_BEEF)
                .pattern("NNN")
                .pattern("NSN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromItem(Items.COOKED_BEEF))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ChargedCharmsItems.absorptionCharm)
                .define('N', Items.IRON_NUGGET)
                .define('A', Items.APPLE)
                .pattern("NNN")
                .pattern("NAN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromItem(Items.APPLE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ChargedCharmsItems.glowupCharm)
                .define('N', Items.GOLD_NUGGET)
                .define('G', Items.GLOW_BERRIES)
                .pattern("NNN")
                .pattern("NGN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromItem(Items.GLOW_BERRIES))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ChargedCharmsItems.totemCharm)
                .define('N', Items.IRON_NUGGET)
                .define('U', Items.TOTEM_OF_UNDYING)
                .pattern("NNN")
                .pattern("NUN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromItem(Items.TOTEM_OF_UNDYING))
                .save(consumer);

        specialRecipe(consumer, RegenerationChargeRecipe.SERIALIZER);
        specialRecipe(consumer, TotemChargeRecipe.SERIALIZER);
        specialRecipe(consumer, AbsorptionChargeRecipe.SERIALIZER);
    }

}
