package chargedcharms.data.recipe;

import org.jetbrains.annotations.NotNull;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.neoforged.neoforge.common.conditions.ICondition;

import chargedcharms.config.ConfigHandler;

public record ConfigResourceCondition(String configValue) implements ICondition {

    public static final String ID = "config_disabled";
    public static final Codec<ConfigResourceCondition> CODEC = RecordCodecBuilder.create(b -> b.group(
        Codec.STRING.fieldOf(ID).forGetter(ConfigResourceCondition::configValue)
    ).apply(b, ConfigResourceCondition::new));

    @Override
    public String toString() {
        return ID + "(\"" + configValue + "\")";
    }

    @Override
    public boolean test(@NotNull IContext context) {
        return !ConfigHandler.conditionsMap.getOrDefault(configValue, false);
    }

    @Override
    public @NotNull Codec<? extends ICondition> codec() {
        return CODEC;
    }

}