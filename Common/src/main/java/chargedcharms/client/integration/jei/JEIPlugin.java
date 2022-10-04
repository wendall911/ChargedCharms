package chargedcharms.client.integration.jei;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import chargedcharms.ChargedCharms;
import chargedcharms.client.integration.CharmChargingRecipeMaker;
import chargedcharms.common.crafting.recipe.AbsorptionChargeRecipe;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.data.integration.ModIntegration;
import chargedcharms.platform.Services;

import static chargedcharms.util.ResourceLocationHelper.prefix;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return prefix("jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Minecraft minecraft = Minecraft.getInstance();
        RecipeManager recipeManager = Objects.requireNonNull(minecraft.level).getRecipeManager();
        List<CraftingRecipe> allCraftingRecipes = recipeManager.getAllRecipesFor(RecipeType.CRAFTING);
        List<CraftingRecipe> charmChargingRecipes = addChargingRecipes(allCraftingRecipes);

        registration.addRecipes(RecipeTypes.CRAFTING, charmChargingRecipes);
        if (!Services.PLATFORM.isModLoaded(ModIntegration.BMO_MODID)) {
            registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK,
                    Collections.singleton(new ItemStack(ChargedCharmsItems.enchantedTotemCharm)));
        }
    }

    private static List<CraftingRecipe> addChargingRecipes(List<CraftingRecipe> allCraftingRecipes) {
        Map<Class<? extends CraftingRecipe>, Supplier<List<CraftingRecipe>>> replacers = new IdentityHashMap<>();

        replacers.put(AbsorptionChargeRecipe.class, () -> CharmChargingRecipeMaker.createRecipes("jei"));

        return allCraftingRecipes.stream()
                .map(CraftingRecipe::getClass)
                .distinct()
                .filter(replacers::containsKey)
                .limit(replacers.size())
                .flatMap(recipeClass -> {
                    Supplier<List<CraftingRecipe>> supplier = replacers.get(recipeClass);

                    try {
                        List<CraftingRecipe> results = supplier.get();

                        return results.stream();
                    }
                    catch (RuntimeException e) {
                        ChargedCharms.LOGGER.error("Failed to create JEI Recipes for " + recipeClass + " " + e);

                        return Stream.of();
                    }
                })
                .toList();
    }

}