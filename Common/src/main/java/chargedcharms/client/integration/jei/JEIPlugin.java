package chargedcharms.client.integration.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.common.Internal;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;

import technology.roughness.whitenoise.platform.Services;

import chargedcharms.ChargedCharms;
import chargedcharms.client.integration.CharmChargingRecipeMaker;
import chargedcharms.common.crafting.recipe.AbsorptionChargeRecipe;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.config.ConfigHandler;
import chargedcharms.data.integration.ModIntegration;

import static chargedcharms.util.ResourceLocationHelper.prefix;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public @NotNull Identifier getPluginUid() {
        return prefix("jei_plugin");
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        RecipeMap clientSyncedRecipes = Internal.getClientSyncedRecipes();
        if (clientSyncedRecipes.values().isEmpty()) {
            ChargedCharms.LOGGER.error("JEI Recipe Registration failed: No synced recipes");

            return;
        }

        Recipes recipes = new Recipes(clientSyncedRecipes);
        List<RecipeHolder<CraftingRecipe>> allCraftingRecipes = recipes.getCraftingRecipes();
        List<RecipeHolder<CraftingRecipe>> charmChargingRecipes = addChargingRecipes(allCraftingRecipes);

        registration.addRecipes(RecipeTypes.CRAFTING, charmChargingRecipes);
        if (!Services.PLATFORM.isModLoaded(ModIntegration.BMO_MODID) || ConfigHandler.Common.disableEnchTotemCharm()) {
            registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK,
                    Collections.singleton(new ItemStack(ChargedCharmsItems.enchantedTotemCharm)));
        }
        if (ConfigHandler.Common.disableRegenCharm()) {
            registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK,
                    Collections.singleton(new ItemStack(ChargedCharmsItems.regenerationCharm)));
        }
        if (ConfigHandler.Common.disableAbsorptionCharm()) {
            registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK,
                    Collections.singleton(new ItemStack(ChargedCharmsItems.absorptionCharm)));
        }
        if (ConfigHandler.Common.disableGlowupCharm()) {
            registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK,
                    Collections.singleton(new ItemStack(ChargedCharmsItems.glowupCharm)));
        }
        if (ConfigHandler.Common.disableTotemCharm()) {
            registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK,
                    Collections.singleton(new ItemStack(ChargedCharmsItems.totemCharm)));
        }
        if (ConfigHandler.Common.disableSpeedCharm()) {
            registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK,
                    Collections.singleton(new ItemStack(ChargedCharmsItems.speedCharm)));
        }
        if (ConfigHandler.Common.disableWaterBreathingCharm()) {
            registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK,
                    Collections.singleton(new ItemStack(ChargedCharmsItems.waterBreathingCharm)));
        }
    }

    private static List<RecipeHolder<CraftingRecipe>> addChargingRecipes(List<RecipeHolder<CraftingRecipe>> allCraftingRecipes) {
        Map<Class<? extends CraftingRecipe>, Supplier<List<RecipeHolder<CraftingRecipe>>>> replacers = new IdentityHashMap<>();
        List<RecipeHolder<CraftingRecipe>> recipes = new ArrayList<>();

        CharmChargingRecipeMaker.createRecipes("jei").forEach(pair -> recipes.add(pair.getSecond()));

        replacers.put(AbsorptionChargeRecipe.class, () -> recipes);

        return allCraftingRecipes.stream()
                .map(RecipeHolder::value)
                .map(CraftingRecipe::getClass)
                .distinct()
                .filter(replacers::containsKey)
                .limit(replacers.size())
                .flatMap(recipeClass -> {
                    Supplier<List<RecipeHolder<CraftingRecipe>>> supplier = replacers.get(recipeClass);

                    try {
                        List<RecipeHolder<CraftingRecipe>> results = supplier.get();

                        return results.stream();
                    }
                    catch (RuntimeException e) {
                        ChargedCharms.LOGGER.error("Failed to create JEI Recipes for {} {}", recipeClass, e);

                        return Stream.of();
                    }
                })
                .toList();
    }

}
