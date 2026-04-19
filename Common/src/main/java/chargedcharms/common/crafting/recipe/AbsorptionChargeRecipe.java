package chargedcharms.common.crafting.recipe;

import java.util.List;

import org.jspecify.annotations.NonNull;

import com.google.common.collect.Lists;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import chargedcharms.common.TagManager;
import chargedcharms.common.item.ChargedCharmsItems;

public class AbsorptionChargeRecipe extends CustomRecipe implements IChargeRecipeBase {

    public static final AbsorptionChargeRecipe INSTANCE = new AbsorptionChargeRecipe();
    public static final MapCodec<AbsorptionChargeRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, AbsorptionChargeRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<AbsorptionChargeRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public boolean matches(CraftingInput craftingInput, @NonNull Level level) {
        return matches(craftingInput);
    }

    @Override
    public @NonNull ItemStack assemble(CraftingInput craftingInput) {
        return assemble(craftingInput, 1);
    }

    @Override
    public @NonNull RecipeSerializer<AbsorptionChargeRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public Pair<ItemStack, ItemStack> checkContainer(CraftingInput craftingInput) {
        List<ItemStack> foods = Lists.newArrayList();
        List<ItemStack> charms = Lists.newArrayList();
        ItemStack food = null;
        ItemStack charm = null;

        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack ingredient = craftingInput.getItem(i);
            ItemStack stack = new ItemStack(ingredient.getItem());
            FoodProperties foodProperties = stack.get(DataComponents.FOOD);

            if (ingredient.getItem().equals(ChargedCharmsItems.absorptionCharm)) {
                charms.add(ingredient);

                if (ingredient.getDamageValue() > 0) {
                    charm = ingredient;
                }
            }
            else if (!ingredient.is(TagManager.Items.CHARM_FOODS_BLACKLIST)
                    && foodProperties != null
                    && foodProperties.nutrition() > 4) {
                foods.add(ingredient);
                food = ingredient;
            }
        }

        if (charms.size() != 1 || foods.size() != 1) {
            food = null;
            charm = null;
        }

        return Pair.of(charm, food);
    }

}
