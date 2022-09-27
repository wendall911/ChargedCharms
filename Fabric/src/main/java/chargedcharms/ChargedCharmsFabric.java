package chargedcharms;

import java.util.HashSet;
import java.util.Set;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import chargedcharms.common.CharmProviders;

public class ChargedCharmsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        boolean isClient = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
        Set<ResourceLocation> charms = new HashSet<>(CharmProviders.getItems());

        ChargedCharms.init();

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

}
