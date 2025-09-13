package chargedcharms.client.integration;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import chargedcharms.util.RegistryHelper;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public class CharmChargingRecipeMaker {

    public static List<RecipeHolder<CraftingRecipe>> createRecipes(String plugin) {
        String group = plugin + ".charm.charging";
        List<RecipeHolder<CraftingRecipe>> recipes = new ArrayList<>();
        List<ItemStack> regenFoods = Lists.newArrayList();
        List<ItemStack> absorptionFoods = Lists.newArrayList();

        RegistryHelper.getRegistry(Registries.ITEM).stream()
                .filter(item -> {
                    ItemStack stack = new ItemStack(item);

                    return stack.has(DataComponents.FOOD);
                })
                .filter(item -> {
                    ItemStack foodStack = new ItemStack(item);
                    List<FoodProperties.PossibleEffect> effects = null;
                    FoodProperties foodProperties = foodStack.get(DataComponents.FOOD);

                    if (foodProperties != null) {
                        effects = foodProperties.effects();
                    }

                    if (effects != null && !effects.isEmpty()) {
                        return effects.stream().noneMatch(props -> props.effect().equals(MobEffects.POISON));
                    }

                    return true;
                }).forEach(food -> {
                    ItemStack foodStack = new ItemStack(food);
                    FoodProperties foodProperties = foodStack.get(DataComponents.FOOD);

                    if (!foodStack.is(TagManager.Items.CHARM_FOODS_BLACKLIST) && foodProperties != null) {
                        if (foodProperties.nutrition() > 4) {
                            absorptionFoods.add(foodStack);
                        }

                        regenFoods.add(foodStack);
                    }
                });

        if (!ConfigHandler.Common.disableRegenCharm()) {
            recipes.add(getRecipe(group, ".regen", ChargedCharmsItems.regenerationCharm, Ingredient.of(regenFoods.stream())));
        }
        if (!ConfigHandler.Common.disableAbsorptionCharm()) {
            recipes.add(getRecipe(group, ".absorption", ChargedCharmsItems.absorptionCharm, Ingredient.of(absorptionFoods.stream())));
        }
        if (!ConfigHandler.Common.disableTotemCharm()) {
            recipes.add(getRecipe(group, ".totem", ChargedCharmsItems.totemCharm, Ingredient.of(Items.TOTEM_OF_UNDYING)));
        }
        if (Services.PLATFORM.isModLoaded(ModIntegration.BMO_MODID) && !ConfigHandler.Common.disableEnchTotemCharm()) {
            recipes.add(getRecipe(group, ".enchanted_totem", ChargedCharmsItems.enchantedTotemCharm, Ingredient.of(TagManager.Items.ENCHANTED_TOTEMS)));
        }
        if (!ConfigHandler.Common.disableSpeedCharm()) {
            recipes.add(getRecipe(group, ".speed", ChargedCharmsItems.speedCharm, Ingredient.of(Items.SUGAR)));
        }

        return recipes;
    }

    private static RecipeHolder<CraftingRecipe> getRecipe(String group, String label, Item charm, Ingredient chargeItems) {
        ItemStack unchargedCharm = new ItemStack(charm);
        unchargedCharm.setDamageValue(unchargedCharm.getMaxDamage());

        Ingredient baseCharm = Ingredient.of(unchargedCharm);

        ItemStack chargedCharm = unchargedCharm.copy();
        chargedCharm.setDamageValue(chargedCharm.getMaxDamage() - 1);

        NonNullList<Ingredient> chargedCharmInputs = NonNullList.of(Ingredient.EMPTY, baseCharm, chargeItems);

        return new RecipeHolder<>(prefix(group + label), new ShapelessRecipe(group, CraftingBookCategory.MISC, chargedCharm, chargedCharmInputs));
    }

}