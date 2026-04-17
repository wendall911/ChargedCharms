package chargedcharms.platform;

import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.platform.services.IPlatform;
import chargedcharms.registries.ChargedCharmsNeoForgeRegistries;

public class NeoForgePlatform implements IPlatform {

    @Override
    public Set<ItemStack> findCharms(LivingEntity livingEntity) {
        return CuriosApi.getCuriosInventory(livingEntity).map(
            inv -> inv.findCurios(stack -> CharmEffectProviders.IS_CHARM.test(stack.getItem())).stream().map(SlotResult::stack))
                .map(itemStackStream -> itemStackStream.collect(Collectors.toSet())).orElse(Sets.newHashSet());
    }

    @Override
    public <T> void registerDataComponent(Identifier name, DataComponentType<T> component) {
        ChargedCharmsNeoForgeRegistries.COMPONENT_TYPE_DEFERRED_REGISTER.register(
            name.getPath(),
            () -> component
        );
    }

}
