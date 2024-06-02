package chargedcharms.platform.services;

import java.util.Set;
import java.util.function.UnaryOperator;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IPlatform {

    Set<ItemStack> findCharms(LivingEntity livingEntity);

    ResourceLocation getResourceLocation(Item item);

    boolean isModLoaded(String name);

    boolean isPhysicalClient();

    <T> DataComponentType<T> registerDataComponent(String name, UnaryOperator<DataComponentType.Builder<T>> builder);

}
