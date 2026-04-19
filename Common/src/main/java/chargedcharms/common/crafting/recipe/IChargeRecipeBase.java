package chargedcharms.common.crafting.recipe;

import com.mojang.datafixers.util.Pair;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;

public interface IChargeRecipeBase {

    default boolean matches(CraftingInput craftingInput) {
        Pair<ItemStack, ItemStack> check = checkContainer(craftingInput);
        boolean hasCharm = check.getFirst() != null;
        boolean hasChargeItem = check.getSecond() != null;

        return hasCharm && hasChargeItem;
    }

    default ItemStack assemble(CraftingInput craftingInput, int damage) {
        Pair<ItemStack, ItemStack> check = checkContainer(craftingInput);
        ItemStack charmCopy = check.getFirst().copy();

        charmCopy.setDamageValue(charmCopy.getDamageValue() - damage);

        return charmCopy;
    }

    default Pair<ItemStack, ItemStack> checkContainer(CraftingInput craftingInput) {
        return Pair.of(null, null);
    }

}
