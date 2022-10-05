package chargedcharms.data.recipe;

import java.util.function.BiConsumer;

import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

import chargedcharms.config.ConfigHandler;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public class ConfigResourceCondition implements ICondition {

    public static final ResourceLocation ID = prefix("config_disabled");
    private final String configValue;

    public ConfigResourceCondition(String configValue) {
        this.configValue = configValue;
    }

    public static void init(BiConsumer<RecipeSerializer<?>, ResourceLocation> consumer) {
        CraftingHelper.register(Serializer.INSTANCE);
    }

    @Override
    public String toString() {
        return "config_disabled(\"" + configValue + "\")";
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public boolean test(IContext context) {
        return !ConfigHandler.conditionsMap.getOrDefault(configValue, false);
    }

    public static class Serializer implements IConditionSerializer<ConfigResourceCondition> {

        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, ConfigResourceCondition value) {
            json.addProperty("config", value.configValue);
        }

        @Override
        public ConfigResourceCondition read(JsonObject json) {
            return new ConfigResourceCondition(GsonHelper.getAsString(json, "config"));
        }

        @Override
        public ResourceLocation getID() {
            return ConfigResourceCondition.ID;
        }

    }

}