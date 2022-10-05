package chargedcharms.client.integration.rei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomDisplay;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;

import chargedcharms.client.integration.CharmChargingRecipeMaker;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.config.ConfigHandler;
import chargedcharms.data.integration.ModIntegration;
import chargedcharms.platform.Services;

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

    @Override
    public void registerEntries(EntryRegistry registry) {
        registry.removeEntryIf(this::shouldHideEntry);
    }

    private boolean shouldHideEntry(EntryStack<?> entryStack) {
        if (entryStack.getType() != VanillaEntryTypes.ITEM) return false;

        ItemStack stack = entryStack.castValue();

        if (!Services.PLATFORM.isModLoaded(ModIntegration.BMO_MODID) || ConfigHandler.Common.disableEnchTotemCharm()) {
            return stack.getItem() == ChargedCharmsItems.enchantedTotemCharm;
        }

        if (ConfigHandler.Common.disableRegenCharm()) {
            return stack.getItem() == ChargedCharmsItems.regenerationCharm;
        }

        if (ConfigHandler.Common.disableAbsorptionCharm()) {
            return stack.getItem() == ChargedCharmsItems.absorptionCharm;
        }

        if (ConfigHandler.Common.disableGlowupCharm()) {
            return stack.getItem() == ChargedCharmsItems.glowupCharm;
        }

        if (ConfigHandler.Common.disableTotemCharm()) {
            return stack.getItem() == ChargedCharmsItems.totemCharm;
        }

        return false;
    }

}
