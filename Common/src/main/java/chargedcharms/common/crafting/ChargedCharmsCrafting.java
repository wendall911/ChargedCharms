package chargedcharms.common.crafting;

import java.util.function.BiConsumer;

import chargedcharms.common.crafting.recipe.AbsorptionChargeRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

import chargedcharms.common.crafting.recipe.EnchantedTotemChargeRecipe;
import chargedcharms.common.crafting.recipe.RegenerationChargeRecipe;
import chargedcharms.common.crafting.recipe.TotemChargeRecipe;
import static chargedcharms.util.ResourceLocationHelper.prefix;

public class ChargedCharmsCrafting {

    public static void registerRecipeSerializers(BiConsumer<RecipeSerializer<?>, ResourceLocation> consumer) {
        consumer.accept(RegenerationChargeRecipe.SERIALIZER, prefix("recharge_regeneration_charm"));
        consumer.accept(TotemChargeRecipe.SERIALIZER, prefix("charge_totem_charm"));
        consumer.accept(EnchantedTotemChargeRecipe.SERIALIZER, prefix("charge_enchanted_totem_charm"));
        consumer.accept(AbsorptionChargeRecipe.SERIALIZER, prefix("recharge_absorption_charm"));
    }

}
