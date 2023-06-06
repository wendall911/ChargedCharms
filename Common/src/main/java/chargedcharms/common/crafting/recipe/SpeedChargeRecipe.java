package chargedcharms.common.crafting.recipe;

import java.util.List;

import com.google.common.collect.Lists;

import com.mojang.datafixers.util.Pair;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import chargedcharms.common.item.ChargedCharmsItems;

public class SpeedChargeRecipe extends ChargeRecipeBase {

    public static final SimpleCraftingRecipeSerializer<SpeedChargeRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(SpeedChargeRecipe::new);

    public SpeedChargeRecipe(ResourceLocation loc, CraftingBookCategory category) {
        super(loc, category);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public Pair<ItemStack, ItemStack> checkContainer(CraftingContainer craftingContainer) {
        List<ItemStack> ingredients = Lists.newArrayList();
        List<ItemStack> charms = Lists.newArrayList();
        ItemStack sugar = null;
        ItemStack charm = null;

        for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
            ItemStack ingredient = craftingContainer.getItem(i);

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