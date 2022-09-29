package chargedcharms.data.recipe;

import java.util.Objects;
import java.util.function.Consumer;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.ItemLike;

import chargedcharms.ChargedCharms;
import chargedcharms.common.crafting.recipe.RegenerationChargeRecipe;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.common.TagManager;
import chargedcharms.data.integration.ModIntegration;
import chargedcharms.data.recipe.condition.ModLoadedCondition;
import chargedcharms.mixin.RecipeProviderAccessor;
import static chargedcharms.util.ResourceLocationHelper.prefix;

public class RecipeProvider extends RecipeProviderBase {

    public RecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return ChargedCharms.MOD_NAME + " - Recipes";
    }

    @Override
    protected void registerRecipes(Consumer<FinishedRecipe> consumer) {
        Consumer<FinishedRecipe> bmoWrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.BMO_MODID));

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

        ShapedRecipeBuilder.shaped(ChargedCharmsItems.enchantedTotemCharm)
                .define('N', Items.IRON_NUGGET)
                .define('S', TagManager.Items.ENCHANTED_TOTEMS)
                .pattern("NNN")
                .pattern("NSN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromTag(TagManager.Items.ENCHANTED_TOTEMS))
                .save(bmoWrapped);

        specialRecipe(consumer, RegenerationChargeRecipe.SERIALIZER);
    }

    public static InventoryChangeTrigger.TriggerInstance conditionsFromItem(ItemLike item) {
        return RecipeProviderAccessor.cc_condition(ItemPredicate.Builder.item().of(item).build());
    }

    public static InventoryChangeTrigger.TriggerInstance conditionsFromTag(TagKey<Item> key) {
        return RecipeProviderAccessor.cc_condition(ItemPredicate.Builder.item().of(key).build());
    }

    protected void specialRecipe(Consumer<FinishedRecipe> consumer, SimpleRecipeSerializer<?> serializer) {
        ResourceLocation name = Registry.RECIPE_SERIALIZER.getKey(serializer);

        SpecialRecipeBuilder.special(serializer).save(consumer, prefix("dynamic/" + Objects.requireNonNull(name).getPath()).toString());
    }

    private static Consumer<FinishedRecipe> withCondition(Consumer<FinishedRecipe> consumer, ICondition... conditions) {
        ConsumerWrapperBuilder builder = ConsumerWrapperBuilder.wrap();

        for (ICondition condition : conditions) {
            builder.addCondition(condition);
        }

        return builder.build(consumer);
    }

}
