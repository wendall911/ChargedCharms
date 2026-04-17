package chargedcharms;

import java.util.function.BiConsumer;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import technology.roughness.whitenoise.platform.Services;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.component.ChargedCharmsComponents;
import chargedcharms.common.crafting.ChargedCharmsCrafting;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.data.integration.ModIntegration;
import chargedcharms.data.recipe.ConfigResourceCondition;

public class ChargedCharmsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        registryInit();

        RegistryEntryAddedCallback.event(BuiltInRegistries.ITEM).register((rawId, id, object) -> {
            if (isClient && !charms.isEmpty()) {
                if (charms.contains(id)) {
                    FabricClientHooks.registerTrinketRenderer(object);
                    charms.remove(id);
                }
            }
        });
    }

    private void registryInit() {
        ChargedCharmsItems.registerItems(bind(BuiltInRegistries.ITEM));
        ChargedCharmsCrafting.registerRecipeSerializers(bind(BuiltInRegistries.RECIPE_SERIALIZER));
        ChargedCharmsComponents.registerDataComponents();
        ConfigResourceCondition.register();
    }

    private static <T> BiConsumer<T, Identifier> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }

}
