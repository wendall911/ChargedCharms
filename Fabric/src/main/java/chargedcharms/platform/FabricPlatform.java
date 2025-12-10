package chargedcharms.platform;

import java.util.function.Consumer;

import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import chargedcharms.platform.services.IPlatform;

public class FabricPlatform implements IPlatform {

    @Override
    public <T> void registerDataComponent(Identifier name, DataComponentType<T> component) {
        Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            name,
            component
        );
    }

    @Override
    public void addItemRegistryCallback(Consumer<Item> consumer) {
        RegistryEntryAddedCallback.event(BuiltInRegistries.ITEM)
            .register((rawId, location, item) -> consumer.accept(item));
    }

}
