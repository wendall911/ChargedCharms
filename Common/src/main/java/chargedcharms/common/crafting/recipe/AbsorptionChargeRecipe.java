package chargedcharms.common.crafting.recipe;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;

import com.mojang.datafixers.util.Pair;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import chargedcharms.common.TagManager;
import chargedcharms.common.item.ChargedCharmsItems;

public class AbsorptionChargeRecipe extends ChargeRecipeBase {

    public static final SimpleCraftingRecipeSerializer<AbsorptionChargeRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(AbsorptionChargeRecipe::new);

    public AbsorptionChargeRecipe(ResourceLocation loc, CraftingBookCategory category) {
        super(loc, category);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public Pair<ItemStack, ItemStack> checkContainer(CraftingContainer craftingContainer) {
        List<ItemStack> foods = Lists.newArrayList();
        List<ItemStack> charms = Lists.newArrayList();
        ItemStack food = null;
        ItemStack charm = null;

        for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
            ItemStack ingredient = craftingContainer.getItem(i);

            if (ingredient.getItem().equals(ChargedCharmsItems.absorptionCharm)) {
                charms.add(ingredient);

                if (ingredient.getDamageValue() > 0) {
                    charm = ingredient;
                }
            }
            else if (!ingredient.is(TagManager.Items.CHARM_FOODS_BLACKLIST)
                    && ingredient.isEdible()
                    && Objects.requireNonNull(ingredient.getItem().getFoodProperties()).getNutrition() > 4) {
                List<Pair<MobEffectInstance, Float>> effects = ingredient.getItem().getFoodProperties().getEffects();

                if (effects.isEmpty() || effects.stream().noneMatch(props -> props.getFirst().getEffect().equals(MobEffects.POISON))) {
                    foods.add(ingredient);
                    food = ingredient;
                }
            }
        }

        if (charms.size() != 1 || foods.size() != 1) {
            food = null;
            charm = null;
        }

        return Pair.of(charm, food);
    }

}
