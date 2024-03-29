package chargedcharms.common.crafting.recipe;

import java.util.List;

import com.google.common.collect.Lists;

import com.mojang.datafixers.util.Pair;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.common.TagManager;

public class EnchantedTotemChargeRecipe extends ChargeRecipeBase {

    public static final SimpleCraftingRecipeSerializer<EnchantedTotemChargeRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(EnchantedTotemChargeRecipe::new);

    public EnchantedTotemChargeRecipe(ResourceLocation loc, CraftingBookCategory category) {
        super(loc, category);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public Pair<ItemStack, ItemStack> checkContainer(CraftingContainer craftingContainer) {
        List<ItemStack> totems = Lists.newArrayList();
        List<ItemStack> charms = Lists.newArrayList();
        ItemStack totem = null;
        ItemStack charm = null;

        for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
            ItemStack ingredient = craftingContainer.getItem(i);

            if (ingredient.getItem().equals(ChargedCharmsItems.enchantedTotemCharm)) {
                charms.add(ingredient);

                if (ingredient.getDamageValue() > 0) {
                    charm = ingredient;
                }
            }
            else if (ingredient.is(TagManager.Items.ENCHANTED_TOTEMS)) {
                totems.add(ingredient);
                totem = ingredient;
            }
        }

        if (charms.size() != 1 || totems.size() != 1) {
            totem = null;
            charm = null;
        }

        return Pair.of(charm, totem);
    }

}
