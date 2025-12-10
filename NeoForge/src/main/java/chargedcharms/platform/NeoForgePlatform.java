package chargedcharms.platform;

import java.util.function.Consumer;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import net.neoforged.neoforge.registries.callback.AddCallback;

import chargedcharms.platform.services.IPlatform;
import chargedcharms.registries.ChargedCharmsNeoForgeRegistries;

public class NeoForgePlatform implements IPlatform {

    @Override
    public <T> void registerDataComponent(Identifier name, DataComponentType<T> component) {
        ChargedCharmsNeoForgeRegistries.COMPONENT_TYPE_DEFERRED_REGISTER.register(
            name.getPath(),
            () -> component
        );
    }

    @Override
    public void addItemRegistryCallback(Consumer<Item> consumer) {
        BuiltInRegistries.ITEM.addCallback(
            (AddCallback<Item>) (registry, rawId, location, item) -> consumer.accept(item));
    }

}
