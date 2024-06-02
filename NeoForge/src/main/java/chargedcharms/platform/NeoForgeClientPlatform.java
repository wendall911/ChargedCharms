package chargedcharms.platform;

import java.util.Collections;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomDisplay;

import net.minecraft.client.model.EntityModel;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import top.theillusivec4.curios.api.client.ICurioRenderer;

import chargedcharms.platform.services.IClientPlatform;

public class NeoForgeClientPlatform implements IClientPlatform {

    @Override
    public void translateToPosition(LivingEntity livingEntity,
            EntityModel<? extends LivingEntity> model, PoseStack poseStack) {
        ICurioRenderer.translateIfSneaking(poseStack, livingEntity);
        ICurioRenderer.rotateIfSneaking(poseStack, livingEntity);
        float zPos = -0.15F;

        if (!livingEntity.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
            zPos = -0.20F;
        }

        poseStack.translate(0.1F, 0.2F, zPos);
    }

    @Override
    public void addCustomDisplay(DisplayRegistry helper, List<EntryIngredient> input, RecipeHolder<CraftingRecipe> recipe, RegistryAccess registryAccess) {
        recipe.value().getIngredients().forEach(ingredient -> {
            input.add(EntryIngredients.ofIngredient(ingredient));
        });

        helper.add(new DefaultCustomDisplay(null, input, Collections.singletonList(EntryIngredients.of(recipe.value().getResultItem(registryAccess)))));
    }

    @Override
    public boolean isVanillaItemType(EntryStack<?> entryStack) {
        return entryStack.getType() == VanillaEntryTypes.ITEM;
    }

}
