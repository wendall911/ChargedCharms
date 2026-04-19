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

public class TotemChargeRecipe extends CustomRecipe implements IChargeRecipeBase {

    public static final TotemChargeRecipe INSTANCE = new TotemChargeRecipe();
    public static final MapCodec<TotemChargeRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, TotemChargeRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<TotemChargeRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

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
        List<ItemStack> totems = Lists.newArrayList();
        List<ItemStack> charms = Lists.newArrayList();
        ItemStack totem = null;
        ItemStack charm = null;

        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack ingredient = craftingInput.getItem(i);

            if (ingredient.getItem().equals(ChargedCharmsItems.totemCharm)) {
                charms.add(ingredient);

                if (ingredient.getDamageValue() > 0) {
                    charm = ingredient;
                }
            }
            else if (ingredient.getItem().equals(Items.TOTEM_OF_UNDYING)) {
                totems.add(ingredient);
                totem = ingredient;
            }
        }

        if (charms.size() != 1 || totems.size() != 1) {
            totem = null;
            charm = null;
        }

        return Pair.of(charm, totem);
    }

}
