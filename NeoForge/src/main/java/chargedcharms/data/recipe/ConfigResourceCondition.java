package chargedcharms.data.recipe;

import org.jetbrains.annotations.NotNull;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.MapCodec;

import net.neoforged.neoforge.common.conditions.ICondition;

import chargedcharms.config.ConfigHandler;

public record ConfigResourceCondition(String configValue) implements ICondition {

    public static final String ID = "config_disabled";
    public static final MapCodec<ConfigResourceCondition> CODEC = RecordCodecBuilder.mapCodec(b -> b.group(
        Codec.STRING.fieldOf(ID).forGetter(ConfigResourceCondition::configValue)
    ).apply(b, ConfigResourceCondition::new));

    @Override
    public @NotNull String toString() {
        return ID + "(\"" + configValue + "\")";
    }

    @Override
    public boolean test(@NotNull IContext context) {
        return !ConfigHandler.Common.getConfigValue(configValue);
    }

    @Override
    public @NotNull MapCodec<? extends ICondition> codec() {
        return CODEC;
    }

}
