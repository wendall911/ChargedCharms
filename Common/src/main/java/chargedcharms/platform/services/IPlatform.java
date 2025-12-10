package chargedcharms.platform.services;

import java.util.function.Consumer;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public interface IPlatform {

    <T> void registerDataComponent(Identifier name, DataComponentType<T> component);

    void addItemRegistryCallback(Consumer<Item> consumer);

}
