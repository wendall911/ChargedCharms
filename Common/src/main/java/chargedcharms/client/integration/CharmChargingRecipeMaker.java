package chargedcharms.client.integration;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import technology.roughness.whitenoise.platform.Services;

import chargedcharms.common.TagManager;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.config.ConfigHandler;
import chargedcharms.data.integration.ModIntegration;
import chargedcharms.util.IngredientHelper;
import chargedcharms.util.RegistryHelper;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public class CharmChargingRecipeMaker {

    public static List<Pair<ItemStack, RecipeHolder<CraftingRecipe>>> createRecipes(String plugin) {
        String group = plugin + ".charm.charging";
        List<Pair<ItemStack, RecipeHolder<CraftingRecipe>>> recipes = new ArrayList<>();
        List<Item> regenFoods = Lists.newArrayList();
        List<Item> absorptionFoods = Lists.newArrayList();

        RegistryHelper.getRegistry(Registries.ITEM).stream()
                .filter(item -> {
                    ItemStack stack = new ItemStack(item);

                    return stack.has(DataComponents.FOOD);
                }).forEach(food -> {
                    ItemStack foodStack = new ItemStack(food);
                    FoodProperties foodProperties = foodStack.get(DataComponents.FOOD);

                    if (!foodStack.is(TagManager.Items.CHARM_FOODS_BLACKLIST) && foodProperties != null) {
                        if (foodProperties.nutrition() > 4) {
                            absorptionFoods.add(foodStack.getItem());
                        }

                        regenFoods.add(foodStack.getItem());
                    }
                });

        if (!ConfigHandler.Common.disableRegenCharm()) {
            recipes.add(Pair.of(
                new ItemStack(ChargedCharmsItems.regenerationCharm),
                getRecipe(group, ".regen", ChargedCharmsItems.regenerationCharm, Ingredient.of(regenFoods.stream()))
            ));
        }
        if (!ConfigHandler.Common.disableAbsorptionCharm()) {
            recipes.add(Pair.of(
                new ItemStack(ChargedCharmsItems.absorptionCharm),
                getRecipe(group, ".absorption", ChargedCharmsItems.absorptionCharm, Ingredient.of(absorptionFoods.stream()))
            ));
        }
        if (!ConfigHandler.Common.disableTotemCharm()) {
            recipes.add(Pair.of(
                new ItemStack(ChargedCharmsItems.totemCharm),
                getRecipe(group, ".totem", ChargedCharmsItems.totemCharm, Ingredient.of(Items.TOTEM_OF_UNDYING))
            ));
        }
        if (Services.WN_PLATFORM.isModLoaded(ModIntegration.BMO_MODID) && !ConfigHandler.Common.disableEnchTotemCharm()) {
            recipes.add(Pair.of(
                new ItemStack(ChargedCharmsItems.enchantedTotemCharm),
                getRecipe(group, ".enchanted_totem", ChargedCharmsItems.enchantedTotemCharm, IngredientHelper.fromTag(TagManager.Items.ENCHANTED_TOTEMS))
            ));
        }
        if (!ConfigHandler.Common.disableSpeedCharm()) {
            recipes.add(Pair.of(
                new ItemStack(ChargedCharmsItems.speedCharm),
                getRecipe(group, ".speed", ChargedCharmsItems.speedCharm, Ingredient.of(Items.SUGAR))
            ));
        }
        if (!ConfigHandler.Common.disableWaterBreathingCharm()) {
            recipes.add(Pair.of(
                new ItemStack(ChargedCharmsItems.waterBreathingCharm),
                getRecipe(group, ".water_breathing", ChargedCharmsItems.waterBreathingCharm, Ingredient.of(Items.KELP))
            ));
        }

        return recipes;
    }

    private static RecipeHolder<CraftingRecipe> getRecipe(String group, String label, Item charm, Ingredient chargeItems) {
        ItemStack unchargedCharm = new ItemStack(charm);
        unchargedCharm.setDamageValue(unchargedCharm.getMaxDamage());

        Ingredient baseCharm = Ingredient.of(unchargedCharm.getItem());

        ItemStack chargedCharm = unchargedCharm.copy();
        chargedCharm.setDamageValue(chargedCharm.getMaxDamage() - 1);

        NonNullList<Ingredient> chargedCharmInputs = NonNullList.of(null, baseCharm, chargeItems);

        return new RecipeHolder<>(
            ResourceKey.create(Registries.RECIPE, prefix(group + label)),
            new ShapelessRecipe(
                RecipeBuilder.createCraftingCommonInfo(true),
                RecipeBuilder.createCraftingBookInfo(RecipeCategory.MISC, group),
                new ItemStackTemplate(chargedCharm.getItem(), 1),
                chargedCharmInputs
            )
        );
    }

}
