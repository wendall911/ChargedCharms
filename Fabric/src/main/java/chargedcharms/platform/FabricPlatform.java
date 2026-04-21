package chargedcharms.platform;

import java.util.Set;

import com.google.common.collect.Sets;

import eu.pb4.trinkets.api.TrinketsApi;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.platform.services.IPlatform;

public class FabricPlatform implements IPlatform {

    @Override
    public Set<ItemStack> findCharms(LivingEntity livingEntity) {
        Set<ItemStack> results = Sets.newHashSet();

        TrinketsApi.getAttachment(livingEntity).getAllEquipped().iterator().forEachRemaining(component -> {
            if (CharmEffectProviders.IS_CHARM.test(component.getB().getItem())) {
                results.add(component.getB());
            }
        });

        return results;
    }

    @Override
    public <T> void registerDataComponent(Identifier name, DataComponentType<T> component) {
        Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            name,
            component
        );
    }

}
