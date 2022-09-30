package chargedcharms.common.crafting.recipe;

import com.mojang.datafixers.util.Pair;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class ChargeRecipeBase extends CustomRecipe {

    public ChargeRecipeBase(ResourceLocation loc) {
        super(loc);
    }

    @Override
    public boolean matches(CraftingContainer craftingContainer, Level level) {
        Pair<ItemStack, ItemStack> check = checkContainer(craftingContainer);
        boolean hasCharm = check.getFirst() != null;
        boolean hasChargeItem = check.getSecond() != null;

        return hasCharm && hasChargeItem;
    }

    @Override
    public ItemStack assemble(CraftingContainer craftingContainer) {
        Pair<ItemStack, ItemStack> check = checkContainer(craftingContainer);
        ItemStack charmCopy = check.getFirst().copy();

        charmCopy.setDamageValue(charmCopy.getDamageValue() - 1);

        return charmCopy;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    public Pair<ItemStack, ItemStack> checkContainer(CraftingContainer craftingContainer) {
        return Pair.of(null, null);
    }

}
