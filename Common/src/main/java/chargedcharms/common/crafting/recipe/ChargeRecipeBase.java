package chargedcharms.common.crafting.recipe;

import org.jetbrains.annotations.NotNull;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class ChargeRecipeBase extends CustomRecipe {

    public ChargeRecipeBase(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public @NotNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return null;
    }

    @Override
    public boolean matches(CraftingInput craftingInput, Level level) {
        Pair<ItemStack, ItemStack> check = checkContainer(craftingInput);
        boolean hasCharm = check.getFirst() != null;
        boolean hasChargeItem = check.getSecond() != null;

        return hasCharm && hasChargeItem;
    }

    @Override
    public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        Pair<ItemStack, ItemStack> check = checkContainer(craftingInput);
        ItemStack charmCopy = check.getFirst().copy();

        charmCopy.setDamageValue(charmCopy.getDamageValue() - 1);

        return charmCopy;
    }

    public Pair<ItemStack, ItemStack> checkContainer(CraftingInput craftingInput) {
        return Pair.of(null, null);
    }

}
