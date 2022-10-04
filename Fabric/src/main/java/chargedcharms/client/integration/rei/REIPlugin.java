package chargedcharms.client.integration.rei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomDisplay;

import net.minecraft.world.item.crafting.CraftingRecipe;

import chargedcharms.client.integration.CharmChargingRecipeMaker;

public class REIPlugin implements REIClientPlugin {

    @Override
    public void registerDisplays(DisplayRegistry helper) {
        List<CraftingRecipe> recipes = CharmChargingRecipeMaker.createRecipes("rei");

        recipes.forEach(recipe -> {
            List<EntryIngredient> input = new ArrayList<>();

            recipe.getIngredients().forEach(ingredient -> {
                input.add(EntryIngredients.ofIngredient(ingredient));
            });

            helper.add(new DefaultCustomDisplay(null, input, Collections.singletonList(EntryIngredients.of(recipe.getResultItem()))));
        });
    }

}
