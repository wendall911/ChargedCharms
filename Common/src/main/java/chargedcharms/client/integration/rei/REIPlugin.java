package chargedcharms.client.integration.rei;

import java.util.ArrayList;
import java.util.List;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.ItemStack;

import chargedcharms.client.integration.CharmChargingRecipeMaker;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.config.ConfigHandler;
import chargedcharms.data.integration.ModIntegration;
import chargedcharms.platform.Services;

public class REIPlugin implements REIClientPlugin {

    @Override
    public void registerDisplays(DisplayRegistry helper) {
        List<RecipeHolder<CraftingRecipe>> recipes = CharmChargingRecipeMaker.createRecipes("rei");
        RegistryAccess registryAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);

        recipes.forEach(recipe -> {
            List<EntryIngredient> input = new ArrayList<>();

            Services.CLIENT_PLATFORM.addCustomDisplay(helper, input, recipe, registryAccess);
        });
    }

    @Override
    public void registerEntries(EntryRegistry registry) {
        registry.removeEntryIf(this::shouldHideEntry);
    }

    private boolean shouldHideEntry(EntryStack<?> entryStack) {
        if (!Services.CLIENT_PLATFORM.isVanillaItemType(entryStack)) return false;

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
        if (ConfigHandler.Common.disableSpeedCharm()) {
            return stack.getItem() == ChargedCharmsItems.speedCharm;
        }

        return false;
    }

}
