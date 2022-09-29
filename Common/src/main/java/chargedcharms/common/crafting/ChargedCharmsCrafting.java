/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package chargedcharms.common.crafting;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import chargedcharms.common.crafting.recipe.ConditionalRecipe;
import chargedcharms.common.crafting.recipe.RegenerationChargeRecipe;
import chargedcharms.data.recipe.ICondition;
import chargedcharms.data.recipe.IConditionSerializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.resources.ResourceLocation;

import chargedcharms.data.recipe.condition.ModLoadedCondition;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public class ChargedCharmsCrafting {

    private static final Map<ResourceLocation, IConditionSerializer<?>> conditionsMap = new HashMap<>();

    public static <T extends ICondition> JsonObject serialize(T condition) {
        @SuppressWarnings("unchecked")
        IConditionSerializer<T> serializer = (IConditionSerializer<T>)conditionsMap.get(condition.getID());
        if (serializer == null)
            throw new JsonSyntaxException("Unknown condition type: " + condition.getID().toString());
        return serializer.getJson(condition);
    }

    public static IConditionSerializer<?> register(IConditionSerializer<?> serializer) {
        ResourceLocation key = serializer.getID();
        if (conditionsMap.containsKey(key))
            throw new IllegalStateException("Duplicate recipe condition serializer: " + key);
        conditionsMap.put(key, serializer);
        return serializer;
    }

    public static boolean processConditions(JsonObject json, String memberName, ICondition.IContext context)
    {
        return !json.has(memberName) || processConditions(GsonHelper.getAsJsonArray(json, memberName), context);
    }

    public static boolean processConditions(JsonArray conditions, ICondition.IContext context)
    {
        for (int x = 0; x < conditions.size(); x++)
        {
            if (!conditions.get(x).isJsonObject())
                throw new JsonSyntaxException("Conditions must be an array of JsonObjects");

            JsonObject json = conditions.get(x).getAsJsonObject();
            if (!getCondition(json).test(context))
                return false;
        }
        return true;
    }

    public static ICondition getCondition(JsonObject json)
    {
        ResourceLocation type = new ResourceLocation(GsonHelper.getAsString(json, "type"));
        IConditionSerializer<?> serializer = conditionsMap.get(type);
        if (serializer == null)
            throw new JsonSyntaxException("Unknown condition type: " + type.toString());
        return serializer.read(json);
    }

    public static void registerRecipeSerializers(BiConsumer<RecipeSerializer<?>, ResourceLocation> consumer) {
        consumer.accept(RegenerationChargeRecipe.SERIALIZER, prefix("recharge_regeneration_charm"));
        consumer.accept(ConditionalRecipe.SERIALZIER, prefix("conditional"));
    }

}
