package chargedcharms.platform.services;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;

import net.minecraft.client.model.EntityModel;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public interface IClientPlatform {

    void translateToPosition(LivingEntity livingEntity, EntityModel<? extends LivingEntity> model,
            PoseStack poseStack);

    void addCustomDisplay(DisplayRegistry helper, List<EntryIngredient> input, RecipeHolder<CraftingRecipe> recipe, RegistryAccess registryAccess);

    boolean isVanillaItemType(EntryStack<?> entryStack);

}
