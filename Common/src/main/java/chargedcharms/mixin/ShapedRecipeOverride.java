package chargedcharms.mixin;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import chargedcharms.common.item.ChargedCharmBase;
import chargedcharms.common.item.ChargedCharmsItems;

@Mixin(ShapedRecipe.class)
public abstract class ShapedRecipeOverride {

    @Inject(at = @At(value = "RETURN"), method = "assemble(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/core/RegistryAccess;)Lnet/minecraft/world/item/ItemStack;", cancellable = true)
    private void checkAssemble(CraftingContainer craftingContainer, RegistryAccess registryAccess, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack resultCopy = cir.getReturnValue();

        if (resultCopy.is(ChargedCharmsItems.glowupCharm)) {
            resultCopy.setDamageValue(resultCopy.getMaxDamage());

            cir.setReturnValue(resultCopy);
        }
        else if (resultCopy.getItem() instanceof ChargedCharmBase) {
            resultCopy.setDamageValue(resultCopy.getMaxDamage() - 1);

            cir.setReturnValue(resultCopy);
        }
    }

}