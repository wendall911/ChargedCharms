package chargedcharms.common.crafting.recipe;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;

import com.mojang.datafixers.util.Pair;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;

import chargedcharms.common.TagManager;
import chargedcharms.common.item.ChargedCharmsItems;

public class AbsorptionChargeRecipe extends ChargeRecipeBase {

    public static final SimpleRecipeSerializer<AbsorptionChargeRecipe> SERIALIZER = new SimpleRecipeSerializer<>(AbsorptionChargeRecipe::new);

    public AbsorptionChargeRecipe(ResourceLocation loc) {
        super(loc);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public Pair<ItemStack, ItemStack> checkContainer(CraftingContainer craftingContainer) {
        List<ItemStack> foods = Lists.newArrayList();
        List<ItemStack> charms = Lists.newArrayList();
        ItemStack food = null;
        ItemStack charm = null;

        for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
            ItemStack ingredient = craftingContainer.getItem(i);

            if (ingredient.getItem().equals(ChargedCharmsItems.absorptionCharm)) {
                charms.add(ingredient);

                if (ingredient.getDamageValue() > 0) {
                    charm = ingredient;
                }
            }
            else if (!ingredient.is(TagManager.Items.REGEN_FOODS_BLACKLIST)
                    && ingredient.isEdible()
                    && Objects.requireNonNull(ingredient.getItem().getFoodProperties()).getNutrition() > 4) {
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

    public static class Type implements RecipeType<AbsorptionChargeRecipe> {

        public static final Type INSTANCE = new Type();

        private Type() {}

    }

}
