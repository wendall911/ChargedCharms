package chargedcharms.client.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import chargedcharms.common.TagManager;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.config.ConfigHandler;
import chargedcharms.data.integration.ModIntegration;
import chargedcharms.platform.Services;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public class CharmChargingRecipeMaker {

    public static List<CraftingRecipe> createRecipes(String plugin) {
        String group = plugin + ".charm.charging";
        List<CraftingRecipe> recipes = new ArrayList<>();
        List<ItemStack> regenFoods = Lists.newArrayList();
        List<ItemStack> absorptionFoods = Lists.newArrayList();

        Registry.ITEM.stream()
                .filter(Item::isEdible)
                .filter(item -> {
                    List<Pair<MobEffectInstance, Float>> effects = Objects.requireNonNull(item.getFoodProperties()).getEffects();
                    if (!effects.isEmpty()) {
                        return effects.stream().noneMatch(props -> props.getFirst().getEffect().equals(MobEffects.POISON));
                    }

                    return true;
                }).forEach(food -> {
                    ItemStack foodStack = new ItemStack(food);

                    if (!foodStack.is(TagManager.Items.CHARM_FOODS_BLACKLIST) && foodStack.isEdible()) {
                        if (food.getFoodProperties().getNutrition() > 4) {
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

        return recipes;
    }

    private static CraftingRecipe getRecipe(String group, String label, Item charm, Ingredient chargeItems) {
        ItemStack unchargedCharm = new ItemStack(charm);
        unchargedCharm.setDamageValue(unchargedCharm.getMaxDamage());

        Ingredient baseCharm = Ingredient.of(unchargedCharm);

        ItemStack chargedCharm = unchargedCharm.copy();
        chargedCharm.setDamageValue(chargedCharm.getMaxDamage() - 1);

        NonNullList<Ingredient> chargedCharmInputs = NonNullList.of(Ingredient.EMPTY, baseCharm, chargeItems);

        return new ShapelessRecipe(prefix(group + label), group, chargedCharm, chargedCharmInputs);
    }

}