package chargedcharms.data.recipe;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.Nullable;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.ItemLike;

import chargedcharms.common.TagManager;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.mixin.RecipeProviderAccessor;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public abstract class RecipeProviderBase implements DataProvider {

    private final PackOutput packOutput;

    protected RecipeProviderBase(@Nonnull final PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    @Override
    @Nonnull
    public CompletableFuture<?> run(@Nonnull CachedOutput cache) throws IllegalStateException {
        final PackOutput.PathProvider pathProvider = this.packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "recipes");
        final PackOutput.PathProvider advancementPathProvider = this.packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "advancements");
        Set<ResourceLocation> resourceLocationSet = Sets.newHashSet();
        List<CompletableFuture<?>> recipeList = new ArrayList<>();

        this.registerRecipes((recipe) -> {
            if (!resourceLocationSet.add(recipe.getId())) {
                throw new IllegalStateException("Duplicate recipe " + recipe.getId());
            }
            else {
                recipeList.add(DataProvider.saveStable(cache,
                        recipe.serializeRecipe(),
                        pathProvider.json(recipe.getId())));
                JsonObject advancement = recipe.serializeAdvancement();

                if (advancement != null) {
                    CompletableFuture<?> recipeAdvancement = saveAdvancement(cache, recipe, advancement, advancementPathProvider);

                    if (recipeAdvancement != null) {
                        recipeList.add(recipeAdvancement);
                    }
                }
            }
        });

        return CompletableFuture.allOf(recipeList.toArray(CompletableFuture[]::new));
    }

    @Nullable
    protected CompletableFuture<?> saveAdvancement(CachedOutput cache, FinishedRecipe recipe, JsonObject json, PackOutput.PathProvider path) {
        return DataProvider.saveStable(cache, json, path.json(recipe.getAdvancementId()));
    }

    protected abstract void registerRecipes(Consumer<FinishedRecipe> consumer);

    public static InventoryChangeTrigger.TriggerInstance conditionsFromItem(ItemLike item) {
        return RecipeProviderAccessor.cc_condition(ItemPredicate.Builder.item().of(item).build());
    }

    public static InventoryChangeTrigger.TriggerInstance conditionsFromTag(TagKey<Item> key) {
        return RecipeProviderAccessor.cc_condition(ItemPredicate.Builder.item().of(key).build());
    }

    protected static void specialRecipe(Consumer<FinishedRecipe> consumer, SimpleCraftingRecipeSerializer<?> serializer) {
        ResourceLocation name = BuiltInRegistries.RECIPE_SERIALIZER.getKey(serializer);

        SpecialRecipeBuilder.special(serializer).save(consumer, prefix("dynamic/" + Objects.requireNonNull(name).getPath()).toString());
    }

    protected static ShapedRecipeBuilder enchantedTotemCharm() {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ChargedCharmsItems.enchantedTotemCharm)
                .define('N', Items.IRON_NUGGET)
                .define('S', TagManager.Items.ENCHANTED_TOTEMS)
                .pattern("NNN")
                .pattern("NSN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromTag(TagManager.Items.ENCHANTED_TOTEMS));
    }

    protected static ShapedRecipeBuilder regenerationCharm() {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ChargedCharmsItems.regenerationCharm)
            .define('N', Items.IRON_NUGGET)
                .define('S', Items.APPLE)
                .pattern("NNN")
                .pattern("NSN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromItem(Items.APPLE));
    }

    protected static ShapedRecipeBuilder absorptionCharm() {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ChargedCharmsItems.absorptionCharm)
                .define('N', Items.IRON_NUGGET)
                .define('A', Items.COOKED_BEEF)
                .pattern("NNN")
                .pattern("NAN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromItem(Items.COOKED_BEEF));
    }

    protected static ShapedRecipeBuilder glowupCharm() {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ChargedCharmsItems.glowupCharm)
                .define('N', Items.GOLD_NUGGET)
                .define('G', Items.GLOW_BERRIES)
                .pattern("NNN")
                .pattern("NGN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromItem(Items.GLOW_BERRIES));
    }

    protected static ShapedRecipeBuilder totemCharm() {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ChargedCharmsItems.totemCharm)
                .define('N', Items.IRON_NUGGET)
                .define('U', Items.TOTEM_OF_UNDYING)
                .pattern("NNN")
                .pattern("NUN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromItem(Items.TOTEM_OF_UNDYING));
    }

    protected static ShapedRecipeBuilder speedCharm() {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ChargedCharmsItems.speedCharm)
                .define('N', Items.IRON_NUGGET)
                .define('S', Items.SUGAR)
                .define('B', Items.LEATHER_BOOTS)
                .pattern("NSN")
                .pattern("NBN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromItem(Items.SUGAR));
    }

}