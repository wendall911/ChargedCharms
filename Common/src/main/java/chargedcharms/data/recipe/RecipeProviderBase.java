package chargedcharms.data.recipe;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.ItemLike;

import chargedcharms.common.TagManager;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.mixin.RecipeProviderAccessor;
import chargedcharms.platform.Services;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public abstract class RecipeProviderBase implements DataProvider {

    private final DataGenerator generator;

    protected RecipeProviderBase(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(@Nonnull CachedOutput cache) throws IllegalStateException {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> recipes = Sets.newHashSet();

        registerRecipes((provider) -> {
            if (recipes.add(provider.getId())) {
                JsonObject advancement = provider.serializeAdvancement();

                RecipeProviderAccessor.callSaveRecipe(cache, provider.serializeRecipe(), path.resolve(
                        "data/"
                        + provider.getId().getNamespace()
                        + "/recipes/"
                        + provider.getId().getPath()
                        + ".json"
                ));

                if (advancement != null) {
                    Services.PLATFORM.saveRecipeAdvancement(this.generator, cache, advancement, path.resolve(
                        "data/"
                        + provider.getId().getNamespace()
                        + "/advancements/"
                        + provider.getAdvancementId().getPath()
                        + ".json"
                    ));
                }
            }
            else {
                throw new IllegalStateException("Duplicate recipe " + provider.getId());
            }
        });
    }

    protected abstract void registerRecipes(Consumer<FinishedRecipe> consumer);

    public static InventoryChangeTrigger.TriggerInstance conditionsFromItem(ItemLike item) {
        return RecipeProviderAccessor.cc_condition(ItemPredicate.Builder.item().of(item).build());
    }

    public static InventoryChangeTrigger.TriggerInstance conditionsFromTag(TagKey<Item> key) {
        return RecipeProviderAccessor.cc_condition(ItemPredicate.Builder.item().of(key).build());
    }

    protected static void specialRecipe(Consumer<FinishedRecipe> consumer, SimpleRecipeSerializer<?> serializer) {
        ResourceLocation name = Registry.RECIPE_SERIALIZER.getKey(serializer);

        SpecialRecipeBuilder.special(serializer).save(consumer, prefix("dynamic/" + Objects.requireNonNull(name).getPath()).toString());
    }

    protected static ShapedRecipeBuilder enchantedTotemCharm() {
        return ShapedRecipeBuilder.shaped(ChargedCharmsItems.enchantedTotemCharm)
                .define('N', Items.IRON_NUGGET)
                .define('S', TagManager.Items.ENCHANTED_TOTEMS)
                .pattern("NNN")
                .pattern("NSN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromTag(TagManager.Items.ENCHANTED_TOTEMS));
    }

    protected static ShapedRecipeBuilder regenerationCharm() {
        return ShapedRecipeBuilder.shaped(ChargedCharmsItems.regenerationCharm)
            .define('N', Items.IRON_NUGGET)
                .define('S', Items.APPLE)
                .pattern("NNN")
                .pattern("NSN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromItem(Items.APPLE));
    }

    protected static ShapedRecipeBuilder absorptionCharm() {
        return ShapedRecipeBuilder.shaped(ChargedCharmsItems.absorptionCharm)
                .define('N', Items.IRON_NUGGET)
                .define('A', Items.COOKED_BEEF)
                .pattern("NNN")
                .pattern("NAN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromItem(Items.COOKED_BEEF));
    }

    protected static ShapedRecipeBuilder glowupCharm() {
        return ShapedRecipeBuilder.shaped(ChargedCharmsItems.glowupCharm)
                .define('N', Items.GOLD_NUGGET)
                .define('G', Items.GLOW_BERRIES)
                .pattern("NNN")
                .pattern("NGN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromItem(Items.GLOW_BERRIES));
    }

    protected static ShapedRecipeBuilder totemCharm() {
        return ShapedRecipeBuilder.shaped(ChargedCharmsItems.totemCharm)
                .define('N', Items.IRON_NUGGET)
                .define('U', Items.TOTEM_OF_UNDYING)
                .pattern("NNN")
                .pattern("NUN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromItem(Items.TOTEM_OF_UNDYING));
    }

    protected static ShapedRecipeBuilder speedCharm() {
        return ShapedRecipeBuilder.shaped(ChargedCharmsItems.speedCharm)
                .define('N', Items.IRON_NUGGET)
                .define('S', Items.SUGAR)
                .define('B', Items.LEATHER_BOOTS)
                .pattern("NSN")
                .pattern("NBN")
                .pattern("NNN")
                .unlockedBy("has_item", conditionsFromItem(Items.SUGAR));
    }

}