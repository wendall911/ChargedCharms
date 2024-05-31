package chargedcharms.platform;

import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.platform.services.IPlatform;

public class NeoForgePlatform implements IPlatform {

    @Override
    public Set<ItemStack> findCharms(LivingEntity livingEntity) {
        return CuriosApi.getCuriosInventory(livingEntity).map(
            inv -> inv.findCurios(stack -> CharmEffectProviders.IS_CHARM.test(stack.getItem())).stream().map(SlotResult::stack))
                .map(itemStackStream -> itemStackStream.collect(Collectors.toSet())).orElse(Sets.newHashSet());
    }

    @Override
    public ResourceLocation getResourceLocation(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    @Override
    public boolean isModLoaded(String name) {
        return ModList.get().isLoaded(name);
    }

    // TODO add to creative tabs
    @Override
    public Item.Properties getProps() {
        return new Item.Properties();
    }

    @Override
    public boolean isPhysicalClient() {
        return FMLLoader.getDist() == Dist.CLIENT;
    }

}
