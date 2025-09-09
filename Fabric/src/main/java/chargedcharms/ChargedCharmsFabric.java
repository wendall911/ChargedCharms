package chargedcharms;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import technology.roughness.whitenoise.platform.Services;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.crafting.ChargedCharmsCrafting;
import chargedcharms.common.item.ChargedCharmsItems;

public class ChargedCharmsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        boolean isClient = Services.PLATFORM.isPhysicalClient();

        registryInit();

        ChargedCharms.init();

        Set<ResourceLocation> charms = new HashSet<>(CharmEffectProviders.getItems());

        if (isClient) {
            Set<ResourceLocation> remove = new HashSet<>();

            for (ResourceLocation charm : charms) {
                Item item = Registry.ITEM.get(charm);

                if (item != Items.AIR) {
                    FabricClientHooks.registerTrinketRenderer(item);
                    remove.add(charm);
                }
            }

            charms.removeAll(remove);
        }

        RegistryEntryAddedCallback.event(Registry.ITEM).register((rawId, id, object) -> {
            if (isClient && !charms.isEmpty()) {
                if (charms.contains(id)) {
                    FabricClientHooks.registerTrinketRenderer(object);
                    charms.remove(id);
                }
            }
        });
    }

    private void registryInit() {
        ChargedCharmsItems.registerItems(bind(Registry.ITEM));

        ChargedCharmsCrafting.registerRecipeSerializers(bind(Registry.RECIPE_SERIALIZER));
    }

    private static <T> BiConsumer<T, ResourceLocation> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }

}
