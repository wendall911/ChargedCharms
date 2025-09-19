package chargedcharms.common.crafting.recipe;

import java.util.List;

import com.google.common.collect.Lists;

import com.mojang.datafixers.util.Pair;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import chargedcharms.common.item.ChargedCharmsItems;

public class WaterBreathingChargeRecipe extends ChargeRecipeBase {

    public static final SimpleCraftingRecipeSerializer<WaterBreathingChargeRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(WaterBreathingChargeRecipe::new);

    public WaterBreathingChargeRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public Pair<ItemStack, ItemStack> checkContainer(CraftingInput craftingInput) {
        List<ItemStack> ingredients = Lists.newArrayList();
        List<ItemStack> charms = Lists.newArrayList();
        ItemStack kelp = null;
        ItemStack charm = null;

        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack ingredient = craftingInput.getItem(i);

            if (ingredient.getItem().equals(ChargedCharmsItems.waterBreathingCharm)) {
                charms.add(ingredient);

                if (ingredient.getDamageValue() > 0) {
                    charm = ingredient;
                }
            }
            else if (ingredient.getItem().equals(Items.KELP)) {
                ingredients.add(ingredient);
                kelp = ingredient;
            }
        }

        if (charms.size() != 1 || ingredients.size() != 1) {
            kelp = null;
            charm = null;
        }

        return Pair.of(charm, kelp);
    }

}
