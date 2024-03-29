package chargedcharms;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.crafting.ChargedCharmsCrafting;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.data.integration.ModIntegration;
import chargedcharms.platform.Services;

public class ChargedCharmsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        boolean isClient = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;

        registryInit();

        Set<ResourceLocation> charms = new HashSet<>(CharmEffectProviders.getItems());

        if (isClient) {
            Set<ResourceLocation> remove = new HashSet<>();

            for (ResourceLocation charm : charms) {
                Item item = BuiltInRegistries.ITEM.get(charm);
                boolean addItem = true;

                if (item != Items.AIR) {
                    FabricClientHooks.registerTrinketRenderer(item);
                    if (item == ChargedCharmsItems.enchantedTotemCharm) {
                        if (!Services.PLATFORM.isModLoaded(ModIntegration.BMO_MODID)) {
                            addItem = false;
                        }
                    }

                    if (addItem) {
                        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> entries.accept(item));
                    }

                    remove.add(charm);
                }
            }

            charms.removeAll(remove);
        }

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
    }

    private static <T> BiConsumer<T, ResourceLocation> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }

}