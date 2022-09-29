package chargedcharms.platform;

import com.google.gson.JsonObject;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import chargedcharms.common.CharmProviders;
import chargedcharms.common.ForgeCreativeTab;
import chargedcharms.platform.mixin.RecipeProviderForgeAccessor;
import chargedcharms.platform.services.IPlatform;

import java.nio.file.Path;

public class ForgePlatform implements IPlatform {

    @Override
    public ItemStack findCharm(LivingEntity livingEntity) {
        return CuriosApi.getCuriosHelper()
            .findFirstCurio(livingEntity, stack -> CharmProviders.IS_CHARM.test(stack.getItem()))
            .map(SlotResult::stack).orElse(ItemStack.EMPTY);
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
    public Item.Properties getProps() {
        return new Item.Properties().tab(ForgeCreativeTab.INSTANCE);
    }

    @Override
    public void saveRecipeAdvancement(DataGenerator gen, CachedOutput cache, JsonObject json, Path path) {
        ((RecipeProviderForgeAccessor) new RecipeProvider(gen)).callSaveRecipeAdvancement(cache, json, path);
    }

}
