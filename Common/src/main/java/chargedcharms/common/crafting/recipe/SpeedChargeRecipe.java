package chargedcharms.common.crafting.recipe;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;

import com.mojang.datafixers.util.Pair;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import chargedcharms.common.item.ChargedCharmsItems;

public class SpeedChargeRecipe extends ChargeRecipeBase {

    public static final RecipeSerializer<SpeedChargeRecipe> SERIALIZER = new CustomRecipe.Serializer<>(SpeedChargeRecipe::new);

    public SpeedChargeRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public @NotNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public Pair<ItemStack, ItemStack> checkContainer(CraftingInput craftingInput) {
        List<ItemStack> ingredients = Lists.newArrayList();
        List<ItemStack> charms = Lists.newArrayList();
        ItemStack sugar = null;
        ItemStack charm = null;

        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack ingredient = craftingInput.getItem(i);

            if (ingredient.getItem().equals(ChargedCharmsItems.speedCharm)) {
                charms.add(ingredient);

                if (ingredient.getDamageValue() > 0) {
                    charm = ingredient;
                }
            }
            else if (ingredient.getItem().equals(Items.SUGAR)) {
                ingredients.add(ingredient);
                sugar = ingredient;
            }
        }

        if (charms.size() != 1 || ingredients.size() != 1) {
            sugar = null;
            charm = null;
        }

        return Pair.of(charm, sugar);
    }

}