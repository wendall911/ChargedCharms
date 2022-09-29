package chargedcharms.common.crafting.recipe;

import java.util.List;

import com.google.common.collect.Lists;

import com.mojang.datafixers.util.Pair;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.Level;

import chargedcharms.common.TagManager;
import chargedcharms.common.item.ChargedCharmsItems;

public class RegenerationChargeRecipe extends CustomRecipe {

    public static final SimpleRecipeSerializer<RegenerationChargeRecipe> SERIALIZER = new SimpleRecipeSerializer<>(RegenerationChargeRecipe::new);

    public RegenerationChargeRecipe(ResourceLocation loc) {
        super(loc);
    }

    @Override
    public boolean matches(CraftingContainer craftingContainer, Level level) {
        Pair<ItemStack, ItemStack> check = checkContainer(craftingContainer);
        boolean hasCharm = check.getFirst() != null;
        boolean hasFood = check.getSecond() != null;

        return hasCharm && hasFood;
    }

    @Override
    public ItemStack assemble(CraftingContainer craftingContainer) {
        Pair<ItemStack, ItemStack> check = checkContainer(craftingContainer);
        ItemStack charmCopy = check.getFirst().copy();

        charmCopy.setDamageValue(charmCopy.getDamageValue() - 1);

        return charmCopy;
    }

    @Override
    public ItemStack getResultItem() {
        ItemStack stack = new ItemStack(ChargedCharmsItems.regenerationCharm);

        stack.setDamageValue(stack.getMaxDamage() - 1);

        return stack;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public Pair<ItemStack, ItemStack> checkContainer(CraftingContainer craftingContainer) {
        List<ItemStack> foods = Lists.newArrayList();
        List<ItemStack> charms = Lists.newArrayList();
        ItemStack food = null;
        ItemStack charm = null;

        for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
            ItemStack ingredient = craftingContainer.getItem(i);

            if (ingredient.getItem().equals(ChargedCharmsItems.regenerationCharm)) {
                charms.add(ingredient);

                if (ingredient.getDamageValue() > 0) {
                    charm = ingredient;
                }
            }
            else if (!ingredient.is(TagManager.Items.REGEN_FOODS_BLACKLIST) && ingredient.isEdible()) {
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

    public static class Type implements RecipeType<RegenerationChargeRecipe> {

        public static final Type INSTANCE = new Type();

        private Type() {}

    }

}
