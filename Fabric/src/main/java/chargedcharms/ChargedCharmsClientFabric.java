package chargedcharms;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.data.integration.ModIntegration;

import technology.roughness.whitenoise.platform.Services;

public class ChargedCharmsClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Set<ResourceLocation> charms = new HashSet<>(CharmEffectProviders.getItems());
        Set<ResourceLocation> remove = new HashSet<>();

        ChargedCharmsClient.init();

        for (ResourceLocation charm : charms) {
            Optional<Holder.Reference<Item>> itemReference = BuiltInRegistries.ITEM.get(charm);
            Item item = itemReference.map(Holder.Reference::value).orElse(Items.AIR);
            boolean addItem = true;

            if (item != Items.AIR) {
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

}
