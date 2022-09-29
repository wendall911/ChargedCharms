package chargedcharms.platform.services;

import java.nio.file.Path;

import com.google.gson.JsonObject;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IPlatform {

    ItemStack findCharm(LivingEntity livingEntity);

    ResourceLocation getResourceLocation(Item item);

    boolean isModLoaded(String name);

    Item.Properties getProps();

    void saveRecipeAdvancement(DataGenerator gen, CachedOutput cache, JsonObject json, Path path);

}
