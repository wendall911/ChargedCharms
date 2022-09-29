package chargedcharms.mixin;

import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import chargedcharms.common.item.ChargedCharmBase;

@Mixin(RepairItemRecipe.class)
public class RepairItemRecipeOverride {

    @Inject(at = @At(value = "HEAD"), method = "matches(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/world/level/Level;)Z", cancellable = true)
    private void isChargedCharm(CraftingContainer craftingContainer, Level pLevel, CallbackInfoReturnable<Boolean> cir) {
        boolean hasChargedCharm = false;

        for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
            if (craftingContainer.getItem(i).getItem() instanceof ChargedCharmBase) {
                hasChargedCharm = true;
            }
        }

        if (hasChargedCharm) {
            cir.setReturnValue(false);
        }
    }

}
