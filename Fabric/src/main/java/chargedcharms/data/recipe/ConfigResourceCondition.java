package chargedcharms.data.recipe;

import com.google.gson.JsonObject;

import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import chargedcharms.config.ConfigHandler;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public class ConfigResourceCondition {

    public static final ResourceLocation ID = prefix("config_disabled");

    public static void init() {
        ResourceConditions.register(ID, ConfigResourceCondition::configCheck);
    }

    public static ConditionJsonProvider configDisabled(String configValue) {
        return new ConditionJsonProvider() {
            @Override
            public ResourceLocation getConditionId() {
                return ID;
            }

            @Override
            public void writeParameters(JsonObject object) {
                object.addProperty("config", configValue);
            }
        };
    }

    public static boolean configCheck(JsonObject json) {
        String configValue = GsonHelper.getAsString(json, "config");

        return !ConfigHandler.conditionsMap.getOrDefault(configValue, false);
    }

}