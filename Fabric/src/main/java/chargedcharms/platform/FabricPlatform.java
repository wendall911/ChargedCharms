package chargedcharms.platform;

import java.util.Set;

import com.google.common.collect.Sets;

import dev.emi.trinkets.api.TrinketsApi;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.platform.services.IPlatform;

public class FabricPlatform implements IPlatform {

    @Override
    public Set<ItemStack> findCharms(LivingEntity livingEntity) {
        Set<ItemStack> results = Sets.newHashSet();

        return TrinketsApi.getTrinketComponent(livingEntity).map(component -> {
            component.getEquipped(stack -> CharmEffectProviders.IS_CHARM.test(stack.getItem())).stream().map(Tuple::getB)
                    .forEach(results::add);

            return results;
        }).orElse(results);
    }

    // TODO add to creative tabs
    @Override
    public FabricItemSettings getProps() {
        return new FabricItemSettings();
    }

}
