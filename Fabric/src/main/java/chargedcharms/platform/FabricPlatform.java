package chargedcharms.platform;

import java.util.Set;

import com.google.common.collect.Sets;

import eu.pb4.trinkets.api.TrinketsApi;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.platform.services.IPlatform;

public class FabricPlatform implements IPlatform {

    @Override
    public Set<ItemStack> findCharms(LivingEntity livingEntity) {
        Set<ItemStack> results = Sets.newHashSet();

        /*
        // TODO Implement with eu.pb4 Trinkets
        return TrinketsApi.getTrinketComponent(livingEntity).map(component -> {
            component.getEquipped(stack -> CharmEffectProviders.IS_CHARM.test(stack.getItem())).stream().map(Tuple::getB)
                    .forEach(results::add);

            return results;
        }).orElse(results);
         */

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
