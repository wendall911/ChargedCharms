package chargedcharms.platform.services;

import java.util.Set;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IPlatform {

    Set<ItemStack> findCharms(LivingEntity livingEntity);

    <T> void registerDataComponent(ResourceLocation name, DataComponentType<T> component);

}
