package chargedcharms.platform;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.platform.services.IPlatform;

public class ForgePlatform implements IPlatform {

    @Override
    public Set<ItemStack> findCharms(LivingEntity livingEntity) {
        Set<ItemStack> results = Sets.newHashSet();

        CuriosApi.getCuriosHelper().findCurios(livingEntity, stack -> CharmEffectProviders.IS_CHARM.test(stack.getItem()))
                .stream().map(SlotResult::stack).forEach(results::add);

        return results;
    }

    // TODO add to creative tabs
    @Override
    public Item.Properties getProps() {
        return new Item.Properties();
    }

}