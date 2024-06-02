package chargedcharms.common.component;

import java.util.function.UnaryOperator;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;

import chargedcharms.platform.Services;

public class ChargedCharmsComponents {

    public static void init() {}

    public static final DataComponentType<Integer> SOLAR_RADIATION = register("solar_radiation", (builder) -> {
        return builder.persistent(ExtraCodecs.intRange(0, 20000)).networkSynchronized(ByteBufCodecs.VAR_INT);
    });

    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return Services.PLATFORM.registerDataComponent(name, builder);
    }

}
