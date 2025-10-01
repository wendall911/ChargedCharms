package chargedcharms.data.recipe;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import chargedcharms.common.TagManager;
import chargedcharms.common.item.ChargedCharmsItems;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public class RecipeProviderBase {

    private static Criterion<InventoryChangeTrigger.TriggerInstance> has(HolderLookup.RegistryLookup<Item> itemRegistry, TagKey<Item> pTag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(itemRegistry, pTag).build());
    }

    private static Criterion<InventoryChangeTrigger.TriggerInstance> has(HolderLookup.RegistryLookup<Item> itemRegistry, ItemLike pItemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(itemRegistry, pItemLike).build());
    }

    private static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate... predicates) {
        return CriteriaTriggers.INVENTORY_CHANGED.createCriterion(
            new InventoryChangeTrigger.TriggerInstance(
                Optional.empty(),
                InventoryChangeTrigger.TriggerInstance.Slots.ANY,
                List.of(predicates)
            )
        );
    }

    public static void specialRecipe(RecipeOutput exporter, CustomRecipe.Serializer<?> serializer, Function<CraftingBookCategory, Recipe<?>> recipeFunction) {
        ResourceLocation name = BuiltInRegistries.RECIPE_SERIALIZER.getKey(serializer);

        SpecialRecipeBuilder.special(recipeFunction).save(
            exporter, prefix("dynamic/" + Objects.requireNonNull(name).getPath()).toString());
    }

    protected static ShapedRecipeBuilder enchantedTotemCharm(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.MISC, ChargedCharmsItems.enchantedTotemCharm)
            .define('N', Items.IRON_NUGGET)
            .define('S', TagManager.Items.ENCHANTED_TOTEMS)
            .pattern("NNN")
            .pattern("NSN")
            .pattern("NNN")
            .unlockedBy("has_item", has(itemRegistry, TagManager.Items.ENCHANTED_TOTEMS));
    }

    protected static ShapedRecipeBuilder regenerationCharm(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.MISC, ChargedCharmsItems.regenerationCharm)
            .define('N', Items.IRON_NUGGET)
            .define('S', Items.APPLE)
            .pattern("NNN")
            .pattern("NSN")
            .pattern("NNN")
            .unlockedBy("has_item", has(itemRegistry, Items.APPLE));
    }

    protected static ShapedRecipeBuilder absorptionCharm(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.MISC, ChargedCharmsItems.absorptionCharm)
            .define('N', Items.IRON_NUGGET)
            .define('A', Items.COOKED_BEEF)
            .pattern("NNN")
            .pattern("NAN")
            .pattern("NNN")
            .unlockedBy("has_item", has(itemRegistry, Items.COOKED_BEEF));
    }

    protected static ShapedRecipeBuilder glowupCharm(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.MISC, ChargedCharmsItems.glowupCharm)
            .define('N', Items.GOLD_NUGGET)
            .define('G', Items.GLOW_BERRIES)
            .pattern("NNN")
            .pattern("NGN")
            .pattern("NNN")
            .unlockedBy("has_item", has(itemRegistry, Items.GLOW_BERRIES));
    }

    protected static ShapedRecipeBuilder totemCharm(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.MISC, ChargedCharmsItems.totemCharm)
            .define('N', Items.IRON_NUGGET)
            .define('U', Items.TOTEM_OF_UNDYING)
            .pattern("NNN")
            .pattern("NUN")
            .pattern("NNN")
            .unlockedBy("has_item", has(itemRegistry, Items.TOTEM_OF_UNDYING));
    }

    protected static ShapedRecipeBuilder speedCharm(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.MISC, ChargedCharmsItems.speedCharm)
            .define('N', Items.IRON_NUGGET)
            .define('S', Items.SUGAR)
            .define('B', Items.LEATHER_BOOTS)
            .pattern("NSN")
            .pattern("NBN")
            .pattern("NNN")
            .unlockedBy("has_item", has(itemRegistry, Items.SUGAR));
    }

    protected static ShapedRecipeBuilder waterBreathingCharm(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.MISC, ChargedCharmsItems.waterBreathingCharm)
            .define('N', Items.IRON_NUGGET)
            .define('K', Items.KELP)
            .pattern("NNN")
            .pattern("NKN")
            .pattern("NNN")
            .unlockedBy("has_item", has(itemRegistry, Items.KELP));
    }

}
