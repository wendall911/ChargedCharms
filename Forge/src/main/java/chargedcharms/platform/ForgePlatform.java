package chargedcharms.platform;

import java.nio.file.Path;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeProvider;
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

import chargedcharms.common.CharmProviders;
import chargedcharms.common.ForgeCreativeTab;
import chargedcharms.platform.mixin.RecipeProviderForgeAccessor;
import chargedcharms.platform.services.IPlatform;

public class ForgePlatform implements IPlatform {

    @Override
    public Set<ItemStack> findCharms(LivingEntity livingEntity) {
        Set<ItemStack> results = Sets.newHashSet();

        CuriosApi.getCuriosHelper().findCurios(livingEntity, stack -> CharmProviders.IS_CHARM.test(stack.getItem()))
                .stream().map(SlotResult::stack).forEach(results::add);

        return results;
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

    @Override
    public boolean isPhysicalClient() {
        return FMLLoader.getDist() == Dist.CLIENT;
    }

}
