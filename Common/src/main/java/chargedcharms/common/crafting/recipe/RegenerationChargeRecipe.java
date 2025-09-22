package chargedcharms.common.crafting.recipe;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import chargedcharms.common.TagManager;
import chargedcharms.common.item.ChargedCharmsItems;

public class RegenerationChargeRecipe extends ChargeRecipeBase {

    public static final RecipeSerializer<RegenerationChargeRecipe> SERIALIZER = new CustomRecipe.Serializer<>(RegenerationChargeRecipe::new);

    public RegenerationChargeRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public @NotNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public Pair<ItemStack, ItemStack> checkContainer(CraftingInput craftingInput) {
        List<ItemStack> foods = Lists.newArrayList();
        List<ItemStack> charms = Lists.newArrayList();
        ItemStack food = null;
        ItemStack charm = null;

        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack ingredient = craftingInput.getItem(i);

            if (ingredient.getItem().equals(ChargedCharmsItems.regenerationCharm)) {
                charms.add(ingredient);

                if (ingredient.getDamageValue() > 0) {
                    charm = ingredient;
                }
            }
            else if (!ingredient.is(TagManager.Items.CHARM_FOODS_BLACKLIST)) {
                foods.add(ingredient);
                food = ingredient;
            }
        }

        if (charms.size() != 1 || foods.size() != 1) {
            food = null;
            charm = null;
        }

        return Pair.of(charm, food);
    }

}
