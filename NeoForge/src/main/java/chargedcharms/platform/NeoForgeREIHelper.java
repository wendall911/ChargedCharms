package chargedcharms.platform;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomDisplay;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import chargedcharms.platform.services.IREIHelper;

public class NeoForgeREIHelper implements IREIHelper {

    @Override
    public void addCustomDisplay(DisplayRegistry helper, List<EntryIngredient> input, RecipeHolder<CraftingRecipe> recipe, ItemStack result) {
        recipe.value().placementInfo().ingredients().forEach(ingredient -> {
            input.add(EntryIngredients.ofIngredient(ingredient));
        });

        helper.add(new DefaultCustomDisplay(input, Collections.singletonList(EntryIngredients.of(result)), Optional.of(recipe.id().location())));
    }

    @Override
    public boolean isVanillaItemType(EntryStack<?> entryStack) {
        return entryStack.getType() == VanillaEntryTypes.ITEM;
    }

}
