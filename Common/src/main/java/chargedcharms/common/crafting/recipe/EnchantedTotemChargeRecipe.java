package chargedcharms.common.crafting.recipe;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;

import com.mojang.datafixers.util.Pair;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.common.TagManager;

public class EnchantedTotemChargeRecipe extends ChargeRecipeBase {

    public static final RecipeSerializer<EnchantedTotemChargeRecipe> SERIALIZER = new CustomRecipe.Serializer<>(EnchantedTotemChargeRecipe::new);

    public EnchantedTotemChargeRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public @NotNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public Pair<ItemStack, ItemStack> checkContainer(CraftingInput craftingInput) {
        List<ItemStack> totems = Lists.newArrayList();
        List<ItemStack> charms = Lists.newArrayList();
        ItemStack totem = null;
        ItemStack charm = null;

        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack ingredient = craftingInput.getItem(i);

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
