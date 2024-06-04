package chargedcharms.platform.services;

import java.util.List;

import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;

import me.shedaniel.rei.api.common.entry.EntryStack;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public interface IREIHelper {

    void addCustomDisplay(DisplayRegistry helper, List<EntryIngredient> input, RecipeHolder<CraftingRecipe> recipe, RegistryAccess registryAccess);

    boolean isVanillaItemType(EntryStack<?> entryStack);

}
