package chargedcharms.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;

public class RegistryHelper {

    @SuppressWarnings("unchecked")
    public static <T> Registry<T> getRegistry(ResourceKey<Registry<T>> resourceKey) {
        return (Registry<T>) BuiltInRegistries.REGISTRY.get(resourceKey.location());
    }

}
