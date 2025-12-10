package chargedcharms.common.component;

import chargedcharms.platform.Services;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public class ChargedCharmsComponents {

    public static void registerDataComponents() {
        register(prefix("solar_radiation"), SOLAR_RADIATION);
    }

    public static final DataComponentType<Integer> SOLAR_RADIATION = DataComponentType.<Integer>builder().persistent(ExtraCodecs.intRange(0, 20000)).networkSynchronized(ByteBufCodecs.VAR_INT).build();

    private static <T> void register(Identifier name, DataComponentType<T> component) {
        Services.PLATFORM.registerDataComponent(name, component);
    }

}
