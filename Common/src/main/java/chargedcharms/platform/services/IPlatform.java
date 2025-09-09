package chargedcharms.platform.services;

import java.nio.file.Path;
import java.util.Set;

import com.google.gson.JsonObject;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IPlatform {

    Set<ItemStack> findCharms(LivingEntity livingEntity);

    Item.Properties getProps();

    void saveRecipeAdvancement(DataGenerator gen, HashCache cache, JsonObject json, Path path);

}
