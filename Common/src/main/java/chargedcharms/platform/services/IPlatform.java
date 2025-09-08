package chargedcharms.platform.services;

import java.util.Set;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IPlatform {

    Set<ItemStack> findCharms(LivingEntity livingEntity);

    Item.Properties getProps();

}
