package chargedcharms.platform;

import java.util.Set;
import java.util.stream.Collectors;

import chargedcharms.registries.ChargedCharmsForgeRegistries;
import com.google.common.collect.Sets;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.ForgeRegistries;

import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.platform.services.IPlatform;

public class ForgePlatform implements IPlatform {

    @Override
    public Set<ItemStack> findCharms(LivingEntity livingEntity) {
        return CuriosApi.getCuriosInventory(livingEntity).map(
            inv -> inv.findCurios(stack -> CharmEffectProviders.IS_CHARM.test(stack.getItem())).stream().map(SlotResult::stack))
                .map(itemStackStream -> itemStackStream.collect(Collectors.toSet())).orElse(Sets.newHashSet());
    }

    @Override
    public ResourceLocation getResourceLocation(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }

    @Override
    public boolean isModLoaded(String name) {
        return ModList.get().isLoaded(name);
    }

    @Override
    public boolean isPhysicalClient() {
        return FMLLoader.getDist() == Dist.CLIENT;
    }

    @Override
    public <T> void registerDataComponent(ResourceLocation name, DataComponentType<T> component) {
        ChargedCharmsForgeRegistries.COMPONENT_TYPE_DEFERRED_REGISTER.register(
            name.getPath(),
            () -> component
        );
    }

}
