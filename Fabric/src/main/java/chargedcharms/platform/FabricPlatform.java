package chargedcharms.platform;

import java.nio.file.Path;
import java.util.List;

import com.google.gson.JsonObject;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import chargedcharms.common.CharmProviders;
import chargedcharms.common.FabricCreativeTab;
import chargedcharms.platform.services.IPlatform;

public class FabricPlatform implements IPlatform {

    @Override
    public ItemStack findCharm(LivingEntity livingEntity) {
        return TrinketsApi.getTrinketComponent(livingEntity).map(component -> {
            List<Tuple<SlotReference, ItemStack>> res =
                component.getEquipped(stack -> CharmProviders.IS_CHARM.test(stack.getItem()));

            return res.size() > 0 ? res.get(0).getB() : ItemStack.EMPTY;
        }).orElse(ItemStack.EMPTY);
    }

    @Override
    public ResourceLocation getResourceLocation(Item item) {
        return Registry.ITEM.getKey(item);
    }

    @Override
    public boolean isModLoaded(String name) {
        return FabricLoader.getInstance().isModLoaded(name);
    }

    @Override
    public FabricItemSettings getProps() {
        return new FabricItemSettings().group(FabricCreativeTab.INSTANCE);
    }

    @Override
    public void saveRecipeAdvancement(DataGenerator gen, CachedOutput cache, JsonObject json, Path path) {
        RecipeProvider.saveAdvancement(cache, json, path);
    }

}
