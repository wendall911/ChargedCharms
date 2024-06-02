package chargedcharms.data.recipe;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import chargedcharms.config.ConfigHandler;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public record ConfigResourceCondition(String configValue) implements ResourceCondition {

    private static final ResourceLocation ID = prefix("config_disabled");
    public static final MapCodec<ConfigResourceCondition> CODEC = RecordCodecBuilder.mapCodec(b -> b.group(
        Codec.STRING.fieldOf("config").forGetter(ConfigResourceCondition::configValue)
    ).apply(b, ConfigResourceCondition::new));
    private static final ResourceConditionType<ConfigResourceCondition> TYPE = ResourceConditionType.create(ID, CODEC);

    public static void init() {
        ResourceConditions.register(TYPE);
    }

    @Override
    public ResourceConditionType<?> getType() {
        return TYPE;
    }

    @Override
    public boolean test(@Nullable HolderLookup.Provider registryLookup) {
        return !ConfigHandler.conditionsMap.getOrDefault(configValue, false);
    }
}