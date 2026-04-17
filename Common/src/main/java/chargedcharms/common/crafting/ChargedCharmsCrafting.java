package chargedcharms.common.crafting;

import java.util.function.BiConsumer;

import chargedcharms.common.crafting.recipe.WaterBreathingChargeRecipe;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;

import chargedcharms.common.crafting.recipe.AbsorptionChargeRecipe;
import chargedcharms.common.crafting.recipe.EnchantedTotemChargeRecipe;
import chargedcharms.common.crafting.recipe.RegenerationChargeRecipe;
import chargedcharms.common.crafting.recipe.SpeedChargeRecipe;
import chargedcharms.common.crafting.recipe.TotemChargeRecipe;
import static chargedcharms.util.ResourceLocationHelper.prefix;

public class ChargedCharmsCrafting {

    public static void registerRecipeSerializers(BiConsumer<RecipeSerializer<?>, Identifier> consumer) {
        consumer.accept(RegenerationChargeRecipe.SERIALIZER, prefix("recharge_regeneration_charm"));
        consumer.accept(TotemChargeRecipe.SERIALIZER, prefix("charge_totem_charm"));
        consumer.accept(EnchantedTotemChargeRecipe.SERIALIZER, prefix("charge_enchanted_totem_charm"));
        consumer.accept(AbsorptionChargeRecipe.SERIALIZER, prefix("recharge_absorption_charm"));
        consumer.accept(SpeedChargeRecipe.SERIALIZER, prefix("charge_speed_charm"));
        consumer.accept(WaterBreathingChargeRecipe.SERIALIZER, prefix("charge_water_breathing_charm"));
    }

}
