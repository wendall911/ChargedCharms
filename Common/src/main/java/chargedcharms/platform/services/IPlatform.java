package chargedcharms.platform.services;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IPlatform {

    ItemStack findCharm(LivingEntity livingEntity);

    ResourceLocation getResourceLocation(Item item);

    boolean isModLoaded(String name);

}
