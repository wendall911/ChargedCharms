package chargedcharms.common.crafting.recipe;

import java.util.List;

import org.jspecify.annotations.NonNull;

import com.google.common.collect.Lists;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import chargedcharms.common.item.ChargedCharmsItems;

public class WaterBreathingChargeRecipe extends CustomRecipe implements IChargeRecipeBase {

    public static final WaterBreathingChargeRecipe INSTANCE = new WaterBreathingChargeRecipe();
    public static final MapCodec<WaterBreathingChargeRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, WaterBreathingChargeRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<WaterBreathingChargeRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public boolean matches(CraftingInput craftingInput, @NonNull Level level) {
        return matches(craftingInput);
    }

    @Override
    public @NonNull ItemStack assemble(CraftingInput craftingInput) {
        return assemble(craftingInput, 1);
    }
    @Override
    public @NonNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public Pair<ItemStack, ItemStack> checkContainer(CraftingInput craftingInput) {
        List<ItemStack> ingredients = Lists.newArrayList();
        List<ItemStack> charms = Lists.newArrayList();
        ItemStack kelp = null;
        ItemStack charm = null;

        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack ingredient = craftingInput.getItem(i);

            if (ingredient.getItem().equals(ChargedCharmsItems.waterBreathingCharm)) {
                charms.add(ingredient);

                if (ingredient.getDamageValue() > 0) {
                    charm = ingredient;
                }
            }
            else if (ingredient.getItem().equals(Items.KELP)) {
                ingredients.add(ingredient);
                kelp = ingredient;
            }
        }

        if (charms.size() != 1 || ingredients.size() != 1) {
            kelp = null;
            charm = null;
        }

        return Pair.of(charm, kelp);
    }

}
